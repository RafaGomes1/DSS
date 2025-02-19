package oficina.veiculo;

import DAOs.InformacaoDAO;

import java.util.List;
import java.util.Map;

public abstract class Veiculo {
    private String matricula;
    private String modelo;
    private String marca;
    private InformacaoDAO fichaVeiculo;
    private String tipoMotor;

    public Veiculo(String matricula, String modelo, String marca, InformacaoDAO fichaVeiculo, String tipoMotor) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.fichaVeiculo = fichaVeiculo;
        this.tipoMotor = tipoMotor;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public InformacaoDAO getFichaVeiculo() {
        return fichaVeiculo;
    }


    public void addInformacao(Informacao i) {
        this.fichaVeiculo.put(i.getIdInformacao(),i);
    }

    public String getTipoMotor() {
        return tipoMotor;
    }

    public void setTipoMotor(String tipoMotor) {
        this.tipoMotor = tipoMotor;
    }

    public abstract boolean equals(Object o);

    public abstract Veiculo clone();

    public abstract boolean isHibrid();

    public abstract String toString();
}
