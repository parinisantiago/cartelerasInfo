package tokenJWT;

import modelo.Rol;
import modelo.Usuario;
import modeloDAO.RolDAO;
import modeloDAO.UsuarioDAO;

public class UserInfoToken{
	
	private long userID;
	private String user;
	private Rol rol;
	
	public UserInfoToken(){}
	
	public UserInfoToken(Usuario usuario){
		this.userID = usuario.getId();
		this.user = usuario.getUser();
		this.rol = usuario.getRol();
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Usuario getUsuario(UsuarioDAO dao){
		return dao.getById(userID);
	}
	
	public Rol getRol(){
		return this.rol;
	}

	@Override
	public String toString() {
		return "UserInfoToken [userID=" + userID + ", user=" + user + ", rol=" + rol + "]";
	}

}
