package br.unisc.locadoraDeVeiculos.DTO;

import br.unisc.locadoraDeVeiculos.entidades.Usuario;

import java.time.LocalDate;

public class LocacaoDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private String veiculoModelo;
    private String veiculoMarca;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;

    public LocacaoDTO(Long id, Long usuarioId, String usuarioNome, String veiculoModelo, String veiculoMarca, LocalDate dataInicio, LocalDate dataFim, String status) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.veiculoModelo = veiculoModelo;
        this.veiculoMarca = veiculoMarca;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getVeiculoModelo() {
        return veiculoModelo;
    }

    public void setVeiculoModelo(String veiculoModelo) {
        this.veiculoModelo = veiculoModelo;
    }

    public String getVeiculoMarca() {
        return veiculoMarca;
    }

    public void setVeiculoMarca(String veiculoMarca) {
        this.veiculoMarca = veiculoMarca;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Você pode querer remover esses métodos, a menos que tenha um propósito específico para eles
    public Usuario getUsuario() {
        // Retornar um objeto Usuario, se aplicável
        return null; // Aqui você deve retornar um objeto Usuario válido se aplicável
    }

    public VeiculoDTO getVeiculo() {
        // Retornar um objeto VeiculoDTO, se aplicável
        return null; // Aqui você deve retornar um objeto VeiculoDTO válido se aplicável
    }
}
