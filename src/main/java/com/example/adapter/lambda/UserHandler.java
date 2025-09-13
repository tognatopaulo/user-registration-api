package com.example.adapter.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.adapter.http.api.dto.UserRequest;
import com.example.adapter.http.api.mapper.UserMapper;
import com.example.domain.application.UserService;
import com.example.infra.persistence.UserRepositoryDynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class UserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final Logger log = LoggerFactory.getLogger(UserHandler.class);

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserHandler() {
        DynamoDbClient dynamoDbClient = DynamoDbClient.create();
        UserRepositoryDynamoDB userRepository = new UserRepositoryDynamoDB(dynamoDbClient);
        this.userService = new UserService(userRepository);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            log.info("Received request: {}", objectMapper.writeValueAsString(event));
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