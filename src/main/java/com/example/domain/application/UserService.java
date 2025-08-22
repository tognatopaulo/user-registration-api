package com.example.domain.application;

import com.example.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        // Aqui você pode adicionar a lógica para buscar usuários do banco de dados
        // Por enquanto, vamos retornar a lista de usuários já registrada
        return userList;
    }

    public User registerUser(User request) {
        // Aqui você pode adicionar a lógica para salvar o usuário no banco de dados
        // Por enquanto, vamos apenas retornar o usuário recebido
        userList.add(request);
        return request;
    }
}
