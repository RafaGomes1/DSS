package DAOs;

import oficina.servico.Servico;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServicoDAO implements Map<Integer,Servico>{
    private static ServicoDAO singleton = null;

    private ServicoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Servico (" +
                         "idServico INT NOT NULL," +
                         "Custo DECIMAL NOT NULL," +
                         "TempoEstimado VARCHAR(20) NOT NULL," +
                         "TipoServico INT NOT NULL," +
                    "FOREIGN KEY (TipoServico) REFERENCES ESIDEAL.TipoServico(Tipo)," +
                    "PRIMARY KEY (idServico))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static ServicoDAO getInstance(){
        if(ServicoDAO.singleton == null){
            ServicoDAO.singleton = new ServicoDAO();
        }
        return ServicoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE ESIDEAL.Servico");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size(){
        int i = 0;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT count(*) + ESIDEAL.Servico;");

            if (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException e){
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public boolean containsKey(Object key) {
        boolean b;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement s = conn.createStatement();
             ResultSet r=
                     s.executeQuery("SELECT idServico FROM ESIDEAL.Servico WHERE idServico ='" + key + "'")){
            b = r.next();
        }catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return b;
    }

    public boolean containsValue(Object value) {
        Servico s = (Servico) value;
        return this.containsKey(s.getIdServico());
    }

    public Set<Map.Entry<Integer, Servico>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    public Servico get(Object key) {
        Servico r = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM ESIDEAL.Servico WHERE idServico="+key)) {
            if (rs.next()){
                int tipoServico = rs.getInt("TipoServico");
               r = new Servico(rs.getInt("idServico"),rs.getInt("Custo"),rs.getInt("TempoEstimado"),rs.getString("Designacao"),TipoServicoDAO.getInstance().get(tipoServico));
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

    public Set<Integer> keySet() {
        Set<Integer> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT idServico FROM Servico")) {
            while (rs.next()) {
                int idc = rs.getInt("idServico");
                res.add(idc);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    @Override
    public Servico put(Integer key, Servico value) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO Servico (idServico) VALUES (?)")){
                pstm.setInt(1, value.getIdServico());
                pstm.executeUpdate();
            }
        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Collection<Servico> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public void putAll(Map<? extends Integer, ? extends Servico> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }

    public Servico remove(Object key) {
        Servico c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement s = conn.createStatement()) {
            s.executeUpdate("DELETE FROM ESIDEAL.Servico WHERE idServico ='"+key+"'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }































}
