package oficina.servico;

import java.time.LocalDate;

public class Notificacao {
    private int idNotificacao;
    private String info;
    private LocalDate data;
    private String cliente;

    public Notificacao(int idNotificacao, String info, LocalDate data, String cliente) {
        this.idNotificacao = idNotificacao;
        this.info = info;
        this.data = data;
        this.cliente = cliente;
    }

    public Notificacao(Notificacao notificacao)
    {
        this.idNotificacao = notificacao.getIdNotificacao();
        this.data = notificacao.getData();
        this.info = notificacao.getInfo();
        this.cliente = notificacao.getCliente();
    }

    public int getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(int idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append("Notificação ").append(this.idNotificacao).append(" -");
        string.append("Data: ").append(this.data.toString()).append("; ");
        string.append("Informação: ").append(info);
        return string.toString();
    }

    public Notificacao clone()
    {
        return new Notificacao(this);
    }
}
