package com.devbuild.Portail_de_suivi_du_doctorat.Controller.API;

import com.devbuild.Portail_de_suivi_du_doctorat.Services.CampagneInscriptionService;
import com.devbuild.Portail_de_suivi_du_doctorat.Services.InscriptionService;
import com.devbuild.Portail_de_suivi_du_doctorat.entities.CampagneInscription;
import com.devbuild.Portail_de_suivi_du_doctorat.entities.Inscription;
import com.devbuild.Portail_de_suivi_du_doctorat.enums.StatutDossier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ═══════════════════════════════════════════════════════════
 *  REST API — Inscriptions & Campagnes
 *  Base URL inscriptions : /api/inscriptions
 *  Base URL campagnes    : /api/campagnes
 * ═══════════════════════════════════════════════════════════
 *
 *  TOUTES LES MÉTHODES DE InscriptionService :
 *   ✔ soumettre(doctorantId, campagneId, inscription)
 *   ✔ findById()
 *   ✔ findByDoctorantId()
 *   ✔ findByStatut()
 *   ✔ findEnAttenteAvisDirecteur()
 *   ✔ findEnAttenteValidationAdmin()
 *   ✔ validerParDirecteur()
 *   ✔ validerParAdmin()
 *   ✔ rejeter()
 *   ✔ countByStatut()
 *
 *  TOUTES LES MÉTHODES DE CampagneInscriptionService :
 *   ✔ creer()
 *   ✔ modifier()
 *   ✔ findById()
 *   ✔ findCampagneActive()
 *   ✔ findAll()
 *   ✔ activer()
 *   ✔ desactiver()
 */
@RestController
@RequiredArgsConstructor
public class InscriptionApiController {

    private final InscriptionService         inscriptionService;
    private final CampagneInscriptionService campagneService;

    // ══════════════════════════════════════════════════════════════════
    // INSCRIPTIONS — /api/inscriptions
    // ══════════════════════════════════════════════════════════════════

