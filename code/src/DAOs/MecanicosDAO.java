package DAOs;

import oficina.servico.Mecanico;
import oficina.utilizador.Utilizador;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MecanicosDAO implements Map<String, Mecanico> {

    private static MecanicosDAO singleton = null;

    private MecanicosDAO(){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement st = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Mecanico (" +
                         "idMecanico VARCHAR(50) NOT NULL," +
                         "TipoCompetencia INT NOT NULL," +
                         "UltimoInicioTurno DATETIME NOT NULL," +
                         "UltimoFimTurno DATETIME NOT NULL," +
                         "FOREIGN KEY (idMecanico) REFERENCES ESIDEAL.Utilizador(idUtilizador)," +
                         "FOREIGN KEY (TipoCompetencia) REFERENCES ESIDEAL.TipoServico(Tipo)," +
                         "PRIMARY KEY (idMecanico))";
            st.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static MecanicosDAO getInstance(){
        if (MecanicosDAO.singleton == null){
            MecanicosDAO.singleton = new MecanicosDAO();
        }
        return MecanicosDAO.singleton;
    }

    public void clear(){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            stm.executeUpdate("TRUNCATE Mecanico ");
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public int size(){
        int i = 0;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT count(*) FROM ESIDEAL.Mecanico;");

            if (rs.next()){
                i = rs.getInt(1);
            }
        } catch (SQLException e){
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    public boolean isEmpty(){
        return this.size() == 0;
    }

    public boolean containsKey(Object key){
        boolean r;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idMecanico FROM ESIDEAL.Mecanico WHERE idMecanico='"+key+"'");
            r = rs.next();
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Mecanico m = (Mecanico) value;
        return this.containsKey(m.getIdUtilizador());
    }

    @Override
    public Set<Entry<String, Mecanico>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    @Override
    public Mecanico get(Object key) {
        Mecanico m = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement uStm = conn.createStatement();
            ResultSet setU = uStm.executeQuery("SELECT * FROM Utilizador WHERE idUtilizador='"+key+"'");
            Statement stm = conn.createStatement();
            ResultSet mc = stm.executeQuery("SELECT * FROM Mecanico WHERE idMecanico='"+key+"'");
            if (mc.next()){
                String mec = mc.getString("idMecanico");
                m = new Mecanico(UtlizadoresDAO.getInstance().get(mc.getString("idMecanico")),mc.getObject("UltimoInicioTurno", LocalDateTime.class),mc.getObject("UltimoFimTurno", LocalDateTime.class),TipoServicoDAO.getInstance().get(mc.getInt("TipoCompetencia")));
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return m;
    }

    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT  idMecanico FROM Mecanico");

            while (rs.next()){
                String idm = rs.getString("idMecanico");
                res.add(idm);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Collection<Mecanico> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public Mecanico put(String key, Mecanico value){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement st =conn.createStatement();

            if (this.containsKey(key)){
                PreparedStatement pstm1 = conn.prepareStatement("UPDATE Mecanico WHERE idMecanico= ?");
                pstm1.setString(1,key);
                pstm1.executeUpdate();
            }
            else {
                PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO  Mecanico(idMecanico,Nome,Password,turno,competencias) VALUES (?,?,?,?,?)");
                pstm2.setString(1, value.getIdUtilizador());
                pstm2.setString(2, value.getNome());
                pstm2.setString(3,value.getPassword());
//                pstm2.setString(4,value.getTurno());

                pstm2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public LocalDateTime getInicioTurno(String key)
    {
        LocalDateTime turno = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet mc = stm.executeQuery("SELECT * FROM Mecanico WHERE idMecanico='"+key+"'");
            if (mc.next()){
                String mec = mc.getString("idMecanico");
                turno = mc.getObject("UltimoInicioTurno",LocalDateTime.class);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return turno;
    }

    public LocalDateTime getFimTurno(String key)
    {
        LocalDateTime turno = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet mc = stm.executeQuery("SELECT * FROM Mecanico WHERE idMecanico='"+key+"'");
            if (mc.next()){
                String mec = mc.getString("idMecanico");
                turno = mc.getObject("UltimoFimTurno",LocalDateTime.class);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return turno;
    }

    public void updateTurnoInicio(String key,LocalDateTime inicio){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);

            if (this.containsKey(key)){
                PreparedStatement pstm = conn.prepareStatement("Update Mecanico SET UltimoInicioTurno = ? WHERE idMecanico='"+key+"'");
                pstm.setObject(1,inicio);
                pstm.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public void updateTurnoFim(String key,LocalDateTime fim){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);

            if (this.containsKey(key)){
                PreparedStatement pstm = conn.prepareStatement("Update Mecanico SET UltimoFimTurno = ? WHERE idMecanico='"+key+"'");
                pstm.setObject(1,fim);
                pstm.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public Mecanico remove (Object key){
        Mecanico value = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm3 = conn.createStatement();

            PreparedStatement pstm = conn.prepareStatement("DELETE FROM Mecanico WHERE idMecanico= ?");
            value = this.get(key);
            pstm.setString(1,(String) key);
            pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public void putAll(Map<? extends String, ? extends Mecanico> m){
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }
}

