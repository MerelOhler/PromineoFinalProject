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
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RequestMapping("/loginuser")
@OpenAPIDefinition(info = @Info(title = "LoginUser Service"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface LoginUserController {
  //@formatter:off
  @Operation(
      summary = "Returns a list of LoginUsers. User needs to be logged in to access.",
      description = "Returns a list of LoginUsers",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of LoginUsers is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "403", 
              description = "The user is not logged in or doesn't have the correct privileges.", 
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
      }
  )  
  @GetMapping
  public List<LoginUser> getAllLU();
  
  @Operation(
      summary = "Returns a LoginUser with that specific id. User needs to be logged in to access.",
      description = "Returns a a LoginUsers given an id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A LoginUser is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "403", 
              description = "The user is not logged in or doesn't have the correct privileges.", 
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
            description = "Login user id")
      }
  )
  @GetMapping("/ByID")
  ResponseEntity<LoginUser> getLUById(@RequestParam int id);
  
  @Operation(
      summary = "Returns LoginUsers that are in the party. User needs to be logged in to access.",
      description = "Returns a list of LoginUsers given an party id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of LoginUsers is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "403", 
              description = "The user is not logged in or doesn't have the correct privileges.", 
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
            description = "Party id")
      }
  )
  @GetMapping("/InParty")
  public List<LoginUser> getLoginUsers4PartyId(@RequestParam int id);
  
  @Operation(
      summary = "User is able to login with an existing LoginUser",
      description = "returns whether login was successfull or not",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "the user is logged in.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
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
              name = "email", 
              allowEmptyValue = false, 
              required = true, 
              description = "Login user Email"),
          @Parameter(
              name = "password", 
              allowEmptyValue = false, 
              required = true, 
              description = "Login user Password")
          }      
  )
  @GetMapping("/login")
  public boolean login(@RequestParam String email, @RequestParam String password);
  
  @Operation(
      summary = "User is able to logout. User needs to be logged in to access.",
      description = "does not return anything, logs out current user",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of LoginUsers is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      }    
  )
  @GetMapping("/logout")
  public String logout();
  
  @Operation(
      summary = "User is able to add a new login user and log in as the new loginuser. If User is already logged "
          + "they will first be logged out.",
      description = "Creates new login user and logs in as the new login user. Login user can only be created"
          + "by the user themselves.",
      responses = {
          @ApiResponse(
              responseCode = "201", 
              description = "LoginUser is created.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "409", 
              description = "Provided email is not unique.", 
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
  public ResponseEntity<LoginUser> createLU (@RequestBody LoginUser loginUser) throws DuplicateFoundException;
  
  @Operation(
      summary = "User is able to update the current login user. User needs to be logged in to access. ",
      description = "returns the updated loginuser",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A login user has been updated.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "403", 
              description = "The user is not logged in or doesn't have the correct privileges.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "No Login users were found with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "409", 
              description = "Provided email is not unique.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      }
  )
  @PutMapping()
  public ResponseEntity<LoginUser> updateLU (@RequestBody LoginUser loginUser) throws DuplicateFoundException;
  
  @Operation(
      summary = "Current LoginUser is DATE deleted, and is not actually deleted. User needs to be logged in to access.",
      description = "returns a message whether the user has been deleted. User can only delete their own account",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "Login user has successfully been date deleted.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = LoginUser.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "403", 
              description = "The user is not logged in or doesn't have the correct privileges.", 
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
      }
  )
  @DeleteMapping()
  public String deleteLU();

  //@formatter:on

}
