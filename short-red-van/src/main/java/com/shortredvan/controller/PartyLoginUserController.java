package com.shortredvan.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.shortredvan.entity.PartyLoginUser;
import com.shortredvan.entity.PartyLoginUserKey;
import com.shortredvan.exception.DuplicateFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@RequestMapping("/partyloginuser")
@OpenAPIDefinition(info = @Info(title = "PartyLoginUser Service"), 
  servers = { @Server(url = "http://localhost:8080", description = "Local server.")})
public interface PartyLoginUserController {
  //@formatter:off
  
  @Operation(
      summary = "Returns a list of PartyUserLogins",
      description = "Returns a list of PartyUserLogins",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of PartyUserLogins is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = PartyLoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No PartyUserLogins was found with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      }
  )  
  @GetMapping
  List<PartyLoginUser> getAllPLUs();
  
  @Operation(
      summary = "Returns a PartyUserLogin with that specific id",
      description = "Returns a a PartyUserLogin given an id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A Party is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = PartyLoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No Parties were found with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "partyId", 
              allowEmptyValue = false, 
              required = true, 
              description = "Party Id"),
          @Parameter(
              name = "loginUserId", 
              allowEmptyValue = false, 
              required = true, 
              description = "Login User Id")
     }
  )
  @GetMapping("/ByID")
  ResponseEntity<PartyLoginUser> getPLUById(@RequestParam int partyId, @RequestParam int loginUserId);
  
  @Operation(
      summary = "User is able to add a connection between a loginuser and a party",
      description = "returns the new PartyUserLogin",
      responses = {
          @ApiResponse(
              responseCode = "201", 
              description = "PartyUserLogin is created.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = PartyLoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "409", 
              description = "Provided Party name is not unique.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      },
          parameters = {
              @Parameter(
                  name = "partyId", 
                  allowEmptyValue = false, 
                  required = true, 
                  description = "Party Id"),
              @Parameter(
                  name = "loginUserId", 
                  allowEmptyValue = false, 
                  required = true, 
                  description = "Login User Id")
         }
  )
  @PostMapping
  public ResponseEntity<PartyLoginUser> createPLU (@RequestParam int partyId, @RequestParam int loginUserId) throws DuplicateFoundException;

  @Operation(
      summary = "User is able to delete an existing connection between a party and a loginuser",
      description = "returns a message whether the connection between party and loginuser has been deleted",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "connection has successfully been deleted.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = PartyLoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No PartyLoginUsers users were found with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "partyId", 
              allowEmptyValue = false, 
              required = true, 
              description = "Party Id"),
          @Parameter(
              name = "loginUserId", 
              allowEmptyValue = false, 
              required = true, 
              description = "Login User Id")
     }
  )
  @DeleteMapping()
  public String deletePLU(@RequestParam int PartyId, @RequestParam int loginUserId);
  
  //@formatter:on

}
