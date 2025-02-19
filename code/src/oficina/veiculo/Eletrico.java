package oficina.veiculo;

import DAOs.InformacaoDAO;

import java.util.List;
import java.util.Map;

public class Eletrico extends Veiculo {

    private int motorEletricoPotencia;

    public Eletrico(String matricula, String modelo, String marca, InformacaoDAO fichaVeiculo, String tipoMotor, int motorEletricoPotencia)
    {
        super(matricula,modelo,marca,fichaVeiculo, tipoMotor);
        this.motorEletricoPotencia = motorEletricoPotencia;
    }

    public int getMotorEletricoPotencia() {
        return motorEletricoPotencia;
    }

    public void setMotorEletricoPotencia(int motorEletricoPotencia) {
        this.motorEletricoPotencia = motorEletricoPotencia;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o.getClass() != MotorGasolina.class) {
            return false;
        }
        Eletrico c = (Eletrico) o;
        return  c.getMatricula() == this.getMatricula() &&
                c.getModelo() == this.getModelo() &&
                c.getMarca() == this.getMarca() &&
                c.getFichaVeiculo() == this.getFichaVeiculo() &&
                c.getMotorEletricoPotencia() == this.motorEletricoPotencia;
    }

    public Eletrico clone() {
        return new Eletrico(super.getMatricula(),super.getModelo(),super.getMarca(),super.getFichaVeiculo(),this.getTipoMotor(),this.motorEletricoPotencia);
    }

    public boolean isHibrid(){
        return false;
    }

    public String toString()
    {
        return "Veiculo " + getMatricula() + " - Modelo: " + this.getModelo() + "; Marca: " + getMarca() + "; Tipo Motor: " + getTipoMotor();
    }
}

