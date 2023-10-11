package br.com.sqs.bridge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
