package com.example.backend.controller;

import com.example.backend.model.AuthRequest;
import com.example.backend.model.AuthResponse;
import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UsuarioRepository usuarioRepository, JwtUtil jwtUtil)
    {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            System.out.println("INTENTO DE LOGIN - Usuario recibido: [" + request.getUsername() + "]");
            System.out.println("INTENTO DE LOGIN - Clave recibida: [" + request.getPassword() + "]");
        } catch (Exception e) {
            throw new Exception("Usuario o contraseña incorrectos");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String rolLimpio = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        final String jwt = jwtUtil.generarToken(userDetails.getUsername(), rolLimpio);
        return new AuthResponse(jwt, userDetails.getUsername(), rolLimpio);
    }

    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario nuevoUsuario) throws Exception
    {
        if (usuarioRepository.findByUsername(nuevoUsuario.getUsername()).isPresent()) {
            throw new Exception("El nombre de usuario ya está en uso");
        }
        return usuarioRepository.save(nuevoUsuario);
    }
}