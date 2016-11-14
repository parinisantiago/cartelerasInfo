package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Rol;
import modeloDAO.RolDAO;
import modeloDAOJPA.RolJpaDAO;

/**
 * Servlet implementation class Pruebas
 */
public class Pruebas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pruebas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Rol rol = new Rol();
		rol.setNombre("lorelei");

		RolDAO dao = new RolJpaDAO();
		
		dao.persist(rol);
		
		rol = new Rol();
		rol.setNombre("alala");
		dao.persist(rol);
		
		System.out.println("all: "+dao.selectAll());
		
		rol.setNombre("sin nombre"); 
		rol = dao.getById(new Long(1));
		System.out.println("nombre de bd con id 1: "+rol.getNombre());
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
