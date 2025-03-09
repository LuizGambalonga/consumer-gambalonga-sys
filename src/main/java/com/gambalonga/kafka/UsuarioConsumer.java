package com.gambalonga.kafka;

import com.gambalonga.usuario.model.UsuarioConfirmacaoModel;
import com.gambalonga.usuario.model.UsuarioModel;
import com.gambalonga.usuario.repository.jpa.UsuarioRepository;
import com.gambalonga.usuario.repository.mongo.UsuarioConfirmacaoRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.gambalonga.usuario.enums.UsuarioAtivo.ATIVADO;

@Component
public class UsuarioConsumer {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConfirmacaoRepository usuarioConfirmacaoRepository;

    public UsuarioConsumer(UsuarioRepository usuarioRepository, UsuarioConfirmacaoRepository usuarioConfirmacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioConfirmacaoRepository = usuarioConfirmacaoRepository;
    }

    @KafkaListener(topics = "usuario-confirmado", groupId = "grupo-consumidor")
    public void ativarUsuario(String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario != null) {
            usuario.setAtivo(ATIVADO.getValor());
            usuarioRepository.save(usuario);
        }

        UsuarioConfirmacaoModel confirmacao = new UsuarioConfirmacaoModel();
        confirmacao.setEmail(email);
        confirmacao.setDataConfirmacao(LocalDateTime.now());
        usuarioConfirmacaoRepository.save(confirmacao);
    }
}