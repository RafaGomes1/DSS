package DAOs;

import oficina.servico.Cliente;
import oficina.utilizador.Utilizador;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ClienteDAO implements Map<String, Cliente> {
    private static ClienteDAO singleton = null;

    private ClienteDAO() {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Cliente (" +
                         "idCliente Varchar(50) NOT NULL," +
                         "FOREIGN KEY (idCliente) REFERENCES ESIDEAL.Utilizador(idUtilizador)," +
                         "PRIMARY KEY (idCliente))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static ClienteDAO getInstance(){
        if(ClienteDAO.singleton==null){
            ClienteDAO.singleton = new ClienteDAO();
        }
        return ClienteDAO.singleton;
    }

    public void clear() {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            stm.executeUpdate("TRUNCATE Cliente");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsKey(Object key) {
        boolean r;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idCliente FROM Cliente WHERE idCliente='"+key+"'");
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Cliente c = (Cliente) value;
        return this.containsKey(c.getIdCliente());
    }

    public Set<Entry<String, Cliente>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    public Cliente get(Object key) {
        Cliente r = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement userStm = conn.createStatement();
            ResultSet set = userStm.executeQuery("SELECT * FROM Utilizador WHERE idUtilizador='"+ key + "'");
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Cliente WHERE idCliente='"+key+"'");
            if(rs.next()){
                r = new Cliente(UtlizadoresDAO.getInstance().get(key), NotificacaoDAO.getInstance(),VeiculoDAO.getInstance());
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT user FROM Utilizador")) {
            while (rs.next()) {
                String idc = rs.getString("cliente");
                res.add(idc);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Cliente put(String key, Cliente value) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if (this.containsKey(key)){
                try (PreparedStatement pstm1 = conn.prepareStatement("UPDATE Cliente where user = ?")){
                    pstm1.setString(1,key);
                    pstm1.executeUpdate();
                }
            }
            else {
                try (PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO Cliente (id,nome,pass,notificacoes,veiculos) VALUES (?,?,?,?)")){
                    pstm2.setString(1, value.getIdCliente());
                    pstm2.setString(2, value.getPassword());
                    pstm2.executeUpdate();
                }
            }
        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public Collection<Cliente> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public void putAll(Map<? extends String, ? extends Cliente> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }

    @Override
    public Cliente remove(Object key) {
        Cliente value = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM Cliente WHERE user = ?")){
                value = this.get(key);
                pstm.setString(1,(String)key);
                pstm.executeUpdate();
            }
        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM `ESIDEAL`.`Cliente`;")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }




}
