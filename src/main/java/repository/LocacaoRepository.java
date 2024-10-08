package repository;

import model.Locacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import util.TratamentoException;
import util.TratarErros;
import util.Util;

public class LocacaoRepository {

    public void criarLocacao(Locacao locacao) throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        Util util = new Util();
        try{
            conexaoBD.connectar();
            String sql = "INSERT INTO locacao (USUARIO_FK, FUNCIONARIO_FK, EDICAO_COD, PRAZO, DATA_LOCACAO, ANO, ABERTA) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conexaoBD.conn.prepareStatement(sql);
            pstmt.setInt(1, locacao.getUsuario_id());
            pstmt.setInt(2, locacao.getFuncionario_id());
            pstmt.setInt(3, locacao.getEdicao_cod());

            Date prazo = util.ConverterStrintParaDate(locacao.getPrazo());
            pstmt.setDate(4, prazo);

            Date dataLocacao = util.ConverterStrintParaDate(locacao.getData_locacao());
            pstmt.setDate(5, dataLocacao);


            pstmt.setInt(6, locacao.getAno());
            pstmt.setBoolean(7, locacao.isAberta());
            pstmt.executeUpdate();

            pstmt.close();

        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } catch (IllegalArgumentException e){
            TratarErros.tratamentoDeConversaoDeTipos(e);
        } finally {
            conexaoBD.fecharConexao();
        }
    }

    public ArrayList<Locacao> listarTodasLocacoes() throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        ArrayList<Locacao> locacoes = new ArrayList<Locacao>();
        Util util = new Util();
        try{
            conexaoBD.connectar();
            String sql = "SELECT * FROM locacao";
            Statement stmt = conexaoBD.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                Locacao locacao = new Locacao();
                locacao.setId(rs.getInt("ID"));
                locacao.setUsuario_id(rs.getInt("USUARIO_FK"));
                locacao.setFuncionario_id(rs.getInt("FUNCIONARIO_FK"));
                locacao.setEdicao_cod(rs.getInt("EDICAO_COD"));

                String prazo = util.ConverterDateParaString(rs.getDate("PRAZO"));
                locacao.setPrazo(prazo);

                String dataLocacao = util.ConverterDateParaString(rs.getDate("DATA_LOCACAO"));
                locacao.setData_locacao(dataLocacao);

                locacao.setAno(rs.getInt("ANO"));
                locacao.setAberta(rs.getBoolean("ABERTA"));
                locacoes.add(locacao);
            }

            rs.close();
            stmt.close();
        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } catch (IllegalArgumentException e){
            TratarErros.tratamentoDeConversaoDeTipos(e);
        } finally {
            conexaoBD.fecharConexao();
        }

        return locacoes;
    }

    public void atualizarLocacao(Locacao locacao) throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        Util util = new Util();

        try{
            conexaoBD.connectar();
            String sql = "UPDATE locacao SET USUARIO_FK = ?, FUNCIONARIO_FK = ?, EDICAO_COD = ?, PRAZO = ?, DATA_LOCACAO = ?, ANO = ?, ABERTA = ? WHERE ID = ?";
            PreparedStatement pstmt = conexaoBD.conn.prepareStatement(sql);
            pstmt.setInt(1, locacao.getUsuario_id());
            pstmt.setInt(2, locacao.getFuncionario_id());
            pstmt.setInt(3, locacao.getEdicao_cod());

            Date prazo = util.ConverterStrintParaDate(locacao.getPrazo());
            pstmt.setDate(4, prazo);

            Date dataLocacao = util.ConverterStrintParaDate(locacao.getData_locacao());
            pstmt.setDate(5, dataLocacao);


            pstmt.setInt(6, locacao.getAno());
            pstmt.setBoolean(7, locacao.isAberta());
            pstmt.setInt(8, locacao.getId());
            pstmt.executeUpdate();

            pstmt.close();

        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } catch (IllegalArgumentException e){
            TratarErros.tratamentoDeConversaoDeTipos(e);
        } finally {
            conexaoBD.fecharConexao();
        }
    }

    public void removerLocacao(int id) throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        try{
            conexaoBD.connectar();
            String sql = "DELETE FROM locacao WHERE ID = ?";
            PreparedStatement pstmt = conexaoBD.conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();

        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        }finally {
            conexaoBD.fecharConexao();
        }
    }

}
