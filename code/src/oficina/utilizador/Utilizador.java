package oficina.utilizador;

public class Utilizador {
    private String idUtilizador;
    private String nome;
    private String password;
    private int tipo;

    public Utilizador(String idUtilizador, String nome, String password, int tipo) {
        this.idUtilizador = idUtilizador;
        this.nome = nome;
        this.password = password;
        this.tipo = tipo;
    }

    public String getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(String idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
