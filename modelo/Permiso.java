package modelo;

public class Permiso {
	private Rol rol;
	private Cartelera cartelera;
	private Operacion permiso;
	
	public Permiso(){
		
	}

	public Permiso(Rol rol, Cartelera cartelera, Operacion permiso) {
		super();
		this.rol = rol;
		this.cartelera = cartelera;
		this.permiso = permiso;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Cartelera getCartelera() {
		return cartelera;
	}

	public void setCartelera(Cartelera cartelera) {
		this.cartelera = cartelera;
	}

	public Operacion getPermiso() {
		return permiso;
	}

	public void setPermiso(Operacion permiso) {
		this.permiso = permiso;
	}
	
}
