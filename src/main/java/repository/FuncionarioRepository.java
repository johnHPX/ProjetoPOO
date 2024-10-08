/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.PreparedStatement;
import model.Funcionario;
import util.TratamentoException;
import util.TratarErros;
import util.Util;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author roberts
 */
public class FuncionarioRepository {

    
    public void criarFuncionario(Funcionario funcionario) throws TratamentoException {
        ConexaoBD conexao = new ConexaoBD();
        Util util = new Util();
        try {
            conexao.connectar();
            String sql = "INSERT INTO funcionario(NOME, CPF, SENHA, DATA_NASC, ENDERECO) values(?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conexao.conn.prepareStatement(sql);
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getSenha());

            Date sqlDate = util.ConverterStrintParaDate(funcionario.getDataNasc());

            pstmt.setDate(4, sqlDate);
            pstmt.setString(5, funcionario.getEndereco());
            pstmt.executeUpdate();

            pstmt.close();
        } catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } catch (IllegalArgumentException e){
            TratarErros.tratamentoDeConversaoDeTipos(e);
        } finally {
            conexao.fecharConexao();
        }

    }

    public Funcionario LoginFuncionario(String cpf, String senha) throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        Funcionario funcionario = new Funcionario();
        Util util = new Util();
        try{
            conexaoBD.connectar();
            String sql = "SELECT * FROM funcionario WHERE cpf = ? AND senha = ?";
            PreparedStatement pstmt = conexaoBD.conn.prepareStatement(sql);
            pstmt.setString(1, cpf);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                funcionario.setId(rs.getInt("ID"));
                funcionario.setNome(rs.getString("NOME"));
                funcionario.setCpf(rs.getString("CPF"));
                funcionario.setSenha(rs.getString("SENHA"));

                String dataNasc = util.ConverterDateParaString(rs.getDate("DATA_NASC"));

                funcionario.setDataNasc(dataNasc);
                funcionario.setEndereco(rs.getString("ENDERECO"));
            }

            rs.close();
            pstmt.close();

        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } catch (IllegalArgumentException e){
            TratarErros.tratamentoDeConversaoDeTipos(e);
        } finally {
            conexaoBD.fecharConexao();
        }
        return funcionario;
    }



    public ArrayList<Funcionario> listarTodosFuncionarios() throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
        Util util = new Util();
        try{
            conexaoBD.connectar();
            String sql = "SELECT * FROM funcionario";
            Statement stmt = conexaoBD.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("ID"));
                f.setNome(rs.getString("NOME"));
                f.setCpf(rs.getString("CPF"));
                f.setSenha(rs.getString("SENHA"));

                String dataNasc = util.ConverterDateParaString(rs.getDate("DATA_NASC"));

                f.setDataNasc(dataNasc);
                f.setEndereco(rs.getString("ENDERECO"));
                funcionarios.add(f);
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
        return funcionarios;
    }

    public void alterarFuncionario(Funcionario funcionario) throws TratamentoException {
        ConexaoBD conexaoBD = new ConexaoBD();
        Util util = new Util();
        try {
            String sqlText = "UPDATE funcionario SET NOME = ?, CPF = ?, SENHA = ?, ENDERECO = ?, DATA_NASC = ? WHERE ID = ?";
            conexaoBD.connectar();
            PreparedStatement pstmt = conexaoBD.conn.prepareStatement(sqlText);
            pstmt.setInt(6, funcionario.getId());
            pstmt.setString(1, funcionario.getNome());
            pstmt.setString(2, funcionario.getCpf());
            pstmt.setString(3, funcionario.getSenha());
            pstmt.setString(4, funcionario.getEndereco());

            Date sqlDate = util.ConverterStrintParaDate(funcionario.getDataNasc());

            pstmt.setDate(5, sqlDate);
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

    public void removerFuncionario(int id) throws TratamentoException {
        ConexaoBD conexaoDB = new ConexaoBD();
        try{
            String sql = "DELETE FROM funcionario WHERE ID = ?";
            conexaoDB.connectar();

            PreparedStatement pstmt = conexaoDB.conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
        }catch (SQLException e) {
            TratarErros.tratamentoDeErroBancoDeDados(e);
        } finally {
            conexaoDB.fecharConexao();
        }
    }
        
}
    

