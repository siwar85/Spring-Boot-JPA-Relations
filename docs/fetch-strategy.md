# Stratégie de fetch et de cascade — AutoLoc

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition) ; supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule survit à la suppression de son agence. LAZY évite de charger toute la flotte à chaque lecture d'agence. |
| Agence → Employe | LAZY | Aucune | Un employé existe indépendamment de son agence. LAZY évite de charger tout le personnel inutilement. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules : supprimer un véhicule ne doit pas supprimer un équipement. Un Set évite les doublons. |
| Client → Reservation | LAZY | PERSIST | Les nouvelles réservations sont enregistrées avec leur client, mais l'historique n'est pas supprimé avec lui. |
| Reservation → Vehicule | LAZY | Aucune | Un véhicule existe indépendamment de ses réservations ; association unidirectionnelle. |
| Reservation ↔ Contrat | LAZY | ALL (côté Reservation) | Le contrat découle de la réservation : il est créé, mis à jour et supprimé avec elle. |
| Vehicule → Maintenance | LAZY | PERSIST | Les maintenances sont enregistrées avec leur véhicule, mais l'historique n'est pas supprimé avec lui. |