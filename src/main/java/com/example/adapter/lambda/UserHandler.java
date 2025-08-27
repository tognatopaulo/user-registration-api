package com.example.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.adapter.http.api.dto.UserRequest;
import com.example.adapter.http.api.mapper.UserMapper;
import com.example.domain.application.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    @Inject
    UserService userService;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            UserRequest userRequest = objectMapper.readValue(event.getBody(), UserRequest.class);
            var userEntity = UserMapper.toEntity(userRequest);
            var user = userService.registerUser(userEntity);
            var userResponse = UserMapper.toResponse(user);

            String responseBody = objectMapper.writeValueAsString(userResponse);
            log.info("Usuário registrado com sucesso: {}", responseBody);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(201)
                    .withBody(responseBody);

        } catch (Exception e) {
            log.error("Erro ao registrar usuário", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(400)
                    .withBody("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
