package oficina.servico;

public class Servico {

    private int idServico;
    private int custo;
    private int tempoEstimado;
    private String designacao;
    private TipoServico tipo;

    public Servico (int idServico, int custo, int tempoEstimado, String designacao, TipoServico tipo) {
        this.idServico = idServico;
        this.custo = custo;
        this.tempoEstimado = tempoEstimado;
        this.designacao = designacao;
        this.tipo = tipo;
    }

    public Servico(Servico servico)
    {
        this.idServico = servico.getIdServico();
        this.custo = servico.getCusto();
        this.tempoEstimado = servico.getTempoEstimado();
        this.designacao = servico.getDesignacao();
        this.tipo = getTipo();
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public int getTempoEstimado() {
        return tempoEstimado;
    }

    public void setTempoEstimado(int tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        string.append("Serviço nr. " + this.getIdServico() + "-");
        string.append("Designação: " + this.getDesignacao() + "-");
        string.append("Duração prevista: " + tempoEstimado * 30 + "(minutos) -");
        string.append(this.getTipo().toString());
        return string.toString();
    }

    public Servico clone()
    {
        return new Servico(this);
    }
}
