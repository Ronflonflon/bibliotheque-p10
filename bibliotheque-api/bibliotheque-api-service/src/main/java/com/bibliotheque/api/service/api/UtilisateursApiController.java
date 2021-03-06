package com.bibliotheque.api.service.api;

import com.bibliotheque.api.business.UtilisateurManagement;
import com.bibliotheque.api.service.model.Utilisateur;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestHeader;
import org.threeten.bp.OffsetDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-08-11T23:01:18.100Z")

@Controller
public class UtilisateursApiController implements UtilisateursApi {

    private static final Logger log = LoggerFactory.getLogger(UtilisateursApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    UtilisateurManagement utilisateurManagement;

    @org.springframework.beans.factory.annotation.Autowired
    public UtilisateursApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addUtilisateur(@ApiParam(value = "Un objet Reservation doit être envoyé pour être ajouté", required = true) @Valid @RequestBody Utilisateur body) {
        String accept = request.getHeader("Accept");
        Optional<com.bibliotheque.api.model.Utilisateur> utilisateurTest = utilisateurManagement.findById(body.getId());

        if (utilisateurManagement.findUtilisateurByMail(body.getMail()) == null || utilisateurTest.isPresent()) {
            utilisateurManagement.save(convertUtilisateurApiToUtilisateur(body));
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<Void> deleteUtilisateur(@ApiParam(value = "ID de l'utilisateur à supprimer", required = true) @PathVariable("utilisateurId") Long utilisateurId) {
        String accept = request.getHeader("Accept");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (utilisateurManagement.findUtilisateurByMail(((UserDetails) principal).getUsername()).isBibliothecaire()) {
                if (utilisateurManagement.findById(utilisateurId).isPresent()) {
                    utilisateurManagement.deleteById(utilisateurId);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Utilisateur> findUtilisateursByMail(@NotNull @ApiParam(value = "Trouver un compte par mail", required = true) @Valid @RequestParam(value = "mail", required = true) String mail) {
        String accept = request.getHeader("Accept");
        com.bibliotheque.api.model.Utilisateur utilisateur = utilisateurManagement.findUtilisateurByMail(mail);
        if (utilisateur != null) {
            return new ResponseEntity<Utilisateur>(convertUtilisateurToUtilisateurApi(utilisateur), HttpStatus.OK);
        }
        return new ResponseEntity<Utilisateur>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Utilisateur> getUtilisateurById(@ApiParam(value = "ID of livre to return", required = true) @PathVariable("utilisateurId") Long utilisateurId) {
        String accept = request.getHeader("Accept");
        Optional<com.bibliotheque.api.model.Utilisateur> utilisateur = utilisateurManagement.findById(utilisateurId);
        if (utilisateur.isPresent()) {
            return new ResponseEntity<Utilisateur>(convertUtilisateurToUtilisateurApi(utilisateur.get()), HttpStatus.OK);
        }
        return new ResponseEntity<Utilisateur>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> toggleNotification(@ApiParam(value = "ID de l'utilisateur qui doit être mise à jour", required = true) @PathVariable("utilisateurId") Long utilisateurId, @ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization) {
        String accept = request.getHeader("Accept");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        com.bibliotheque.api.model.Utilisateur utilisateurLog = utilisateurManagement.findUtilisateurByMail(((UserDetails) principal).getUsername());
        if (utilisateurLog.isBibliothecaire() || utilisateurLog.getId() == utilisateurId) {
            utilisateurLog.setNotification(!utilisateurLog.isNotification());
            utilisateurManagement.save(utilisateurLog);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

    }

    public ResponseEntity<Void> updateUtilisateurWithForm(@ApiParam(value = "ID de l'utilisateur qui doit être mis à jour", required = true) @PathVariable("utilisateurId") Long utilisateurId, @ApiParam(value = "Mettre à jour le mail de l'utilisateur") @RequestParam(value = "mail", required = false) String mail, @ApiParam(value = "Mettre à jour le mot de passe de l'utilisateur") @RequestParam(value = "motDePasse", required = false) String motDePasse, @ApiParam(value = "Mettre à jour le prenom de l'utilisateur") @RequestParam(value = "prenom", required = false) String prenom, @ApiParam(value = "Mettre à jour le prenom de l'utilisateur") @RequestParam(value = "nom", required = false) String nom, @ApiParam(value = "Mettre à jour le prenom de l'utilisateur") @RequestParam(value = "dateCreation", required = false) DateTime dateCreation) {
        String accept = request.getHeader("Accept");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            if (utilisateurManagement.findUtilisateurByMail(((UserDetails) principal).getUsername()).isBibliothecaire() ||
                    utilisateurManagement.findUtilisateurByMail(((UserDetails) principal).getUsername()) != null) {
                Optional<com.bibliotheque.api.model.Utilisateur> utilisateur = utilisateurManagement.findById(utilisateurId);
                if (utilisateur.isPresent()) {
                    utilisateur.get().setId(utilisateurId);
                    utilisateur.get().setMotDePasse(motDePasse);
                    utilisateur.get().setMail(mail);
                    utilisateur.get().setPrenom(prenom);
                    utilisateur.get().setDateCreation(dateCreation);
                    utilisateur.get().setNom(nom);
                    utilisateurManagement.save(utilisateur.get());
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Void> connectUser(@NotNull @ApiParam(value = "Trouver un compte par mail", required = true) @Valid @RequestParam(value = "mail", required = true) String mail, @NotNull @ApiParam(value = "Trouver un compte par mail", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        String accept = request.getHeader("Accept");
        if (utilisateurManagement.connexion(mail, password)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    Utilisateur convertUtilisateurToUtilisateurApi(com.bibliotheque.api.model.Utilisateur utilisateur) {
        Utilisateur utilisateurApi = new Utilisateur();

        utilisateurApi.setId(utilisateur.getId());
        utilisateurApi.setDateCreation(utilisateur.getDateCreation());
        utilisateurApi.setMail(utilisateur.getMail());
        utilisateurApi.setMotDePasse(utilisateur.getMotDePasse());
        utilisateurApi.setNom(utilisateur.getNom());
        utilisateurApi.setPrenom(utilisateur.getPrenom());
        utilisateurApi.setNotification(utilisateur.isNotification());
        utilisateurApi.setIsBibliothecaire(utilisateur.isBibliothecaire());
        return utilisateurApi;
    }

    List<Utilisateur> convertListUtilisateurToListUtilisateurApi(List<com.bibliotheque.api.model.Utilisateur> utilisateurs) {
        List<Utilisateur> utilisateursApi = new ArrayList<>();

        for (com.bibliotheque.api.model.Utilisateur utilisateur : utilisateurs) {
            utilisateursApi.add(convertUtilisateurToUtilisateurApi(utilisateur));
        }
        return utilisateursApi;
    }

    com.bibliotheque.api.model.Utilisateur convertUtilisateurApiToUtilisateur(Utilisateur utilisateurApi) {
        com.bibliotheque.api.model.Utilisateur utilisateur = new com.bibliotheque.api.model.Utilisateur();

        utilisateur.setId(utilisateurApi.getId());
        utilisateur.setNom(utilisateurApi.getNom());
        utilisateur.setPrenom(utilisateurApi.getPrenom());
        utilisateur.setDateCreation(utilisateurApi.getDateCreation());
        utilisateur.setMail(utilisateurApi.getMail());
        utilisateur.setMotDePasse(utilisateurApi.getMotDePasse());
        utilisateur.setNotification(utilisateurApi.isNotification());
        utilisateur.setBibliothecaire(utilisateurApi.isIsBibliothecaire());
        return utilisateur;
    }

}