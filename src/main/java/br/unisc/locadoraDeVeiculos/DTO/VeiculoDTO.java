package br.unisc.locadoraDeVeiculos.DTO;

import lombok.Data;

@Data
public class VeiculoDTO {
    private Long id; // Para ADMIN e FUNCIONARIO
    private String modelo;
    private String marca;
    private int anoFabricacao;
    private String placa; // Para ADMIN e FUNCIONARIO
    private String status;

    public VeiculoDTO(String modelo, String marca, int anoFabricacao, String status) {
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacao = anoFabricacao;
        this.status = status;
    }

    public VeiculoDTO(Long id, String modelo, String marca, int anoFabricacao, String placa, String status) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacao = anoFabricacao;
        this.placa = placa;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