    // ─── GET /api/inscriptions/{id} ───────────────────────────────────────────
    // Postman : GET http://localhost:8080/api/inscriptions/1
    @GetMapping("/api/inscriptions/{id}")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DIRECTEUR_THESE','DOCTORANT')")
    public ResponseEntity<Inscription> findById(@PathVariable Long id) {
        return inscriptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── GET /api/inscriptions/doctorant/{doctorantId} ────────────────────────
    // Toutes les inscriptions d'un doctorant
    // Postman : GET http://localhost:8080/api/inscriptions/doctorant/1
    @GetMapping("/api/inscriptions/doctorant/{doctorantId}")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DIRECTEUR_THESE','DOCTORANT')")
    public ResponseEntity<List<Inscription>> findByDoctorantId(@PathVariable Long doctorantId) {
        return ResponseEntity.ok(inscriptionService.findByDoctorantId(doctorantId));
    }

    // ─── GET /api/inscriptions/statut/{statut} ────────────────────────────────
    // Postman : GET http://localhost:8080/api/inscriptions/statut/EN_ATTENTE
    // Valeurs : EN_ATTENTE, SOUMIS, EN_VALIDATION_DIRECTEUR, EN_VALIDATION_ADMIN, VALIDE, REJETE
    @GetMapping("/api/inscriptions/statut/{statut}")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DIRECTEUR_THESE')")
    public ResponseEntity<List<Inscription>> findByStatut(@PathVariable StatutDossier statut) {
        return ResponseEntity.ok(inscriptionService.findByStatut(statut));
    }

    // ─── GET /api/inscriptions/attente-directeur/{directeurId} ───────────────
    // Inscriptions en attente d'avis du directeur (ID du directeur)
    // Postman : GET http://localhost:8080/api/inscriptions/attente-directeur/2
    @GetMapping("/api/inscriptions/attente-directeur/{directeurId}")
    @PreAuthorize("hasRole('DIRECTEUR_THESE')")
    public ResponseEntity<List<Inscription>> findEnAttenteAvisDirecteur(
            @PathVariable Long directeurId) {
        return ResponseEntity.ok(inscriptionService.findEnAttenteAvisDirecteur(directeurId));
    }

    // ─── GET /api/inscriptions/attente-admin ─────────────────────────────────
    // Inscriptions en attente de validation admin
    // Postman : GET http://localhost:8080/api/inscriptions/attente-admin
    @GetMapping("/api/inscriptions/attente-admin")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<List<Inscription>> findEnAttenteValidationAdmin() {
        return ResponseEntity.ok(inscriptionService.findEnAttenteValidationAdmin());
    }

    // ─── GET /api/inscriptions/count/{statut} ────────────────────────────────
    // Nombre d'inscriptions par statut
    // Postman : GET http://localhost:8080/api/inscriptions/count/EN_VALIDATION_ADMIN
    @GetMapping("/api/inscriptions/count/{statut}")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<Map<String, Object>> countByStatut(@PathVariable StatutDossier statut) {
        return ResponseEntity.ok(Map.of(
                "statut", statut.name(),
                "count",  inscriptionService.countByStatut(statut)
        ));
    }

    // ─── POST /api/inscriptions ───────────────────────────────────────────────
    // Soumettre une nouvelle inscription
    // Postman : POST http://localhost:8080/api/inscriptions
    // Params query : ?doctorantId=1&campagneId=1
    // Body (JSON) :
    // {
    //   "type": "PREMIERE_INSCRIPTION"
    // }
    @PostMapping("/api/inscriptions")
    @PreAuthorize("hasRole('DOCTORANT')")
    public ResponseEntity<Inscription> soumettre(
            @RequestParam Long doctorantId,
            @RequestParam Long campagneId,
            @RequestBody Inscription inscription) {
        try {
            Inscription saved = inscriptionService.soumettre(doctorantId, campagneId, inscription);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ─── PATCH /api/inscriptions/{id}/valider-directeur ──────────────────────
    // Le directeur donne son avis
    // Postman : PATCH http://localhost:8080/api/inscriptions/1/valider-directeur
    // Body (JSON) : { "avis": "Avis favorable, sujet pertinent" }
    @PatchMapping("/api/inscriptions/{id}/valider-directeur")
    @PreAuthorize("hasRole('DIRECTEUR_THESE')")
    public ResponseEntity<Inscription> validerParDirecteur(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            Inscription updated = inscriptionService.validerParDirecteur(id, body.get("avis"));
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ─── PATCH /api/inscriptions/{id}/valider-admin ───────────────────────────
    // L'admin valide définitivement
    // Postman : PATCH http://localhost:8080/api/inscriptions/1/valider-admin
    // Body (JSON) : { "commentaire": "Dossier complet et conforme" }
    @PatchMapping("/api/inscriptions/{id}/valider-admin")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<Inscription> validerParAdmin(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            Inscription updated = inscriptionService.validerParAdmin(id, body.get("commentaire"));
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ─── PATCH /api/inscriptions/{id}/rejeter ────────────────────────────────
    // Rejeter un dossier
    // Postman : PATCH http://localhost:8080/api/inscriptions/1/rejeter
    // Body (JSON) : { "motif": "Documents manquants" }
    @PatchMapping("/api/inscriptions/{id}/rejeter")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<Inscription> rejeter(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Inscription updated = inscriptionService.rejeter(id, body.get("motif"));
        return ResponseEntity.ok(updated);
    }

    // ══════════════════════════════════════════════════════════════════
    // CAMPAGNES — /api/campagnes
    // ══════════════════════════════════════════════════════════════════

    // ─── GET /api/campagnes ───────────────────────────────────────────────────
    // Toutes les campagnes
    // Postman : GET http://localhost:8080/api/campagnes
    @GetMapping("/api/campagnes")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DOCTORANT')")
    public ResponseEntity<List<CampagneInscription>> findAllCampagnes() {
        return ResponseEntity.ok(campagneService.findAll());
    }

    // ─── GET /api/campagnes/active ────────────────────────────────────────────
    // La campagne actuellement ouverte
    // Postman : GET http://localhost:8080/api/campagnes/active
    @GetMapping("/api/campagnes/active")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DOCTORANT')")
    public ResponseEntity<CampagneInscription> findCampagneActive() {
        return campagneService.findCampagneActive()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── GET /api/campagnes/{id} ──────────────────────────────────────────────
    // Postman : GET http://localhost:8080/api/campagnes/1
    @GetMapping("/api/campagnes/{id}")
    @PreAuthorize("hasAnyRole('PERSONNEL_ADMIN','DOCTORANT')")
    public ResponseEntity<CampagneInscription> findCampagneById(@PathVariable Long id) {
        return campagneService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─── POST /api/campagnes ──────────────────────────────────────────────────
    // Créer une campagne
    // Postman : POST http://localhost:8080/api/campagnes
    // Body (JSON) :
    // {
    //   "anneeUniversitaire": 2025,
    //   "dateOuverture": "2025-09-01",
    //   "dateFermeture": "2025-10-31",
    //   "description": "Campagne 2025-2026"
    // }
    @PostMapping("/api/campagnes")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<CampagneInscription> creerCampagne(
            @RequestBody CampagneInscription campagne) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(campagneService.creer(campagne));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ─── PUT /api/campagnes/{id} ──────────────────────────────────────────────
    // Modifier une campagne (dates, description)
    // Postman : PUT http://localhost:8080/api/campagnes/1
    @PutMapping("/api/campagnes/{id}")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<CampagneInscription> modifierCampagne(
            @PathVariable Long id,
            @RequestBody CampagneInscription campagne) {
        campagne.setId(id);
        return ResponseEntity.ok(campagneService.modifier(campagne));
    }

    // ─── PATCH /api/campagnes/{id}/activer ───────────────────────────────────
    // Postman : PATCH http://localhost:8080/api/campagnes/1/activer
    @PatchMapping("/api/campagnes/{id}/activer")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<Map<String, String>> activerCampagne(@PathVariable Long id) {
        campagneService.activer(id);
        return ResponseEntity.ok(Map.of("message", "Campagne activée."));
    }

    // ─── PATCH /api/campagnes/{id}/desactiver ────────────────────────────────
    // Postman : PATCH http://localhost:8080/api/campagnes/1/desactiver
    @PatchMapping("/api/campagnes/{id}/desactiver")
    @PreAuthorize("hasRole('PERSONNEL_ADMIN')")
    public ResponseEntity<Map<String, String>> desactiverCampagne(@PathVariable Long id) {
        campagneService.desactiver(id);
        return ResponseEntity.ok(Map.of("message", "Campagne désactivée."));
    }
}