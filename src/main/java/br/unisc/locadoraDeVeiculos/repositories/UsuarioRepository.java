package br.unisc.locadoraDeVeiculos.repositories;

import br.unisc.locadoraDeVeiculos.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Para verificar emails duplicados
}
