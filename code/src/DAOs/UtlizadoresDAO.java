package DAOs;

import oficina.servico.Cliente;
import oficina.servico.Mecanico;
import oficina.utilizador.Utilizador;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UtlizadoresDAO implements Map<String, Utilizador> {

    private static UtlizadoresDAO singleton = null;

    private UtlizadoresDAO() {

        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Utilizador (" +
                         "idUtilizador VARCHAR(50) NOT NULL," +
                         "Nome VARCHAR(50) NOT NULL," +
                         "Password VARCHAR(50)," +
                         "Tipo INT NOT NULL," +
                         "PRIMARY KEY (idUtilizador));";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static UtlizadoresDAO getInstance() {
        if (UtlizadoresDAO.singleton == null) {
            UtlizadoresDAO.singleton = new UtlizadoresDAO();
        }
        return UtlizadoresDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Utilizador ");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public int size() {
        int i = 0;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT count(*) + ESIDEAL.Utilizador;");

            if (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException e){
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean containsKey(Object key) {
        boolean r;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Utilizador FROM ESIDEAL.Utilizador WHERE user='" + key + "'");
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Utilizador u = (Utilizador) value;
        return this.containsKey(u.getIdUtilizador());
    }

    public Set<Entry<String, Utilizador>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }


    public Utilizador get(Object key) {
        Utilizador u = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet us = stm.executeQuery("SELECT * FROM Utilizador WHERE idUtilizador='"+key+"'");
            if (us.next()){
                u = new Utilizador(us.getString("idUtilizador"),us.getString("Nome"),us.getString("Password"),us.getInt("Tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }



    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT user FROM Utilizador");

            while (rs.next()) {
                String idu = rs.getString("user");
                res.add(idu);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Collection<Utilizador> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public Utilizador put(String key, Utilizador value){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();

            if (this.containsKey(key)){
                    PreparedStatement pstm1 = conn.prepareStatement("UPDATE Utilizador WHERE user = ?");
                    pstm1.setString(1,key);
                    pstm1.executeUpdate();
            }
            else {
                    PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO Utilizador (idUtilizador,Nome,Password) VALUES (?,?,?)");
                    pstm2.setString(1,value.getIdUtilizador());
                    pstm2.setString(2,value.getNome());
                    pstm2.setString(3,value.getPassword());
                    pstm2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Utilizador remove (Object key){
        Utilizador value = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm3 = conn.createStatement();

            PreparedStatement pstm = conn.prepareStatement("DELETE  FROM Utilizador WHERE user = ?");
            value = this.get(key);
            pstm.setString(1,(String)key);
            pstm.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Utilizador> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }

}




