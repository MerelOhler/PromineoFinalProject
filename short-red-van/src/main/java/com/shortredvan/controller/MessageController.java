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
import com.shortredvan.entity.Message;
import com.shortredvan.exception.DuplicateFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
  
@RequestMapping("/message")
@OpenAPIDefinition(info = @Info(title = "Message Service"), 
  servers = { @Server(url = "http://localhost:8080", description = "Local server.")})
public interface MessageController {
  //@formatter:off
  @Operation(
      summary = "Returns a list of messages. User needs to be logged in to access.",
      description = "Returns a list of messages",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of messages is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No messages were found with the input criteria.", 
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
  public List<Message> getAllMessages();
  
  @Operation(
      summary = "Returns a message with that specific id. User needs to be logged in to access.",
      description = "Returns a message given an id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A Message is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No messages were found with the input criteria.", 
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
            description = "Message id")
      }
  )
  @GetMapping("/ByID")
  ResponseEntity<Message> getMessageById(@RequestParam int id);
  
  @Operation(
      summary = "Returns messages that are in a party. User needs to be logged in to access.",
      description = "Returns a list of messages given an party id",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of messages is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No Messages were found with the input criteria.", 
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
  public List<Message> getMessages4PartyId(@RequestParam int id);
  
  @Operation(
      summary = "Returns messages that were created by login user. User needs to be logged in to access.",
      description = "Returns a list of messages given a login user",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of messages is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No Messages were found with the input criteria.", 
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
            description = "login user id")
      }
  )
  @GetMapping("/ByLUId")
  public List<Message> getMessages4LUId(@RequestParam int id);
  
  @Operation(
      summary = "Returns messages that are children of a specific message. User needs to be logged in to access.",
      description = "Returns a list of messages given a parent message",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of messages is returned.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No Messages were found with the input criteria.", 
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
            description = "parent message id")
      }
  )
  @GetMapping("/InParent")
  public List<Message> getMessages4ParentId(@RequestParam int id);
  
  
  
  @Operation(
      summary = "User is able to add a new message to a specific party. User needs to be logged in to access and be in the party.",
      description = "Creates new message",
      responses = {
          @ApiResponse(
              responseCode = "201", 
              description = "Message is created.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))
      }     
  )
  @PostMapping
  public ResponseEntity<Message> createMessage (@RequestBody Message message);
  
  @Operation(
      summary = "User is able to update an existing message. User needs to be the author, "
          + "party admin or general admin to access.",
      description = "returns the updated message, users can only update their own messages",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A login user has been updated.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
              description = "No Messages were found with the input criteria.", 
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
            description = "message id")
      }
  )
  @PutMapping()
  public ResponseEntity<Message> updateMessage (@RequestParam int id, @RequestBody Message message);
  
  @Operation(
      summary = "Message is DATE deleted, and is not actually deleted. User needs to be the author, "
          + "party Admin or general Admin to access.",
      description = "returns a message whether the message has been deleted. User can only delete their own messages",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "Login user has successfully been date deleted.", 
              content = @Content(
                  mediaType = "application/json", 
                  schema = @Schema(implementation = Message.class))),
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
            description = "message id")
      }
  )
  @DeleteMapping()
  public String deleteMessage(int id);

  //@formatter:on

}
