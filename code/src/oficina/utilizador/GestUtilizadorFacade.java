package oficina.utilizador;

import DAOs.UtlizadoresDAO;

public class GestUtilizadorFacade implements IGestUtilizador {
    UtlizadoresDAO utilizadores;

    public GestUtilizadorFacade()
    {
        this.utilizadores = UtlizadoresDAO.getInstance();
    }

    public Utilizador procuraUtilizador(String idUtilizador)
    {
        return this.utilizadores.get(idUtilizador);
    }

    public boolean validaUtilizador(String idUtilizador, String password) {
        return this.utilizadores.get(idUtilizador).getPassword().compareTo(password) == 0;
    }
}
