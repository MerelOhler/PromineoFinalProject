package com.shortredvan.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.shortredvan.entity.LoginUser;
import com.shortredvan.entity.Party;
import com.shortredvan.exception.DuplicateFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@RequestMapping("/party")
@OpenAPIDefinition(info = @Info(title = "Party Service"), 
  servers = { @Server(url = "http://localhost:8080", description = "Local server.")})
public interface PartyController {
  //@formatter:off
  
  @Operation(
      summary = "Returns a list of Parties",
      description = "Returns a list of Parties",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of Parties is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No Party was found with the input criteria.", 
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
  List<Party> getAllParties();
  
  @Operation(
      summary = "Returns a Party with that specific id",
      description = "Returns a a Party given an id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A Party is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
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
            name = "id", 
            allowEmptyValue = false, 
            required = true, 
            description = "Party id")
      }
  )
  @GetMapping("/ByID")
  ResponseEntity<Party> getPartyById(@RequestParam int id);
  
  @Operation(
      summary = "User is able to add a Party",
      description = "returns the new Party",
      responses = {
          @ApiResponse(
              responseCode = "201", 
              description = "Party is created.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
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
      }   
  )
  @PostMapping
  public ResponseEntity<Party> createParty (Party party) throws DuplicateFoundException;
  
  @Operation(
      summary = "User is able to update an existing party",
      description = "returns the updated Party",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "Party has been updated.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No Party was found with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "409", 
              description = "Provided name is not unique.", 
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
              name = "id", 
              allowEmptyValue = false, 
              required = true, 
              description = "Party Id")
          }
  )
  @PutMapping()
  public ResponseEntity<Party> updateParty (@RequestParam int id, @RequestBody Party party) throws DuplicateFoundException;

  @Operation(
      summary = "User is able to delete an existing Party",
      description = "returns a message whether the party has been deleted",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "party has successfully been deleted.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No Login users were found with the input criteria.", 
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
              name = "id", 
              allowEmptyValue = false, 
              required = true, 
              description = "Party Id")
          }
  )
  @DeleteMapping()
  public String deleteParty(@RequestParam int id);
  
  @Operation(
      summary = "Find all parties belonging to a login user id",
      description = "returns a list of parties that a login user belongs to",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "list of loginusers has been returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Party.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No parties were found with the input criteria.", 
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
              name = "id", 
              allowEmptyValue = false, 
              required = true, 
              description = "LoginUser Id")
          }
  )
  @GetMapping("/getParties")
  public List<Party> getpartiesByLU (@RequestParam int id);
  
  //@formatter:on

}
