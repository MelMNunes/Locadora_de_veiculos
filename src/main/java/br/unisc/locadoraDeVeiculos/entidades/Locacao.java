package br.unisc.locadoraDeVeiculos.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status; // Aprovada, Pendente, Rejeitada
}
