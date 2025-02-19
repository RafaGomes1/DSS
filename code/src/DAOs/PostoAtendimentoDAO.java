package DAOs;

import oficina.servico.PostoAtendimento;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PostoAtendimentoDAO implements Map<String, PostoAtendimento> {

    private static PostoAtendimentoDAO singleton = null;

    private PostoAtendimentoDAO() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS ESIDEAL.PostoAtendimento (" +
                         "idPostoAtendimento INT NOT NULL," +
                         "TipoServico INT NOT NULL," +
                         "FOREIGN KEY (TipoServico) REFERENCES ESIDEAL.TipoServico(Tipo)," +
                         "PRIMARY KEY (idPostoAtendimento));";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PostoAtendimentoDAO getInstance(){
        if(PostoAtendimentoDAO.singleton == null){
            PostoAtendimentoDAO.singleton = new PostoAtendimentoDAO();
        }
        return PostoAtendimentoDAO.singleton;
    }

    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE ESIDEAL.PostoAtendimento");
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
                     s.executeQuery("SELECT idPostoAtendimento FROM ESIDEAL.PostoAtendimento WHERE idPostoAtendimento ='" + key + "'")){
            b = r.next();
        }catch (SQLException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return b;
    }

    public boolean containsValue(Object value) {
        PostoAtendimento c = (PostoAtendimento) value;
        return this.containsKey(c.getIdPostoAtendimento());
    }

    public Set<Entry<String, PostoAtendimento>> entrySet() {
        return this.keySet().stream().map(k -> Map.entry(k, this.get(k))).collect(Collectors.toSet());
    }

    @Override
    public PostoAtendimento get(Object key) {
        PostoAtendimento r = null;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM ESIDEAL.PostoAtendimento WHERE idPostoAtendimento='"+key+"';")) {
            if (rs.next()){
                int idPosto = rs.getInt("idPostoAtendimento");
                String tipoServico = rs.getString("TipoServico");
               r = new PostoAtendimento(idPosto, AgendamentoDAO.getInstance(), TipoServicoDAO.getInstance().get(tipoServico));
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

    public Set<String> keySet() {
        Set<String> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT idPostoAtendimento FROM PostoAtendimento")) {
            while (rs.next()) {
                String idc = rs.getString("idPostoAtendimento");
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
    public PostoAtendimento put(String key, PostoAtendimento value) {
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO Campeonatos (Nome) VALUES (?)")){
                pstm.setInt(1, value.getIdPostoAtendimento());
                pstm.executeUpdate();
            }
        }catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return value;
    }

    public Collection<PostoAtendimento> values() {
        return this.keySet().stream().map(this::get).collect(Collectors.toList());
    }

    public void putAll(Map<? extends String, ? extends PostoAtendimento> m) {
        m.keySet().forEach(i -> this.put(i, m.get(i)));
    }

    @Override
    public PostoAtendimento remove(Object key) {
        PostoAtendimento c = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement s = conn.createStatement()) {
            s.executeUpdate("DELETE FROM ESIDEAL.PostoAtendimento WHERE idPostoAtendimento ='"+key+"'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return c;
    }

    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOConfig.URL, DAOConfig.USERNAME, DAOConfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM ESIDEAL.PostoAtendimento")) {
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