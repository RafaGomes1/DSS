package oficina.servico;

import java.util.Objects;

public class TipoServico {
    private int tipo; // 0 - Universal; 1 - Combustão; 2 - Gasoleo; 3 - Gasolina; 4 - Eletrico
    private String designacao;

    public TipoServico(int tipo, String designacao) {
        this.tipo = tipo;
        this.designacao = designacao;
    }

    public TipoServico(TipoServico tipo)
    {
        this.tipo = tipo.getTipo();
        this.designacao = tipo.getDesignacao();
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Tipo Serviço: ");
        string.append(this.getDesignacao());
        return string.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoServico that = (TipoServico) o;
        return tipo == that.tipo && this.designacao.equals(((TipoServico) o).getDesignacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, designacao);
    }

    public TipoServico clone()
    {
        return new TipoServico(this);
    }
}
