package tokenJWT;

import modelo.Rol;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

public class UserInfoToken{
	
	private long id;
	private String user;
	private Rol rol;
	
	public UserInfoToken(){}
	
	public UserInfoToken(Usuario usuario){
		this.id = usuario.getId();
		this.user = usuario.getUser();
		this.rol = usuario.getRol();
	}

	public long getId() {
		return id;
	}

	public void setId(long userID) {
		this.id = userID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Usuario getUsuario(UsuarioDAO dao){
		return dao.getById(id);
	}
	
	public Rol getRol(){
		return this.rol;
	}

	@Override
	public String toString() {
		return "UserInfoToken [userID=" + id + ", user=" + user + ", rol=" + rol + "]";
	}

}
