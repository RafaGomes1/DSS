package oficina.veiculo;

import DAOs.VeiculoDAO;

import java.time.LocalDateTime;
import java.util.Map;

public class GestVeiculoFacade implements IGestVeiculo{

    private VeiculoDAO veiculos;

    public GestVeiculoFacade()
    {
        this.veiculos = VeiculoDAO.getInstance();
    }

    public Veiculo procuraVeiculo(String matricula) {
        return this.veiculos.get(matricula);
    }

    public void atualizaFichaVeiculo(String matricula, String info) {
        Veiculo v = this.veiculos.get(matricula);

        int idInformacao = v.getFichaVeiculo().size()+1;

        Informacao i = new Informacao(idInformacao,info,LocalDateTime.now());

        v.addInformacao(i);
    }

    public String getProprietatioVeiculo(String Matricula)
    {
        return this.veiculos.getNomeProprietario(Matricula);
    }

}
