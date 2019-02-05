package micotizador.offline.filestructure;

import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class Parroquia {
	
	private String ParroquiaId;
	
	private String CantonId;
	
	private String nombre;

	public String getParroquiaId() {
		return ParroquiaId;
	}

	public void setParroquiaId(String parroquiaId) {
		ParroquiaId = parroquiaId;
	}

	public String getCantonId() {
		return CantonId;
	}

	public void setCantonId(String cantonId) {
		CantonId = cantonId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return nombre;
	}
}
