package tokenJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;


@Service
public class LoginService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;

	public void logout(Usuario user) {
		
	}
	
	public void logout() {
		
	}
	
	public Usuario login(String user, String password) {
		Usuario u = usuarioDAO.getByUser(user);
		if(u != null){
			if(u.getPassword().equals(password)){
				return u;
			}
		}
		throw new IllegalArgumentException("Usuario o password invalido");
	}

	
}
