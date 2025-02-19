package DAOs;

import oficina.servico.Mecanico;
import oficina.servico.Notificacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class NotificacaoDAO implements Map<Integer, Notificacao> {

    private static NotificacaoDAO singleton = null;

    private NotificacaoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Notificacao (" +
                         "idNotificacao INT NOT NULL," +
                         "Info TEXT NOT NULL," +
                         "Data DATE NOT NULL," +
                         "idCliente VARCHAR(50) NOT NULL," +
                         "FOREIGN KEY (idCliente) REFERENCES ESIDEAL.Cliente(idCliente)," +
                         "PRIMARY KEY (idNotificacao))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public Set<Integer> keySet() {
        Set<Integer> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idNotificacao FROM ESIDEAL.Notificacao");

            while (rs.next()){
                int idm = rs.getInt("idNotificacao");
                res.add(idm);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public Collection<Notificacao> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public static NotificacaoDAO getInstance(){
        if(NotificacaoDAO.singleton == null){
            NotificacaoDAO.singleton = new NotificacaoDAO();
        }
        return NotificacaoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE ESIDEAL.Notificacao");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public boolean containsKey(Object key) {
        boolean b;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement s = conn.createStatement();
             ResultSet r=
                     s.executeQuery("SELECT idNotificacao FROM ESIDEAL.Notificacao WHERE idNotificacao ='" + key + "'")){
            b = r.next();
        }catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return b;
    }

    public boolean containsValue(Object value) {
        Notificacao c = (Notificacao) value;
        return this.containsKey(c.getIdNotificacao());
    }

    public Set<Map.Entry<Integer, Notificacao>> entrySet() {
        return new HashSet<>(this.entrySet());
    }

    public Notificacao get(Object key) {
        Notificacao r = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM ESIDEAL.Notificacao WHERE idNotificacao='"+key+"';")) {
            r = rs.next() ? new Notificacao(rs.getInt("idNotificacao"),rs.getString("Info"),rs.getObject("Data", LocalDate.class),rs.getString("idCliente")) : null;
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public List<Notificacao> getListaCliente(Object key){
        List<Notificacao> l = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet set = stm.executeQuery("SELECT * FROM ESIDEAL.Notificacao WHERE idCliente='"+key+"'");
            while (set.next()){
                Notificacao noti = new Notificacao(set.getInt("idNotificacao"),set.getString("Info"),set.getObject("Data", LocalDate.class),set.getString("idCliente"));
                l.add(noti);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return l;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }


    public Notificacao put(Integer key, Notificacao value) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO Notificacao (idNotificacao,Info,Data,idCliente) VALUES (?,?,?,?)")){
                pstm.setInt(1, value.getIdNotificacao());
                pstm.setString(2,value.getInfo());
                pstm.setObject(3,value.getData());
                pstm.setString(4, value.getCliente());
                pstm.executeUpdate();
            }
        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Notificacao remove(Object key) {
        Notificacao c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement s = conn.createStatement()) {
            s.executeUpdate("DELETE FROM ESIDEAL.Notificacao WHERE idNotificacao ='"+key+"'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Notificacao> m) {

    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM ESIDEAL.Notificacao")) {
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
