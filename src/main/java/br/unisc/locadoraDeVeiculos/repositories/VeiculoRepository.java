package br.unisc.locadoraDeVeiculos.repositories;

import br.unisc.locadoraDeVeiculos.entidades.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByStatus(String status); // Para filtrar veículos pelo status
    }


