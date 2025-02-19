package oficina.utilizador;


public interface IGestUtilizador {
    public Utilizador procuraUtilizador(String idUtilizador);

    public boolean validaUtilizador(String idUtilizador, String password);
}
