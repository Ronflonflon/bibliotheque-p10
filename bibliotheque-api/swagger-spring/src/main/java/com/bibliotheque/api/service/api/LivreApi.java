/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.7).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.bibliotheque.api.service.api;

import com.bibliotheque.api.service.model.Livre;
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

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-08-05T10:23:06.042Z")

@Api(value = "livre", description = "the livre API")
public interface LivreApi {

    @ApiOperation(value = "Ajouter un nouveau livre", nickname = "addLivre", notes = "", tags = {"livre",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(value = "/livre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> addLivre(@ApiParam(value = "Un objet Livre doit être envoyé pour être ajouté", required = true) @Valid @RequestBody Livre body);


    @ApiOperation(value = "Supprimer un livre", nickname = "deletelivre", notes = "", tags = {"livre",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "livre not found")})
    @RequestMapping(value = "/livre/{livreId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deletelivre(@ApiParam(value = "ID du livre à supprimer", required = true) @PathVariable("livreId") Long livreId, @ApiParam(value = "") @RequestHeader(value = "api_key", required = false) String apiKey);


    @ApiOperation(value = "Lister des livres par critères", nickname = "findlivres", notes = "Plusieurs valeurs peuvent être séparées par une virgule", response = Livre.class, responseContainer = "List", tags = {"livre",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Livre.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @RequestMapping(value = "/livre",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Livre>> findlivres(@ApiParam(value = "ID of livre to return", required = true) @PathVariable("livreId") Long livreId);


    @ApiOperation(value = "Trouve un livre par son ID", nickname = "getlivreById", notes = "Trouve un livre par son ID", response = Livre.class, tags = {"livre",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Livre.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "livre not found")})
    @RequestMapping(value = "/livre/{livreId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Livre> getlivreById(@ApiParam(value = "ID of livre to return", required = true) @PathVariable("livreId") Long livreId);


    @ApiOperation(value = "Mettre à jour un livre avec un form data", nickname = "updatelivreWithForm", notes = "", tags = {"livre",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(value = "/livre/{livreId}",
            produces = {"application/json"},
            consumes = {"application/x-www-form-urlencoded"},
            method = RequestMethod.PUT)
    ResponseEntity<Void> updatelivreWithForm(@ApiParam(value = "ID du livre qui doit être mis à jour", required = true) @PathVariable("livreId") Long livreId, @ApiParam(value = "Mettre à jour le nom du livre") @RequestParam(value = "name", required = false) String name, @ApiParam(value = "Mettre à jour l'auteur d'un livre") @RequestParam(value = "auteur", required = false) String auteur, @ApiParam(value = "Mettre à jour la quantité d'un livre") @RequestParam(value = "quantite", required = false) Integer quantite);

}