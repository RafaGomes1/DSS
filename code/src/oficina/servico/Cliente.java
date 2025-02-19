package oficina.servico;

import DAOs.NotificacaoDAO;
import DAOs.VeiculoDAO;
import oficina.utilizador.Utilizador;
import oficina.veiculo.Veiculo;

import java.lang.ref.Cleaner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cliente {

    private Utilizador conta;
    private NotificacaoDAO notificacoes;
    private VeiculoDAO veiculos;


    public Cliente(Utilizador conta, NotificacaoDAO notificacoes, VeiculoDAO veiculos) {
        this.conta = conta;
        this.notificacoes = notificacoes;
        this.veiculos = veiculos;
    }

    public String getIdCliente() {
        return this.conta.getIdUtilizador();
    }

    public String getPassword(){
        return this.conta.getPassword();
    }

    public int getTipoCliente(){
        return this.conta.getTipo();
    }

    public List<Notificacao> getNotificacoes() {
        List<Notificacao> list = this.notificacoes.getListaCliente(this.getIdCliente());
        return list;
    }

    public Map<String, Veiculo> getVeiculos() {
        return veiculos;
    }


    public void adicionaNotificacao(String info)
    {
        Notificacao notificacao = new Notificacao(this.notificacoes.size(),info, LocalDate.now(),this.getIdCliente());
        this.notificacoes.put(notificacao.getIdNotificacao(),notificacao);
    }
}
