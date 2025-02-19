package oficina.servico;

import DAOs.*;
import oficina.veiculo.Veiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GestServicoFacade implements IGestServico
{
    private LocalTime aberturaOficina;
    private LocalTime fechoOficina;
    private ClienteDAO clientes;
    private MecanicosDAO mecanicos;
    private ServicoDAO servicos;
    private OficinaDAO oficinaDAO;
    private PostoAtendimentoDAO postosAtendimento;

    public GestServicoFacade(String idOficina)
    {
        this.oficinaDAO = OficinaDAO.getInstance();
        this.aberturaOficina = this.oficinaDAO.getHoraAbertura(idOficina);
        this.fechoOficina = this.oficinaDAO.getHoraFecho(idOficina);
        this.clientes = ClienteDAO.getInstance();
        this.mecanicos = MecanicosDAO.getInstance();
        this.servicos = ServicoDAO.getInstance();
        this.postosAtendimento = PostoAtendimentoDAO.getInstance();
    }

    public Map<String,Cliente> getClientes() {
        return clientes;
    }

    public Map<String,Mecanico> getMecanicos() {
        return mecanicos;
    }

    public Servico procuraServico(int idServico)
    {
        return this.servicos.get(idServico);
    }

    public TipoServico tipoServico(Servico servico)
    {
        return servico.getTipo();
    }

    public List<String> listaServicosPorVeiculo(String tipoMotor)
    {
        List<String> list = new ArrayList<>();
        for (Servico servico : this.servicos.values())
        {
            int flag = servico.getTipo().getTipo();
            switch (flag)
            {
                case 0: // Universao
                    list.add(servico.toString());
                    break;
                case 1: // Combustao
                    if (tipoMotor.startsWith("Gasoleo") || tipoMotor.startsWith("Gasolina"))
                    {
                        list.add(servico.toString());
                    }
                    break;
                case 2: // Gasoleo
                    if (tipoMotor.startsWith("Gasoleo"))
                    {
                        list.add(servico.toString());
                    }
                    break;
                case 3: // Gasolina
                    if (tipoMotor.startsWith("Gasolina"))
                    {
                        list.add(servico.toString());
                    }
                    break;
                case 4: // Eletrico
                    if (tipoMotor.equals("Eletrico") || tipoMotor.equals("MotorGasoleoH") || tipoMotor.equals("MotorGasolinaH"))
                    {
                        list.add(servico.toString());
                    }
                    break;
            }
        }
        return list;
    }

    public List<PostoAtendimento> getPostosAtendimentoTipoServico(TipoServico tipoServico)
    {
        List<PostoAtendimento> postos = new ArrayList<>();
        for (PostoAtendimento posto : this.postosAtendimento.values())
        {
            if (posto.getTipo().equals(tipoServico))
            {
                postos.add(posto);
            }
        }
        return postos;
    }

    public LocalTime agendaServico(String idCliente, int idServico, String matricula) {
        LocalTime horaPrevista = null;

        if (this.clientes.containsKey(idCliente) && this.servicos.containsKey(idServico))
        {
            Cliente cliente = this.clientes.get(idCliente);
            Servico servico = this.servicos.get(idServico);
            Veiculo veiculo = cliente.getVeiculos().get(matricula);
            List<PostoAtendimento> postos = this.getPostosAtendimentoTipoServico(servico.getTipo());
            if (!postos.isEmpty())
            {
                LocalTime inicio = LocalTime.now();
                for (PostoAtendimento posto : this.postosAtendimento.values())
                {
                    List<Agendamento> agend = posto.getAgendamentos();
                    for (Agendamento agendamento : agend)
                    {
                        if (agendamento.getVeiculo().getMatricula().equals(matricula) && agendamento.getDataHora().toLocalTime().isAfter(inicio))
                        {
                            inicio = agendamento.getDataHora().toLocalTime();
                        }
                    }
                }
                for (PostoAtendimento posto : postos)
                {
                    horaPrevista = posto.getHorarioDisponivel(servico.getTempoEstimado() * 30, inicio ,this.aberturaOficina);
                    if (horaPrevista != null)
                    {
                        posto.addAgendamento(horaPrevista,servico.clone(),veiculo);
                        break;
                    }
                }
            }
        }
        return horaPrevista;
    }

    public boolean verificaExistenciaAgendamento(int idPosto)
    {
        return this.postosAtendimento.get(idPosto).getAgendamentos().isEmpty();
    }

    public boolean verficaExistenciaNotificacoes(String idCliente)
    {
        return this.clientes.get(idCliente).getNotificacoes().isEmpty();
    }

    public void retiraProxServico(int idPosto)
    {
        this.postosAtendimento.get(idPosto).retiraProxServico();
    }

    public String getProximoServicoPosto(int idPosto)
    {
        return this.postosAtendimento.get(idPosto).getProximoAgendamento().toString();
    }

    public void notificaCliente(String idCliente, String info)
    {
        if (this.clientes.containsKey(idCliente))
        {
            this.clientes.get(idCliente).adicionaNotificacao(info);
        }
    }

    public List<PostoAtendimento> getPostosAtentimento()
    {
        List<PostoAtendimento> postos = new ArrayList<>();
        for (PostoAtendimento posto : this.postosAtendimento.values())
        {
            postos.add(posto);
        }
        return postos;
    }

    public Veiculo getVeiculoAgendamento(int idPosto)
    {
        return this.postosAtendimento.get(idPosto).getProximoAgendamento().getVeiculo();
    }


    public List<String> getNotificacoesCliente(String idCliente)
    {
        if (this.clientes.containsKey(idCliente))
        {
            List<String> list = new ArrayList<>();
            List<Notificacao> notificacoes = this.clientes.get(idCliente).getNotificacoes();
            for (Notificacao notificacao : notificacoes)
            {
                list.add(notificacao.toString());
            }
            return list;
        }
        return null;
    }

    public boolean verificaCompetenciaMecanicoPosto(String idMecanico, int idPosto)
    {
        if (this.mecanicos.containsKey(idMecanico) && this.postosAtendimento.containsKey(idPosto))
        {
            return this.mecanicos.get(idMecanico).getCompetencias().equals(this.postosAtendimento.get(idPosto).getTipo());
        }
        return false;
    }

    public boolean verificaInicioTurno(String idMec)
    {
        if (this.mecanicos.containsKey(idMec)) {
            if (this.mecanicos.getInicioTurno(idMec).isBefore(this.mecanicos.getFimTurno(idMec)) && !this.mecanicos.getInicioTurno(idMec).toLocalDate().equals(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }


    public boolean verificaFimTurno(String idMec)
    {
        if (this.mecanicos.containsKey(idMec)) {
            if (this.mecanicos.getFimTurno(idMec).isBefore(this.mecanicos.getInicioTurno(idMec))) {
                return true;
            }
        }
        return false;
    }

    public boolean iniciaTurnoMec(String idMec, int idPosto)
    {
        if (this.mecanicos.containsKey(idMec))
        {
            if (this.mecanicos.get(idMec).getCompetencias().equals(this.postosAtendimento.get(idPosto).getTipo()))
            {
                this.mecanicos.updateTurnoInicio(idMec, LocalDateTime.now());
                return true;
            }
        }
        return false;
    }

    public boolean finalizaTurnoMec(String idMec)
    {
        if (this.mecanicos.containsKey(idMec))
        {
            this.mecanicos.updateTurnoFim(idMec, LocalDateTime.now());
            return true;
        }
        return false;
    }

}
