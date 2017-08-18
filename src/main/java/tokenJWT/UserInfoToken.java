package tokenJWT;

import java.util.HashSet;
import java.util.Set;

import modelo.Rol;
import modelo.Usuario;
import modelo.Cartelera;
import modeloDAO.UsuarioDAO;


public class UserInfoToken{
	
	private long id;
	private String user;
	private String profilePic;
	private Rol rol;
	private Set<Long> cartelerasModificarId = new HashSet<Long>();
	private Set<Long> cartelerasEliminarId = new HashSet<Long>();
	
	public UserInfoToken(){}
	
	public UserInfoToken(Usuario usuario){
		this.id = usuario.getId();
		this.user = usuario.getUser();
		this.rol = usuario.getRol();
		this.profilePic = usuario.getProfilePic();
		for (Cartelera cartelera : usuario.getCartelerasEliminar()) {	
			this.cartelerasEliminarId.add(cartelera.getId());
		}
		for (Cartelera cartelera : usuario.getCartelerasModificar()) {	
			this.cartelerasModificarId.add(cartelera.getId());
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public String getProfilePic() {
		return profilePic;
	}

	@Override
	public String toString() {
		return "UserInfoToken [id=" + id + ", user=" + user + ", rol=" + rol + "]";
	}

	public Set<Long> getCartelerasModificar() {
		return cartelerasModificarId;
	}

	public Set<Long> getCartelerasEliminar() {
		return cartelerasEliminarId;
	}

}
