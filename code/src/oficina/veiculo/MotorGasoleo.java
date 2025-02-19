package oficina.veiculo;

import DAOs.InformacaoDAO;

import java.util.List;
import java.util.Map;

public class MotorGasoleo extends Veiculo{

    private int motorCombustaoPotencia;

    public MotorGasoleo(String matricula, String modelo, String marca, InformacaoDAO fichaVeiculo, String tipoMotor, int motorCombustaoPotencia) {
        super(matricula,modelo,marca,fichaVeiculo, tipoMotor);
        this.motorCombustaoPotencia = motorCombustaoPotencia;
    }

    public int getMotorCombustaoPotencia() {
        return motorCombustaoPotencia;
    }

    public void setMotorCombustaoPotencia(int motorCombustaoPotencia) {
        this.motorCombustaoPotencia = motorCombustaoPotencia;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o.getClass() != MotorGasoleo.class) {
            return false;
        }
        MotorGasoleo c = (MotorGasoleo) o;
        return  c.getMatricula() == this.getMatricula() &&
                c.getModelo() == this.getModelo() &&
                c.getMarca() == this.getMarca() &&
                c.getFichaVeiculo() == this.getFichaVeiculo() &&
                c.getMotorCombustaoPotencia() == this.motorCombustaoPotencia;
    }

    public MotorGasoleo clone() {
        return new MotorGasoleo(super.getMatricula(),super.getModelo(),super.getMarca(),super.getFichaVeiculo(),super.getTipoMotor(),this.motorCombustaoPotencia);
    }

    public boolean isHibrid(){
        return false;
    }

    public String toString()
    {
        return "Veiculo " + getMatricula() + " - Modelo: " + this.getModelo() + "; Marca: " + getMarca() + "; Tipo Motor: " + getTipoMotor();
    }
}
