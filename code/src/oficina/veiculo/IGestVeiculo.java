package oficina.veiculo;

import java.time.LocalDateTime;

public interface IGestVeiculo {

    public Veiculo procuraVeiculo(String matricula);

    public void atualizaFichaVeiculo(String matricula, String info);

    public String getProprietatioVeiculo(String Matricula);
}
