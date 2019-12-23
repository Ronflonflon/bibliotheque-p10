package com.bibliotheque.webapp.controller;

import io.swagger.client.api.LivreApi;
import io.swagger.client.api.ReservationApi;
import io.swagger.client.api.UtilisateurApi;
import io.swagger.client.model.Livre;
import io.swagger.client.model.Reservation;
import io.swagger.client.model.Utilisateur;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    private LivreApi serviceLivre;
    private UtilisateurApi serviceUtilisateur;
    private ReservationApi serviceReservation;

    public MainController(LivreApi serviceLivre, UtilisateurApi serviceUtilisateur, ReservationApi serviceReservation) {
        this.serviceLivre = serviceLivre;
        this.serviceUtilisateur = serviceUtilisateur;
        this.serviceReservation = serviceReservation;
    }

    @GetMapping("/")
    public String accueil(Model model, HttpSession session) {
        return "accueil";
    }

    @GetMapping("/profil")
    public String profil(Model model, HttpSession session) {
        try {
            if (session.getAttribute("mail") != null) {
                Utilisateur utilisateur = serviceUtilisateur.findUtilisateursByMail((String) session.getAttribute("mail")).execute().body();

                String base = session.getAttribute("mail") + ":" + session.getAttribute("password");

                assert utilisateur != null;
                List<Reservation> reservations = serviceReservation.findReservations(
                        "Basic " + Base64.getEncoder().encodeToString(base.getBytes())
                        , null, utilisateur.getId())
                        .execute()
                        .body();

                model.addAttribute("utilisateur", utilisateur);
                model.addAttribute("today", new DateTime().getMillis());
                List<com.bibliotheque.webapp.model.Reservation> reservationsUser = convertListReservationApiToListReservation(reservations);
                model.addAttribute("reservations", reservationsUser);
                return "profil";
            }
            return "redirect:/connexion";
        } catch (Exception e) {
            return "failConnexion";
        }
    }


    @GetMapping("/inscription")
    public String inscription() {
        return "inscription";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/connexion")
    public String connexion(HttpSession session) {
        if (session.getAttribute("mail") != null)
            return "redirect:/profil";
        return "connexion";
    }

    @PostMapping("/connexion")
    public String connexionPost(HttpSession session,
                                @RequestParam(value = "mail", required = false, defaultValue = "") String mail,
                                @RequestParam(value = "password", required = false, defaultValue = "") String password) {
        try {
            if (serviceUtilisateur.connectUser(mail, password).execute().code() == 200) {
                session.setAttribute("mail", mail);
                session.setAttribute("password", password);
                return "redirect:/";
            }
            return "redirect:/connexion";
        } catch (Exception e) {
            return "failConnexion";
        }
    }

    @GetMapping("/deconnexion")
    public String deconnexion(HttpSession session) {
        session.removeAttribute("mail");
        session.removeAttribute("password");
        return "redirect:/";
    }

    private com.bibliotheque.webapp.model.Reservation convertReservationApiToReservation(Reservation reservationApi) throws IOException {
        com.bibliotheque.webapp.model.Reservation reservation = new com.bibliotheque.webapp.model.Reservation();
        reservation.setId(reservationApi.getId());
        reservation.setDateCreation(new DateTime(reservationApi.getDateCreation()));
        reservation.setDateDebut((reservationApi.getDateDebut() != null) ? new DateTime(reservationApi.getDateDebut()) : new DateTime(0L));
        reservation.setDateFin((reservationApi.getDateFin() != null) ? new DateTime(reservationApi.getDateFin()) : new DateTime(0L));
        reservation.setRendu(reservationApi.isRendu());
        reservation.setRenouvelable(reservationApi.isRenouvelable());
        reservation.setAttente(reservationApi.isAttente());
        reservation.setUtilisateur(serviceUtilisateur.getUtilisateurById(reservationApi.getUtilisateurId()).execute().body());
        reservation.setLivre(serviceLivre.getLivreById(reservationApi.getLivreId()).execute().body());

        return reservation;
    }

    private List<com.bibliotheque.webapp.model.Reservation> convertListReservationApiToListReservation(List<Reservation> reservationsApi) throws IOException {
        if (reservationsApi != null) {
            List<com.bibliotheque.webapp.model.Reservation> reservations = new ArrayList<>();
            for (Reservation reservation : reservationsApi) {
                reservations.add(convertReservationApiToReservation(reservation));
            }
            return reservations;
        }
        return null;
    }

    private String encodeHeaderAuthorization(HttpSession session) {
        String baseString = session.getAttribute("mail") + ":" + session.getAttribute("password");
        return "Basic " + Base64.getEncoder().encodeToString(baseString.getBytes());
    }
}
