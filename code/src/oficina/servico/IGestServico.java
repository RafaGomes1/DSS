package oficina.servico;

import oficina.veiculo.Veiculo;

import java.time.LocalTime;
import java.util.List;

public interface IGestServico
{
    public Servico procuraServico(int idServico);
    public TipoServico tipoServico(Servico servico);
    public LocalTime agendaServico(String idCliente, int idServico, String matricula);
    public List<String> listaServicosPorVeiculo(String tipoMotor);
    public String getProximoServicoPosto(int idPosto);
    public List<String> getNotificacoesCliente(String idCliente);
    public void notificaCliente(String idCliente, String info);
    public Veiculo getVeiculoAgendamento(int idPosto);
    public void retiraProxServico(int idPosto);
    public boolean iniciaTurnoMec(String idMec, int idPosto);
    public boolean finalizaTurnoMec(String idMec);
    public boolean verificaExistenciaAgendamento(int idPosto);
    public boolean verficaExistenciaNotificacoes(String idCliente);
    public List<PostoAtendimento> getPostosAtentimento();
    public boolean verificaInicioTurno(String idMec);
    public boolean verificaFimTurno(String idMec);

    public boolean verificaCompetenciaMecanicoPosto(String idMecanico, int idPosto);



}
