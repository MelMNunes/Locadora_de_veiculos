package br.unisc.locadoraDeVeiculos.repositories;

import br.unisc.locadoraDeVeiculos.entidades.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    boolean existsByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndStatus(Long usuarioId, String status);
    boolean existsByVeiculoIdAndStatus(Long veiculoId, String status);
    @Query("SELECT l FROM Locacao l WHERE l.veiculo.id = :veiculoId AND l.status = :status")
    List<Locacao> findByVeiculoIdAndStatus(@Param("veiculoId") Long veiculoId, @Param("status") String status);

}

