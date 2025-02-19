package oficina.servico;

import oficina.veiculo.Veiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Agendamento {
    private int idAgendamento;
    private LocalDateTime dataHora;
    private Servico servico;
    private Veiculo veiculo;

    public Agendamento(int idAgendamento, LocalDateTime dataHora, Servico servico, Veiculo veiculo)
    {
        this.idAgendamento = idAgendamento;
        this.dataHora = dataHora;
        this.servico = servico;
        this.veiculo = veiculo;
    }

    public Agendamento(Agendamento agendamento)
    {
        this.idAgendamento = agendamento.getIdAgendamento();
        this.dataHora = agendamento.getDataHora();
        this.servico = agendamento.getServico();
        this.veiculo = agendamento.getVeiculo();
    }

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Servico getServico() {
        return this.servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico.clone();
    }

    public Veiculo getVeiculo() {
        return veiculo.clone();
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo.clone();
    }

    public Agendamento clone()
    {
        return new Agendamento(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return idAgendamento == that.idAgendamento && Objects.equals(dataHora, that.dataHora) && Objects.equals(servico, that.servico) && Objects.equals(veiculo, that.veiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAgendamento, dataHora, servico, veiculo);
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append("Agendamento: ").append(this.idAgendamento).append("-");
        string.append("Data/Hora: ").append(this.dataHora.toString()).append("-");
        string.append(this.servico.toString()).append("-");
        string.append(this.veiculo.toString());
        return string.toString();
    }



}
