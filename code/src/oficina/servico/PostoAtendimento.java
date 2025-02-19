package oficina.servico;

import DAOs.AgendamentoDAO;
import oficina.veiculo.Veiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PostoAtendimento
{
    private int idPostoAtendimento;
    private AgendamentoDAO agendamentos;
    private TipoServico tipo;

    public PostoAtendimento(int idPostoAtendimento, AgendamentoDAO agendamentos, TipoServico tipo) {
        this.idPostoAtendimento = idPostoAtendimento;
        this.agendamentos = agendamentos;
        this.tipo = tipo;
    }

    public int getIdPostoAtendimento()
    {
        return idPostoAtendimento;
    }

    public void setIdPostoAtendimento(int idPostoAtendimento) {
        this.idPostoAtendimento = idPostoAtendimento;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public void addAgendamento(Agendamento a)
    {
        this.agendamentos.put(a.getIdAgendamento(),a);
    }

    public void addAgendamento(LocalTime hora, Servico servico, Veiculo veiculo)
    {
        Agendamento agendamento = new Agendamento(this.agendamentos.size() + 1, hora.atDate(LocalDate.now()), servico, veiculo);
        this.agendamentos.put(this.getIdPostoAtendimento(),agendamento);
    }

    public static LocalTime arredondarParaProximoIntervalo(LocalTime horaOriginal) {
        int minutosIntervalo = 60;
        if (horaOriginal.getMinute() < 30)
        {
            minutosIntervalo = 30;
        }
        int minutos = (horaOriginal.getMinute() / minutosIntervalo) * minutosIntervalo;
        return horaOriginal.withMinute(minutos).plusMinutes(minutosIntervalo).minusSeconds(horaOriginal.getSecond());
    }

    public Agendamento getProximoAgendamento()
    {
        List<Agendamento> agendamentos = this.agendamentos.getListaAgendamentos(this.getIdPostoAtendimento());
        agendamentos.sort(Comparator.comparing(Agendamento::getDataHora));
        if (!agendamentos.isEmpty())
        {
            return agendamentos.get(0).clone();
        }
        return null;
    }

    public void retiraProxServico()
    {
        Agendamento prox = this.getProximoAgendamento();
        this.agendamentos.remove(prox.getIdAgendamento());
    }

    public List<Agendamento> getAgendamentos()
    {
        return this.agendamentos.getListaAgendamentos(this.getIdPostoAtendimento());
    }

    public LocalTime getHorarioDisponivel(int duracao,LocalTime inicio ,LocalTime horaFecho)
    {
        List<Agendamento> agend = this.agendamentos.getListaAgendamentos(this.getIdPostoAtendimento()); //
        LocalTime inicioPrevisto = arredondarParaProximoIntervalo(inicio);
        LocalTime fimPrevisto = inicioPrevisto.plusMinutes(duracao);
        if (agend.isEmpty())
        {
            return inicioPrevisto;
        }
        agend.sort(Comparator.comparing(Agendamento::getDataHora));

        for (Agendamento agendamento : agend)
        {
            LocalTime inicioAgendamento = agendamento.getDataHora().toLocalTime();
            if (inicioPrevisto.isBefore(inicioAgendamento) && fimPrevisto.isBefore(inicioAgendamento))
            {
                return inicioPrevisto;
            }
            inicioPrevisto = inicioAgendamento.plusMinutes(agendamento.getServico().getTempoEstimado() * 30);
            fimPrevisto = inicioPrevisto.plusMinutes(duracao);
        }

        if (fimPrevisto.isAfter(horaFecho))
        {
            return null;
        }
        return inicioPrevisto;
    }

    public String toString()
    {
        return "Posto de Atendimento nr. " + this.getIdPostoAtendimento() + "; Tipo de serviços do Posto: " + tipo.getDesignacao();
    }

}
