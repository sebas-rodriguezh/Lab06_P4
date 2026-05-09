package com.example.backend.repository;

import com.example.backend.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    private final Map<String, Usuario> usuarios = new HashMap<>();

    public UsuarioRepository() {
        Usuario admin = new Usuario("admin", "admin123", "ADMIN");
        Usuario writer = new Usuario("escritor", "escritor123", "WRITER");
        Usuario reader = new Usuario("lector", "lector123", "READER");

        usuarios.put(admin.getUsername(), admin);
        usuarios.put(writer.getUsername(), writer);
        usuarios.put(reader.getUsername(), reader);
    }

    public Optional<Usuario> findByUsername(String username) {
        return Optional.ofNullable(usuarios.get(username));
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            usuario.setId(java.util.UUID.randomUUID().toString().substring(0, 8));
        }
        usuarios.put(usuario.getUsername(), usuario);
        return usuario;
    }
}