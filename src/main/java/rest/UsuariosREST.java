package rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;
import modeloDAOJPA.UsuarioJpaDAO;

@RestController
public class UsuariosREST {

	private UsuarioDAO dao = new UsuarioJpaDAO();
	
    @RequestMapping(value="/usuario", method=RequestMethod.GET)
    public Usuario usuarioById(@RequestParam(value="id", defaultValue="0") Long id) {
        return dao.getById(id);
    }
}
