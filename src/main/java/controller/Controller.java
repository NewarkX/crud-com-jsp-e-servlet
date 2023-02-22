package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/main")) {
			contatos(request, response);
		} else if(action.equals("/insert")){
			novoContato(request,response);
		} else {
			response.sendRedirect("index.html");
		}
	}
	
	//novo contato
	private void novoContato(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//seta os campos do formulario dentro do objeto contato
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));  
		contato.setEmail(request.getParameter("email"));
		//chama o metodo de inserir passando o contato
		dao.inserirContato(contato);
		//redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//criando um objeto que ira receber os dados javabeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		//encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

}
