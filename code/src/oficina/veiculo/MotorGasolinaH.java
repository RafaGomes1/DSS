package oficina.veiculo;

import DAOs.InformacaoDAO;

import java.util.List;
import java.util.Map;

public class MotorGasolinaH extends MotorGasolina implements Hibrido {

    private int motorEletricoPotencia;

    public MotorGasolinaH(String matricula, String modelo, String marca, InformacaoDAO fichaVeiculo, String tipoMotor, int motorCombustaoPotencia, int motorEletricoPotencia) {
        super(matricula,modelo,marca,fichaVeiculo, tipoMotor, motorCombustaoPotencia);
        this.motorEletricoPotencia = motorEletricoPotencia;
    }

    public int getMotorEletricoPotencia() {
        return motorEletricoPotencia;
    }

    public void setMotorEletricoPotencia(int motorEletricoPotencia) {
        this.motorEletricoPotencia = motorEletricoPotencia;
    }

    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o.getClass() != MotorGasolinaH.class) {
            return false;
        }

        MotorGasolinaH c = (MotorGasolinaH) o;
        return  c.getMatricula() == this.getMatricula() &&
                c.getModelo() == this.getModelo() &&
                c.getMarca() == this.getMarca() &&
                c.getFichaVeiculo() == this.getFichaVeiculo() &&
                c.getMotorCombustaoPotencia() == this.getMotorCombustaoPotencia() &&
                c.getMotorEletricoPotencia() == this.motorEletricoPotencia;
    }

    public MotorGasolinaH clone() {
        return new MotorGasolinaH(super.getMatricula(), super.getModelo(), super.getMarca(), super.getFichaVeiculo(), super.getTipoMotor(), super.getMotorCombustaoPotencia(), this.motorEletricoPotencia);
    }

    public boolean isHibrid(){
        return true;
    }

    public String toString()
    {
        return "Veiculo " + getMatricula() + " - Modelo: " + this.getModelo() + "; Marca: " + getMarca() + "; Tipo Motor: " + getTipoMotor();
    }
}
