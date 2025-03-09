package com.gambalonga.kafka;

import com.gambalonga.usuario.enums.UsuarioAtivo;
import com.gambalonga.usuario.model.UsuarioModel;
import com.gambalonga.usuario.repository.UsuarioRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.gambalonga.usuario.enums.UsuarioAtivo.ATIVADO;

@Component
public class UsuarioConsumer {

    private final UsuarioRepository usuarioRepository;

    public UsuarioConsumer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @KafkaListener(topics = "usuario-confirmado", groupId = "grupo-consumidor")
    public void ativarUsuario(String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            usuario.setAtivo(ATIVADO.getValor());
            usuarioRepository.save(usuario);
        }
    }
}