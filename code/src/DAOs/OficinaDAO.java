package DAOs;

import oficina.servico.Oficina;

import java.sql.*;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OficinaDAO implements Map<String,Oficina> {
    private static OficinaDAO singleton = null;

    private OficinaDAO() {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Oficina (" +
                         "idOficina VARCHAR(20) NOT NULL," +
                         "HoraAbertura TIME NOT NULL," +
                         "HoraFecho TIME NOT NULL," +
                         "PRIMARY KEY (idOficina));";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static OficinaDAO getInstance(){
        if(OficinaDAO.singleton==null){
            OficinaDAO.singleton = new OficinaDAO();
        }
        return OficinaDAO.singleton;
    }

    public Oficina get(Object key) {
        Oficina r = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM ESIDEAL.Oficina WHERE idServico='"+key+"'")) {
            if (rs.next()){
                r = new Oficina(rs.getString("idOficina"),rs.getObject("HoraAbertura",LocalTime.class),rs.getObject("HoraFecho",LocalTime.class));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public void clear() {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            stm.executeUpdate("TRUNCATE Oficina");
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
            ResultSet rs = stm.executeQuery("SELECT idOficina FROM Oficina WHERE idOficina='"+key+"'");
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public boolean containsValue(Object value) {
        Oficina o = (Oficina) value;
        return this.containsKey(o.getIdOficina());
    }

    public Set<Map.Entry<String, Oficina>> entrySet() {
        return this.entrySet().stream().collect(Collectors.toSet());
    }

    public LocalTime getHoraAbertura(String key){
        LocalTime hora = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet set = stm.executeQuery("SELECT HoraAbertura FROM Oficina WHERE idOficina = '" + key + "'");
            if (set.next()){
                hora = set.getObject("HoraAbertura", LocalTime.class);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return hora;
    }

    public LocalTime getHoraFecho(Object key){
        LocalTime hora = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet set = stm.executeQuery("SELECT * FROM Oficina WHERE idOficina='"+key+"'");
            if (set.next()){
                hora = set.getObject("HoraFecho", LocalTime.class);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return hora;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT idOficina FROM Oficina")) {
            while (rs.next()) {
                String idc = rs.getString("idOficina");
                res.add(idc);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Oficina put(String key, Oficina value) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            if (this.containsKey(key)){
                try (PreparedStatement pstm1 = conn.prepareStatement("UPDATE Oficina where idOficina = ?")){
                    pstm1.setString(1,key);
                    pstm1.executeUpdate();
                }
            }
            else {
                try (PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO Oficina (id,HoraAbertura,HoraFecho) VALUES (?,?,?)")){
                    pstm2.setString(1, value.getIdOficina());
                    pstm2.setObject(2, value.getHoraAbertura());
                    pstm2.setObject(3, value.getHoraFecho());
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
    public Collection<Oficina> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public void putAll(Map<? extends String, ? extends Oficina> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }

    @Override
    public Oficina remove(Object key) {
        Oficina value = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM Oficina WHERE idOficina ='" + key+"'")){
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
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM Oficina;")) {
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
