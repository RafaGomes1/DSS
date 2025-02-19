package DAOs;

import oficina.veiculo.*;

import java.sql.*;
import java.util.*;

public class VeiculoDAO implements Map<String, Veiculo>
{
    private static VeiculoDAO singleton = null;

    public VeiculoDAO()
    {
        try (Connection connection = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Veiculo (" +
                         "Matricula VARCHAR(8) NOT NULL," +
                         "Modelo VARCHAR(45) NOT NULL," +
                         "Marca VARCHAR(45) NOT NULL," +
                         "TipoMotor VARCHAR(15) NOT NULL," +
                         "idCliente INT NOT NULL," +
                         "FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)," +
                         "PRIMARY KEY (Matricula))";
            statement.executeUpdate(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static VeiculoDAO getInstance()
    {
        if (VeiculoDAO.singleton == null)
        {
            VeiculoDAO.singleton = new VeiculoDAO();
        }
        return VeiculoDAO.singleton;
    }

//    public void clear()
//    {
//        try
//        {
//            Connection connection = DriverManager.getConnection((DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
//            Statement statement = connection.createStatement();
//            statement.executeUpdate("TRUNCATE Veiculo");
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//            throw new NullPointerException(e.getMessage());
//        }
//    }

    public boolean containsKey(Object key)
    {
        boolean flag;
        try
        {
            Connection connection = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT Matricula FROM ESIDEAL.Veiculo WHERE Matricula = '" + key + "'");
            flag = set.next();
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return flag;
    }

    public boolean containsValue(Object value)
    {
        Veiculo veiculo = (Veiculo) value;
        return this.containsKey(veiculo.getMatricula());
    }

    public Veiculo get(Object key)
    {
        Veiculo veiculo = null;
        try {
            Connection connection = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM Veiculo WHERE Matricula = '"+ key + "'");
            if (set.next()){
                String tipo = set.getString("TipoMotor");

                if (tipo.equals("Gasolina")){
                    veiculo = new MotorGasolina(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(), set.getString("TipoMotor"),140);
                } else if (tipo.equals("Gasoleo")) {
                    veiculo = new MotorGasoleo(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),130);
                } else if (tipo.equals("Eletrico")) {
                    veiculo = new Eletrico(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),100);
                } else if (tipo.equals("MotorGasoleoH")) {
                    veiculo = new MotorGasoleoH(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),120,100);
                } else if (tipo.equals("MotorGasolinaH")) {
                    veiculo = new MotorGasolinaH(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),110,100);
                } else {
                    throw new RuntimeException("(VeiculDAO -> função get");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return veiculo;
    }

    public Map<String,Veiculo> getListaCliente(Object key){
        Map<String,Veiculo> lista = new HashMap<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM Veiculo WHERE idCliente='"+key+"'");
            while (set.next()){
                String tipo = set.getString("TipoMotor");
                String matricula = set.getString("Matricula");
                Veiculo v = null;

                if (tipo.equals("Gasolina")){
                    v = new MotorGasolina(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(), set.getString("TipoMotor"),set.getInt("MotorCombustaoPotencia"));
                } else if (tipo.equals("Gasoleo")) {
                    v = new MotorGasoleo(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),set.getInt("MotorCombustaoPotencia"));
                } else if (tipo.equals("Eletrico")) {
                    v = new Eletrico(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),set.getInt("MotorEletricoPotencia"));
                } else if (tipo.equals("MotorGasoleoH")) {
                    v = new MotorGasoleoH(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),set.getInt("MotorCombustaoPotencia"),set.getInt("MotorEletricoPotencia"));
                } else if (tipo.equals("MotorGasolinaH")) {
                    v = new MotorGasolinaH(set.getString("Matricula"),set.getString("Modelo"),set.getString("Marca"),InformacaoDAO.getInstance(),set.getString("TipoMotor"),set.getInt("MotorCombustaoPotencia"),set.getInt("MotorEletricoPotencia"));
                } else {
                    throw new RuntimeException("(VeiculDAO -> função get");
                }
                lista.put(matricula,v);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return lista;
    }

    public String getNomeProprietario(Object key)
    {
        try
        {
            Connection connection = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT idCliente FROM ESIDEAL.Veiculo WHERE Matricula = '" + key + "'");
            String prop = null;
            if (set.next())
            {
                prop = set.getString("idCliente");
            }
            return prop;
        } catch (SQLException e)
        {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Veiculo put(String key, Veiculo value) {
        return null;
    }

    @Override
    public Veiculo remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Veiculo> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Veiculo> values() {
        return null;
    }

    public Set<Entry<String, Veiculo>> entrySet()
    {
        return null;
    }


    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
