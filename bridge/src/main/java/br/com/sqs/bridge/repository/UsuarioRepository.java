package br.com.sqs.bridge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sqs.bridge.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAllByOrderByIdDesc();

    List<Usuario> findByEmailContainingOrderByEmailAsc(String email);

    Usuario findByEmail(String email);

}
