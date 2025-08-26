package com.example.infra.persistence;

import com.example.adapter.persistence.UserRepository;
import com.example.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.*;

@ApplicationScoped
public class UserRepositoryDynamoDB implements UserRepository {

    private static final String TABLE_NAME = "users";

    @Inject
    DynamoDbClient dynamoDbClient;

    @Override
    public void saveUser(User user) {
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(UUID.randomUUID().toString());
        }

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("userId", AttributeValue.fromS(String.valueOf(user.getUserId())));
        item.put("userName", AttributeValue.fromS(user.getUserName()));
        item.put("email", AttributeValue.fromS(user.getEmail()));
        item.put("password", AttributeValue.fromS(user.getPassword()));
        item.put("isActive", AttributeValue.fromBool(user.isActive()));
        item.put("activationCode", AttributeValue.fromS(user.getActivationCode()));

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }
}
