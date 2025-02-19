package oficina.servico;

import java.time.LocalTime;

public class Oficina {
    private String idOficina;
    private LocalTime horaAbertura;
    private LocalTime horaFecho;

    public Oficina(String idOficina, LocalTime horaAbertura, LocalTime horaFecho) {
        this.idOficina = idOficina;
        this.horaAbertura = horaAbertura;
        this.horaFecho = horaFecho;
    }

    public String getIdOficina() {
        return idOficina;
    }

    public void setIdOficina(String idOficina) {
        this.idOficina = idOficina;
    }

    public LocalTime getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public LocalTime getHoraFecho() {
        return horaFecho;
    }

    public void setHoraFecho(LocalTime horaFecho) {
        this.horaFecho = horaFecho;
    }
}
