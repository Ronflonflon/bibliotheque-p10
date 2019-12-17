/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.7).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.bibliotheque.api.service.api;

import com.bibliotheque.api.service.model.Reservation;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-13T16:21:57.354Z")

@Api(value = "reservations", description = "the reservations API")
public interface ReservationsApi {

    @ApiOperation(value = "Ajouter une nouvelle réservation", nickname = "addReservation", notes = "", authorizations = {
            @Authorization(value = "basicAuth")
    }, tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(value = "/reservations",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> addReservation(@ApiParam(value = "Un objet Reservation doit être envoyé pour être ajouté", required = true) @Valid @RequestBody Reservation body, @ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);

    @ApiOperation(value = "Mettre à jour un livre avec un form data", nickname = "comingBack", notes = "", tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 403, message = "La réservation ne peut pas être renouvelée"),
            @ApiResponse(code = 404, message = "Not found")})
    @RequestMapping(value = "/reservations/{reservationId}/comingBack",
            produces = {"application/json"},
            consumes = {"application/x-www-form-urlencoded"},
            method = RequestMethod.PATCH)
    ResponseEntity<Void> comingBack(@ApiParam(value = "ID de la réservation qui doit être mise à jour", required = true) @PathVariable("reservationId") Long reservationId);


    @ApiOperation(value = "Supprimer une réservation", nickname = "deleteReservation", notes = "", authorizations = {
            @Authorization(value = "basicAuth")
    }, tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "livre not found")})
    @RequestMapping(value = "/reservations/{reservationId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteReservation(@ApiParam(value = "ID du livre à supprimer", required = true) @PathVariable("reservationId") Long reservationId, @ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "Récupérer la liste de réservations expirées", nickname = "expiredReservation", notes = "", response = Reservation.class, responseContainer = "List", tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Aucune réservation expirée trouvée")})
    @RequestMapping(value = "/reservations/expired",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Reservation>> expiredReservation();


    @ApiOperation(value = "Trouver des réservations", nickname = "findReservations", notes = "Plusieurs valeurs peuvent être séparées par une virgule", response = Reservation.class, responseContainer = "List", authorizations = {
            @Authorization(value = "basicAuth")
    }, tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @RequestMapping(value = "/reservations",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Reservation>> findReservations(@ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization, @ApiParam(value = "Réservations faites sur l'id d'un livre") @Valid @RequestParam(value = "livreId", required = false) Long livreId, @ApiParam(value = "Réservations faites par un utilisateur") @Valid @RequestParam(value = "utilisateurId", required = false) Long utilisateurId);


    @ApiOperation(value = "Trouve une réservation par son ID", nickname = "getReservationById", notes = "Trouve une réservation par son ID", response = Reservation.class, authorizations = {
            @Authorization(value = "basicAuth")
    }, tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "livre not found")})
    @RequestMapping(value = "/reservations/{reservationId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Reservation> getReservationById(@ApiParam(value = "ID of livre to return", required = true) @PathVariable("reservationId") Long reservationId, @ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "Mettre à jour un livre avec un form data", nickname = "renewReservation", notes = "", authorizations = {
            @Authorization(value = "basicAuth")
    }, tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 403, message = "La réservation ne peut pas être renouvelée"),
            @ApiResponse(code = 404, message = "Not found")})
    @RequestMapping(value = "/reservations/{reservationId}/renew",
            produces = {"application/json"},
            consumes = {"application/x-www-form-urlencoded"},
            method = RequestMethod.PATCH)
    ResponseEntity<Void> renewReservation(@ApiParam(value = "ID de la réservation qui doit être mise à jour", required = true) @PathVariable("reservationId") Long reservationId, @ApiParam(value = "Envoie de l'utilisateur faisant le requête", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "Mettre à jour un livre avec un form data", nickname = "toggleRenouvelableReservation", notes = "", tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 403, message = "La réservation ne peut pas être renouvelée"),
            @ApiResponse(code = 404, message = "Not found")})
    @RequestMapping(value = "/reservations/{reservationId}/toggleRenouvelable",
            produces = {"application/json"},
            consumes = {"application/x-www-form-urlencoded"},
            method = RequestMethod.PATCH)
    ResponseEntity<Void> toggleRenouvelableReservation(@ApiParam(value = "ID de la réservation qui doit être mise à jour", required = true) @PathVariable("reservationId") Long reservationId);

    @ApiOperation(value = "Récupérer la liste de réservations en cours d'un livre", nickname = "getReservationsOfaBookInProgress", notes = "", response = Reservation.class, responseContainer = "List", tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Aucune réservation expirée trouvée")})
    @RequestMapping(value = "/reservations/{livreId}/progress",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Reservation>> getReservationsOfaBookInProgress(@ApiParam(value = "Réservations faites sur l'id d'un livre", required = true) @PathVariable("livreId") Long livreId);

    @ApiOperation(value = "Récupérer la liste de réservations en attente d'un livre", nickname = "getReservationsOfaBookWaiting", notes = "", response = Reservation.class, responseContainer = "List", tags = {"reservation",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Reservation.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Aucune réservation expirée trouvée")})
    @RequestMapping(value = "/reservations/waiting",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Reservation>> getReservationsOfaBookWaiting(@NotNull @ApiParam(value = "Réservations faites sur l'id d'un livre", required = true) @Valid @RequestParam(value = "livreId", required = true) Long livreId);
}