package oficina;

import oficina.servico.*;
import oficina.utilizador.GestUtilizadorFacade;
import oficina.utilizador.IGestUtilizador;
import oficina.veiculo.GestVeiculoFacade;
import oficina.veiculo.IGestVeiculo;
import oficina.veiculo.Veiculo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ESIDEALFacade
{
    private IGestServico servicos;
    private IGestVeiculo veiculos;
    private IGestUtilizador utilizadores;

    public ESIDEALFacade(String idOficina)
    {
        this.servicos = new GestServicoFacade(idOficina);
        this.veiculos = new GestVeiculoFacade();
        this.utilizadores = new GestUtilizadorFacade();
    }

    // LOGIN - Qql tipo de utilizador - TODOS
    public int login(String idUtilizador, String password)
    {
        if(this.utilizadores.validaUtilizador(idUtilizador,password))
        {
            return this.utilizadores.procuraUtilizador(idUtilizador).getTipo();
        }
        return -1;
    }

    public boolean checkProxServico(int idPosto)
    {
        return this.servicos.verificaExistenciaAgendamento(idPosto);
    }

    public boolean checkNotificacoes(String idCliente)
    {
        return this.servicos.verficaExistenciaNotificacoes(idCliente);
    }

    public List<String> getPostos()
    {
        List<String> list = new ArrayList<>();
        List<PostoAtendimento> postoAtendimentos = this.servicos.getPostosAtentimento();
        for (PostoAtendimento posto : postoAtendimentos)
        {
            list.add(posto.toString());
        }
        return list;
    }

    public String getProximoServico(int idPosto)
    {
        return this.servicos.getProximoServicoPosto(idPosto);
    }

    public LocalTime agendarServiço(String idCliente, String idVeiculo, int idServiço) // 0 -> Sucesso; 1 -> Cliente não existe; 2 -> Serviço não exi
    {
        LocalTime horaAgendamento = this.servicos.agendaServico(idCliente,idServiço,idVeiculo);
        Servico servico = this.servicos.procuraServico(idServiço);
        if (horaAgendamento != null)
        {
            this.veiculos.atualizaFichaVeiculo(idVeiculo, "Serviço Agendado:" + servico.getDesignacao());
        }
        return horaAgendamento;
    }

    public void completaServico(int idPosto)
    {
        Veiculo veiculo = this.servicos.getVeiculoAgendamento(idPosto);
        this.servicos.notificaCliente(this.veiculos.getProprietatioVeiculo(veiculo.getMatricula()),"Servico realizado com sucesso!");
        this.veiculos.atualizaFichaVeiculo(veiculo.getMatricula(),"Serviço Agendado realizado com sucesso!");
        this.servicos.retiraProxServico(idPosto);
    }

    public void rejeitaServico(int idPosto, String info)
    {
        String informacao = "Serviço rejeitado, motivo: " + info;
        Veiculo veiculo = this.servicos.getVeiculoAgendamento(idPosto);
        this.servicos.notificaCliente(this.veiculos.getProprietatioVeiculo(veiculo.getMatricula()),informacao);
        this.veiculos.atualizaFichaVeiculo(veiculo.getMatricula(),informacao);
        this.servicos.retiraProxServico(idPosto);
    }

    public boolean checkInicioTurnoMec(String idMecanico)
    {
        return this.servicos.verificaInicioTurno(idMecanico);
    }

    public boolean checkFimTurnoMec(String idMecanico)
    {
        return this.servicos.verificaFimTurno(idMecanico);
    }

    public boolean checkCompetenciaMecPosto(String idMecanico, int idPosto)
    {
        return this.servicos.verificaCompetenciaMecanicoPosto(idMecanico,idPosto);
    }

    public List<String> getNotificacoesCliente(String idCliente)
    {
        return this.servicos.getNotificacoesCliente(idCliente);
    }

    public List<String> listaServicos(String idVeiculo)
    {
        return this.servicos.listaServicosPorVeiculo(this.veiculos.procuraVeiculo(idVeiculo).getTipoMotor());
    }

    public boolean iniciaTurno(String idMecanico, int idPosto)
    {
        return this.servicos.iniciaTurnoMec(idMecanico,idPosto);
    }

    public boolean finalizaTurno(String idMecanico)
    {
        return this.servicos.finalizaTurnoMec(idMecanico);
    }

}
