package com.example.adapter.http.api;

import com.example.adapter.http.api.dto.UserRequest;
import com.example.adapter.http.api.mapper.UserMapper;
import com.example.domain.application.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/users")
public class UsersResource {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(UsersResource.class);

    private final UserService userService;

    public UsersResource(UserService userService) {
        this.userService = userService;
    }


    @GET
    public Response getUsers() {
        log.info("getUsers");
        var response = userService.getUserList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") String id) {
        // Aqui você pode buscar o usuário pelo ID no banco de dados ou serviço
        // Por enquanto, vamos retornar uma resposta simulada
        return Response.ok("User with ID: " + id).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@Valid UserRequest request) throws JsonProcessingException {
        log.info("Starting registering User: {}", objectMapper.writeValueAsString(request));
        var userRequest = UserMapper.toEntity(request);
        var response = userService.registerUser(userRequest);
        var userResponse = UserMapper.toResponse(response);
        log.info("User registered successfully: {}", objectMapper.writeValueAsString(response));
        return Response.ok(userResponse).build();
    }
}
