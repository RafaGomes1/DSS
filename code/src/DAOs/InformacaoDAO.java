package DAOs;

import oficina.veiculo.Informacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InformacaoDAO implements Map<Integer, Informacao> {

    private static InformacaoDAO singleton = null;

    private InformacaoDAO() {

        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Informacao (" +
                         "idInfo INT NOT NULL," +
                         "Informacao TEXT NOT NULL," +
                         "DataHora DATETIME NOT NULL," +
                         "idVeiculo VARCHAR(8) NOT NULL," +
                         "FOREIGN KEY (idVeiculo) REFERENCES ESIDEAL.Veiculo(Matricula)," +
                         "PRIMARY KEY (IdInfo))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static InformacaoDAO getInstance() {
        if (InformacaoDAO.singleton == null) {
            InformacaoDAO.singleton = new InformacaoDAO();
        }
        return InformacaoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Informacao ");
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

            ResultSet rs = st.executeQuery("SELECT count(*) FROM ESIDEAL.Informacao;");

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
            ResultSet rs = stm.executeQuery("SELECT idInfo FROM ESIDEAL.Informacao WHERE idInfo='" + key + "'");
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Informacao u = (Informacao) value;
        return this.containsKey(u.getIdInformacao());
    }

    public Set<Entry<Integer, Informacao>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    public Informacao get(Object key) {
        Informacao u = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet us = stm.executeQuery("SELECT * FROM Informacao WHERE idInfo='" + key + "'");
            u = us.next() ? new Informacao(us.getInt("idInformação"), us.getString("Info"), us.getObject("Data", LocalDateTime.class)) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return u;
    }

    public List<Informacao> getInfosVeiculo(Object key){
        List<Informacao> infos = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet iv = stm.executeQuery("SELECT * FROM Informacao WHERE idVeiculo='"+key+"'");
            while (iv.next()){
                Informacao info = new Informacao(iv.getInt("idInfo"),iv.getString("Info"),iv.getObject("DataHora", LocalDateTime.class));
                infos.add(info);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return infos;
    }


    @Override
    public Set<Integer> keySet() {
        Set<Integer> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idInfo FROM Informacao");

            while (rs.next()) {
                Integer idu = rs.getInt("idInfo");
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
    public Collection<Informacao> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public Informacao put(Integer key, Informacao value) {
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();

            if (this.containsKey(key)) {
                PreparedStatement pstm1 = conn.prepareStatement("UPDATE Informacao WHERE idInfo = ?");
                pstm1.setInt(1, key);
                pstm1.executeUpdate();
            } else {
                PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO Informacao (idInfo,Informacao,DataHora,idVeiculo) VALUES (?,?,?,?)");
                pstm2.setInt(1, value.getIdInformacao());
                pstm2.setString(2, value.getInfo());
                pstm2.setObject(3,value.getData());
                pstm2.setObject(4,"AB-12-34");
                pstm2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Informacao remove(Object key) {
        Informacao value = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm3 = conn.createStatement();

            PreparedStatement pstm = conn.prepareStatement("DELETE  FROM Informacao WHERE idInfo = ?");
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
    public void putAll(Map<? extends Integer, ? extends Informacao> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }
}
