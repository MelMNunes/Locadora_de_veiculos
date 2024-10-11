package br.unisc.locadoraDeVeiculos.controllers;

import br.unisc.locadoraDeVeiculos.DTO.VeiculoDTO;
import br.unisc.locadoraDeVeiculos.entidades.Veiculo;
import br.unisc.locadoraDeVeiculos.services.LocacaoService;
import br.unisc.locadoraDeVeiculos.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping("/adicionar")
    public ResponseEntity<String> adicionarVeiculo(@RequestParam String tipoUsuario, @RequestBody Veiculo veiculo) {
        if (!"ADMIN".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem adicionar veículos");
        }
        veiculoService.adicionarVeiculo(veiculo);
        return ResponseEntity.ok("Veículo adicionado com sucesso");
    }

    // Endpoint para listar veículos com base no papel do usuário
    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> listarVeiculos(@RequestParam(required = false) String role) {
        if (role == null) {
            return ResponseEntity.badRequest().body(null); // Retorna erro se o parâmetro não for informado
        }

        List<Veiculo> veiculos;
        switch (role.toUpperCase()) {
            case "ADMIN":
                veiculos = veiculoService.listarTodosVeiculos();
                break;
            case "FUNCIONARIO":
                veiculos = veiculoService.listarVeiculosDisponiveis();
                break;
            case "USUARIO":
                veiculos = veiculoService.listarVeiculosDisponiveis();
                break;
            default:
                return ResponseEntity.badRequest().body(null);
        }

        // Mapeia os veículos para DTOs
        List<VeiculoDTO> veiculoDTOs = veiculos.stream().map(veiculo -> {
            if ("USUARIO".equals(role)) {
                return new VeiculoDTO(veiculo.getModelo(), veiculo.getMarca(), veiculo.getAnoFabricacao(), veiculo.getStatus());
            } else {
                return new VeiculoDTO(veiculo.getId(), veiculo.getModelo(), veiculo.getMarca(), veiculo.getAnoFabricacao(), veiculo.getPlaca(), veiculo.getStatus());
            }
        }).collect(Collectors.toList());

        return ResponseEntity.ok(veiculoDTOs); // Retorna a lista de DTOs
    }

    // Endpoint para atualizar o status de um veículo
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        veiculoService.atualizarStatusVeiculo(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<String> aprovarLocacao(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario) && !"FUNCIONARIO".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores ou funcionários podem aprovar locações");
        }
        locacaoService.aprovarLocacao(id);
        return ResponseEntity.ok("Locação aprovada com sucesso");
    }

    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<String> excluirVeiculo(@RequestParam String tipoUsuario, @PathVariable Long id) {
        if (!"ADMIN".equals(tipoUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: Apenas administradores podem excluir veículos");
        }

        // Verifique se o veículo tem locações ativas
        if (locacaoService.verificaVeiculoTemLocacoesAtivas(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível excluir este veículo, pois ele está associado a locações ativas.");
        }

        veiculoService.excluirVeiculo(id);
        return ResponseEntity.ok("Veículo excluído com sucesso");
    }


}
