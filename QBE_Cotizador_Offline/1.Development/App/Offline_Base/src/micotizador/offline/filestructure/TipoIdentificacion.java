package micotizador.offline.filestructure;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class TipoIdentificacion {
	private String id;

	private String nombre;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public  String toString() {
		return nombre;
	}
}
