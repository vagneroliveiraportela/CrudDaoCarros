package br.edu.ifnmg.carros.dao;

import br.edu.ifnmg.carros.entidade.Carro;
import br.edu.ifnmg.carros.util.FabricaConexao;
import br.edu.ifnmg.carros.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Danilo Souza Almeida
 */
public class CarroDAO implements CrudDAO<Carro>{
    
    @Override
    public void salvar(Carro carro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(carro.getId() == null){
                ps = conexao.prepareStatement("INSERT INTO `tcarro` (`modelo`,`fabricante`,`cor`,`ano`) VALUES (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update tcarro set modelo=?, fabricante=?, cor=?, ano=? where id=?");
                ps.setInt(5, carro.getId());
            }
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getFabricante());
            ps.setString(3, carro.getCor());
            ps.setDate(4, new Date(carro.getAno().getTime()));
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar!", ex);
        }
    }
    
    @Override
    public void deletar(Carro carro) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps  = conexao.prepareStatement("delete from tcarro where id = ?");
            ps.setInt(1, carro.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o carro!", ex);
        }
    }
    
    @Override
    public List<Carro> buscar() throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from tcarro");
            ResultSet resultSet = ps.executeQuery();
            List<Carro> carros = new ArrayList<>();
            while(resultSet.next()){
                Carro carro = new Carro();
                carro.setId(resultSet.getInt("id"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setFabricante(resultSet.getString("fabricante"));
                carro.setCor(resultSet.getString("cor"));
                carro.setAno(resultSet.getDate("ano"));
                carros.add(carro);
            }
            FabricaConexao.fecharConexao();
            return carros;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os carros!",ex);
        }
    }
}
