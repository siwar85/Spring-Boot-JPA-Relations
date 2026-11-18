package tn.esprit.autoloc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> gererRessourceIntrouvable(
            ResourceNotFoundException ex) {
        return construireReponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> gererRequeteInvalide(
            IllegalArgumentException ex) {
        return construireReponse(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
    }

    // Erreurs sur les champs d'un DTO reçu avec @Valid @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> gererChampsInvalides(
            MethodArgumentNotValidException ex) {
        List<String> erreurs = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + " : " + f.getDefaultMessage())
                .sorted()
                .toList();
        return construireReponse(HttpStatus.BAD_REQUEST, "Données invalides", erreurs);
    }

    // Erreurs sur les paramètres d'URL (@Positive sur les identifiants)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> gererParametresInvalides(
            HandlerMethodValidationException ex) {
        List<String> erreurs = ex.getParameterValidationResults().stream()
                .flatMap(r -> r.getResolvableErrors().stream()
                        .map(e -> r.getMethodParameter().getParameterName()
                                + " : " + e.getDefaultMessage()))
                .sorted()
                .toList();
        return construireReponse(HttpStatus.BAD_REQUEST, "Paramètres invalides", erreurs);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> gererCorpsIllisible(
            HttpMessageNotReadableException ex) {
        return construireReponse(HttpStatus.BAD_REQUEST,
                "Corps de requête illisible : vérifiez le JSON, les dates (aaaa-mm-jj) et les énumérations",
                List.of());
    }

    private ResponseEntity<Map<String, Object>> construireReponse(
            HttpStatus statut, String message, List<String> erreurs) {
        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("statut", statut.value());
        corps.put("message", message);
        if (!erreurs.isEmpty()) {
            corps.put("erreurs", erreurs);
        }
        corps.put("horodatage", LocalDateTime.now().toString());
        return ResponseEntity.status(statut).body(corps);
    }
}