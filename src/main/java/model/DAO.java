package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DAO {
  // modulo de conexao	
  //parametros de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = 
			"jdbc:mysql://127.0.0.1:3306/dbagenda?"
			+ "useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";
	
  //metodo de conexao
	private Connection conectar() {
		Connection con = null;
		try {
			//para pegar o driver do banco
			Class.forName(driver); 
			//tenta conectar ao branco
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email)"
				+ "values (?,?,?)";
		try {
			//abrir a conexao com o banco
			Connection con = conectar();
			//preparar a query para ser executada
			PreparedStatement ps = con.prepareStatement(create);
			//substituir os parametros pelo conteudo das variaveis
			ps.setString(1, contato.getNome());
			ps.setString(2, contato.getFone());
			ps.setString(3, contato.getEmail());
			//executar a query
			ps.executeUpdate();
			//encerrar a conexao com banco
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
