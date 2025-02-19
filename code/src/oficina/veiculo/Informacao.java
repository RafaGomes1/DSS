package oficina.veiculo;

import java.time.LocalDateTime;

public class Informacao {
    private int idInformacao;
    private String info;
    private LocalDateTime data;

    public Informacao(int idInformacao, String info, LocalDateTime data) {
        this.idInformacao = idInformacao;
        this.info = info;
        this.data = data;
    }

    public int getIdInformacao() {
        return idInformacao;
    }

    public void setIdInformacao(int idInformacao) {
        this.idInformacao = idInformacao;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
