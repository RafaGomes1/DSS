package DAOs;

import oficina.servico.TipoServico;
import oficina.utilizador.Utilizador;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class TipoServicoDAO implements Map<String, TipoServico> {

    private static TipoServicoDAO singleton = null;

    private TipoServicoDAO() {

        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.TipoServico (" +
                    "Tipo INT NOT NULL," +
                    "Designacao VARCHAR(45) NOT NULL," +
                    "PRIMARY KEY (Tipo))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static TipoServicoDAO getInstance() {
        if (TipoServicoDAO.singleton == null) {
            TipoServicoDAO.singleton = new TipoServicoDAO();
        }
        return TipoServicoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE TipoServico ");
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

            ResultSet rs = st.executeQuery("SELECT count(*) + ESIDEAL.TipoServico;");

            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (SQLException e) {
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
            ResultSet rs = stm.executeQuery("SELECT user FROM ESIDEAL.TipoServico WHERE user='" + key + "'");
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        TipoServico u = (TipoServico) value;
        return this.containsKey(u.getTipo());
    }

    public Set<Entry<String, TipoServico>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    public TipoServico get(Object key) {
        TipoServico u = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet us = stm.executeQuery("SELECT * FROM TipoServico WHERE Tipo="+ key);
            if (us.next()){
                u = new TipoServico(us.getInt("Tipo"), us.getString("Designacao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    /*
    public List<TipoServico> getListaCompetencias(Object key){
        List<TipoServico> competencias = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet lc = stm.executeQuery("SELECT * FROM TipoServico Where Tipo='"+key+"'");

        }
    }
    */


    @Override
    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT user FROM TipoServico");

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
    public Collection<TipoServico> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public TipoServico put(String key, TipoServico value) {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();

            if (this.containsKey(key)) {
                PreparedStatement pstm1 = conn.prepareStatement("UPDATE TipoServico WHERE user = ?");
                pstm1.setString(1, key);
                pstm1.executeUpdate();
            } else {
                PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO TipoServico (tipo,designação) VALUES (?,?)");
                pstm2.setInt(1, value.getTipo());
                pstm2.setString(2, value.getDesignacao());
                pstm2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public TipoServico remove(Object key) {
        TipoServico value = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm3 = conn.createStatement();

            PreparedStatement pstm = conn.prepareStatement("DELETE  FROM TipoServico WHERE user = ?");
            value = this.get(key);
            pstm.setString(1, (String) key);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends String, ? extends TipoServico> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }
}
