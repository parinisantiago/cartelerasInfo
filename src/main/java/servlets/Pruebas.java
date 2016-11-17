package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Anuncio;
import modelo.Cartelera;
import modelo.Rol;
import modelo.Usuario;
import modeloDAO.AnuncioDAO;
import modeloDAO.CarteleraDAO;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;
import modeloDAOJPA.AnuncioJpaDAO;
import modeloDAOJPA.CarteleraJpaDAO;
import modeloDAOJPA.RolJpaDAO;
import modeloDAOJPA.UsuarioJpaDAO;

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
//		Rol rol = new Rol();
//		rol.setNombre("lorelei");
//
//		RolDAO dao = new RolJpaDAO();
//		
//		dao.persist(rol);
//		
//		rol = new Rol();
//		rol.setNombre("alala");
//		dao.persist(rol);
//		
//		System.out.println("all: "+dao.selectAll());
//		
//		rol.setNombre("sin nombre"); 
//		rol = dao.getByNombre("lorelei");
//		System.out.println("nombre de bd con id 1: "+rol.getNombre());
		
//		Cartelera cartelera = new Cartelera("juajua");
//		cartelera.setHabilitado(false);
//		CarteleraDAO Cdao = new CarteleraJpaDAO();
//		
//		Cdao.persist(cartelera);
//		
//		cartelera = new Cartelera("zzz");
//		Cdao.persist(cartelera);
//		
//		cartelera = Cdao.getByTitulo("juaJua");
//		System.out.println("habilitado: " + cartelera.isHabilitado());
		
//		Rol rol = new Rol("docente");
//		new RolJpaDAO().persist(rol);
//		
//		Usuario usuario = new Usuario("juan", "juan", true, rol);
//		UsuarioDAO udao = new UsuarioJpaDAO();
//		
//		udao.persist(usuario);
//		usuario = new Usuario("pepe", "pepe", false, rol);
//		udao.persist(usuario);
//		
//		usuario= udao.getByUser("juan");
//		System.out.println("usuario nombre: " + usuario.getUser());
//		
//		System.out.println("usuarios con rol: " + rol.getNombre() + " " + udao.getAllWithRol(rol) );
		
//		Date date1 = null;
//		Date date2 = null;
//		try {
//			date1 = new Date();
//			Thread.sleep(15000);
//			date2 = new Date();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Anuncio anuncio1 = new Anuncio();
//		anuncio1.setFecha(date2);
//		anuncio1.setTitulo("a1");
//		anuncio1.setCuerpo("uwuwuwuwuwuw");
//		Anuncio anuncio2 = new Anuncio();
//		
//		anuncio2.setFecha(date1);
//		anuncio2.setTitulo("a12");
//		anuncio2.setCuerpo("alalalalalalla");
//		
//		AnuncioDAO adao = new AnuncioJpaDAO();
//		
//		adao.persist(anuncio1);
//		adao.persist(anuncio2);
//		
//		List<Anuncio> list = adao.getAllOrderByNewer();
//		
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println("orden " + i + " titulo: " + list.get(i).getTitulo() + " fecha: "+ list.get(i).getFecha());
//		}
		
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
