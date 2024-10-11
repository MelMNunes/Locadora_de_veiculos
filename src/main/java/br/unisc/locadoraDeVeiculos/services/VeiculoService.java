package br.unisc.locadoraDeVeiculos.services;

import br.unisc.locadoraDeVeiculos.entidades.Veiculo;
import br.unisc.locadoraDeVeiculos.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo cadastrarVeiculo(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarVeiculoPorId(Long id) {
        return veiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado!"));
    }

    public void atualizarStatusVeiculo(Long id, String status) {
        Veiculo veiculo = buscarVeiculoPorId(id);
        veiculo.setStatus(status);
        veiculoRepository.save(veiculo);
    }

    public Veiculo adicionarVeiculo(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarTodosVeiculos() {
        return veiculoRepository.findAll();
    }

    public List<Veiculo> listarVeiculosDisponiveis() {
        return veiculoRepository.findByStatus("Disponível"); // Assumindo que você tenha esse método no repositório
    }

    public void excluirVeiculo(Long id) {
        Veiculo veiculo = buscarVeiculoPorId(id); // Verifica se o veículo existe
        veiculoRepository.delete(veiculo); // Exclui o veículo
    }


}

