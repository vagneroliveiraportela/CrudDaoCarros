package br.edu.ifnmg.carros.util;

import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Danilo Souza Almeida
 */
public class FabricaConexao {
    
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost/livro";
    private static final String USUARIO = "root";
    private static final String SENHA = "livro123";
    private static final String DRIVER_SQL = "com.mysql.jdbc.Driver";

    public static Connection getConexao() throws ErroSistema {
        if(conexao == null){
            try {
                Class.forName(DRIVER_SQL);
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível conectar ao banco de dados!", ex);
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("O driver do banco de dados não foi encontrado!", ex);
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() throws ErroSistema{
        if(conexao != null){
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar conexão com o banco de dados!", ex);
            }
        }
    }
    
}
