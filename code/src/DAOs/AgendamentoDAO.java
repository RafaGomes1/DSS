package DAOs;

import oficina.servico.Agendamento;
import oficina.servico.Servico;
import oficina.veiculo.Veiculo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AgendamentoDAO implements Map<Integer, Agendamento> {

    private static AgendamentoDAO singleton = null;

    private AgendamentoDAO() {

        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.Agendamento (" +
                         "idAgendamento INT NOT NULL," +
                         "DataHora DATETIME NOT NULL," +
                         "idServico INT NOT NULL," +
                         "idPostoAtendimento INT NOT NULL," +
                         "idVeiculo INT NOT NULL," +
                         "FOREIGN KEY (idServico) REFERENCES ESIDEAL.Servico(idServico)," +
                         "FOREIGN KEY (idPostoAtendimento) REFERENCES ESIDEAL.PostoAtendimento(idPostoAtendimento)," +
                         "FOREIGN KEY (idVeiculo) REFERENCES ESIDEAL.Veiculo(Matricula)," +
                         "PRIMARY KEY (idAgendamento));";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static AgendamentoDAO getInstance() {
        if (AgendamentoDAO.singleton == null) {
            AgendamentoDAO.singleton = new AgendamentoDAO();
        }
        return AgendamentoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Agendamento ");
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

            ResultSet rs = st.executeQuery("SELECT count(*) FROM ESIDEAL.Agendamento;");

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
            ResultSet rs = stm.executeQuery("SELECT idAgendamento FROM ESIDEAL.Agendamento WHERE idAgendamento=" + key + "");
            r = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Agendamento a = (Agendamento) value;
        return this.containsKey(a.getIdAgendamento());
    }

    public Set<Entry<Integer, Agendamento>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k,this.get(k))).collect(Collectors.toSet());
    }

    public Agendamento get(Object key) {
        Agendamento a = null;
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet us = stm.executeQuery("SELECT * FROM Agendamento WHERE idAgendamento="+key);
            if (us.next()){
                String idServico = us.getString("idServico");
                String idVeiculo = us.getString("idVeiculo");
                a = new Agendamento(us.getInt("idAgendamento"), us.getObject("DataHora", LocalDateTime.class),ServicoDAO.getInstance().get(idServico), VeiculoDAO.getInstance().get(idVeiculo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    public List<Agendamento> getListaAgendamentos(Integer key){
        List<Agendamento> lista = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL,DAOConfig.USERNAME,DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet ag = stm.executeQuery("SELECT * FROM Agendamento WHERE idPostoAtendimento="+key);
            while (ag.next()){
                String idServico = ag.getString("idServico");
                String idVeiculo = ag.getString("idVeiculo");
                Agendamento a = new Agendamento(ag.getInt("idAgendamento"), ag.getObject("DataHora", LocalDateTime.class),ServicoDAO.getInstance().get(idServico),VeiculoDAO.getInstance().get(idVeiculo));
                lista.add(a);
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return lista;
    }




    @Override
    public Set<Integer> keySet() {
        Set<Integer> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idAgendamento FROM Agendamento");

            while (rs.next()) {
                Integer idu = rs.getInt("idAgendamento");
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
    public Collection<Agendamento> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public Agendamento put(Integer key, Agendamento value){
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            Statement stm = conn.createStatement();
            if (this.containsKey(value.getIdAgendamento())){
                PreparedStatement pstm1 = conn.prepareStatement("UPDATE Agendamento WHERE idAgendamento = ?");
                pstm1.setInt(1,key);
                pstm1.executeUpdate();
            }
            else {
                PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO Agendamento (idAgendamento,DataHora,idServico,idPostoAtendimento,idVeiculo) VALUES (?,?,?,?,?)");
                pstm2.setInt(1,value.getIdAgendamento());
                pstm2.setObject(2,value.getDataHora(), Types.TIMESTAMP);
                pstm2.setInt(3,value.getServico().getIdServico());
                pstm2.setInt(4,key);
                pstm2.setString(5,value.getVeiculo().getMatricula());
                pstm2.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Agendamento remove (Object key){
        Agendamento value = this.get(key);
        try {
            Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
            PreparedStatement pstm = conn.prepareStatement("DELETE  FROM Agendamento WHERE idAgendamento = "+key);
            pstm.executeUpdate();
        }  catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Agendamento> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }


}
