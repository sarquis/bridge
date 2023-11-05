package br.com.sqs.bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAllByOrderByIdDesc();

}
