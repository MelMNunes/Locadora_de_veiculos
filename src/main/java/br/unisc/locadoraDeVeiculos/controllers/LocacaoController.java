package br.unisc.locadoraDeVeiculos.controllers;

import br.unisc.locadoraDeVeiculos.DTO.LocacaoDTO;
import br.unisc.locadoraDeVeiculos.entidades.Locacao;
import br.unisc.locadoraDeVeiculos.services.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping
    public ResponseEntity<Locacao> solicitarLocacao(@RequestBody Locacao locacao) {
        return new ResponseEntity<>(locacaoService.solicitarLocacao(locacao), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<String> aprovarLocacao(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario) && !"FUNCIONARIO".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores ou funcionários podem aprovar locações");
        }
        locacaoService.aprovarLocacao(id);
        return ResponseEntity.ok("Locação aprovada com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<LocacaoDTO>> listarLocacoes() {
        List<Locacao> locacoes = locacaoService.listarLocacoes(); // Corrigido aqui
        List<LocacaoDTO> locacaoDTOs = locacoes.stream().map(locacao -> {
            return new LocacaoDTO(
                    locacao.getId(),
                    locacao.getUsuario().getId(),
                    locacao.getUsuario().getNome(),
                    locacao.getVeiculo().getModelo(),
                    locacao.getVeiculo().getMarca(),
                    locacao.getDataInicio(),
                    locacao.getDataFim(),
                    locacao.getStatus()
            );
        }).collect(Collectors.toList());
        return ResponseEntity.ok(locacaoDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirLocacao(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem excluir locações");
        }
        locacaoService.excluirLocacao(id);
        return ResponseEntity.ok("Locação excluída com sucesso");
    }

    @PutMapping("/{id}/disponibilizar")
    public ResponseEntity<String> disponibilizarVeiculo(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem disponibilizar veículos");
        }
        locacaoService.deixarVeiculoDisponivel(id);
        return ResponseEntity.ok("Veículo disponibilizado com sucesso");
    }


}
