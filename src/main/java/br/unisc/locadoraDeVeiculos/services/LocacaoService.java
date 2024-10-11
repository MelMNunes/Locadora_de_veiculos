package br.unisc.locadoraDeVeiculos.services;

import br.unisc.locadoraDeVeiculos.entidades.Locacao;
import br.unisc.locadoraDeVeiculos.entidades.Veiculo;
import br.unisc.locadoraDeVeiculos.repositories.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private VeiculoService veiculoService;

    public Locacao solicitarLocacao(Locacao locacao) {
        Veiculo veiculo = veiculoService.buscarVeiculoPorId(locacao.getVeiculo().getId());

        if (!"Disponível".equals(veiculo.getStatus())) {
            throw new RuntimeException("Veículo indisponível para locação");
        }

        boolean temLocacaoAtiva = locacaoRepository.existsByUsuarioIdAndStatus(locacao.getUsuario().getId(), "Aprovada");
        if (temLocacaoAtiva) {
            throw new RuntimeException("O usuário já possui uma locação ativa");
        }

        locacao.setStatus("Pendente");
        return locacaoRepository.save(locacao);
    }

    public Locacao buscarLocacaoPorId(Long id) {
        return locacaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Locação não encontrada!"));
    }

    public void aprovarLocacao(Long id) {
        Locacao locacao = buscarLocacaoPorId(id);
        if ("Aprovada".equals(locacao.getStatus())) {
            throw new RuntimeException("Esta locação já foi aprovada.");
        }
        locacao.setStatus("Aprovada");

        Veiculo veiculo = locacao.getVeiculo();
        veiculo.setStatus("Locado");
        veiculoService.atualizarStatusVeiculo(veiculo.getId(), "Locado");

        locacaoRepository.save(locacao);
    }

    // Método que lista todas as locações
    public List<Locacao> listarLocacoes() {
        return locacaoRepository.findAll();
    }

    public boolean verificaVeiculoTemLocacoesAtivas(Long veiculoId) {
        // Verifica se há locações ativas associadas ao veículo
        return locacaoRepository.existsByVeiculoIdAndStatus(veiculoId, "Aprovada");
    }

    public void excluirLocacao(Long id) {
        Locacao locacao = buscarLocacaoPorId(id);
        locacaoRepository.delete(locacao);
    }

    public void deixarVeiculoDisponivel(Long veiculoId) {
        // Encontre o veículo pelo ID
        Veiculo veiculo = veiculoService.buscarVeiculoPorId(veiculoId);

        // Atualiza o status do veículo para "Disponível"
        veiculo.setStatus("Disponível");
        veiculoService.atualizarStatusVeiculo(veiculoId, "Disponível");

        // Exclui a locação associada
        List<Locacao> locacoesAtivas = locacaoRepository.findByVeiculoIdAndStatus(veiculoId, "Pendente");
        for (Locacao locacao : locacoesAtivas) {
            locacaoRepository.delete(locacao);
        }
    }


}

