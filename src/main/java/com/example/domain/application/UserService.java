package com.example.domain.application;

import com.example.adapter.persistence.UserRepository;
import com.example.domain.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> getUserList() {
        // Aqui você pode adicionar a lógica para buscar usuários do banco de dados
        // Por enquanto, vamos retornar a lista de usuários já registrada
        return null;
    }

    public User registerUser(User request) {
        // Aqui você pode adicionar a lógica para salvar o usuário no banco de dados
        // Por enquanto, vamos apenas retornar o usuário recebido
        userRepository.saveUser(request);
        return request;
    }
}
