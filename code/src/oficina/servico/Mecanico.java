package oficina.servico;

import oficina.utilizador.Utilizador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

public class Mecanico {

    private Utilizador conta;
    private TipoServico competencia;
    private LocalDateTime ultimoInicioTurno;
    private LocalDateTime ultimoFimTurno;

    public Mecanico(Utilizador utilizador, LocalDateTime inicioturno, LocalDateTime ultimoFimTurno, TipoServico competencias)
    {
        this.conta = utilizador;
        this.ultimoInicioTurno = inicioturno;
        this.ultimoFimTurno = ultimoFimTurno;
        this.competencia = competencias;
    }

    public String getIdUtilizador()
    {
        return this.conta.getIdUtilizador();
    }

    public String getPassword()
    {
        return this.conta.getPassword();
    }

    public String getIdMecanico()
    {
        return this.conta.getIdUtilizador();
    }

    public String getNome()
    {
        return this.conta.getNome();
    }

    public TipoServico getCompetencias() {
        return competencia;
    }

    public void setCompetencias(TipoServico competencias) {
        this.competencia = competencias;
    }
}
