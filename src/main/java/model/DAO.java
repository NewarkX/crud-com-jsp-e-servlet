package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
	
	public ArrayList<JavaBeans> listarContatos(){
		//criando um objeto para acessar a classe javabeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement ps = con.prepareStatement(read);
			//resultset armazena temporariamente o retorno do banco
			//de dados em um objeto
			ResultSet rs = ps.executeQuery();
			//o laco sera executado enquanto houver contatos
			while (rs.next()) {
				//variaveis que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				//populando o arraylist
				contatos.add(new JavaBeans(idcon,nome,fone,email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
