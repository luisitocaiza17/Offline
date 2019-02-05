package micotizador.offline.filestructure;

import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class Variedad {

	private BigInteger variedadId;
	
	private BigInteger tipoCultivoId;
	
	private String nombre;

	public BigInteger getVariedadId() {
		return variedadId;
	}

	public void setVariedadId(BigInteger variedadId) {
		this.variedadId = variedadId;
	}

	public BigInteger getTipoCultivoId() {
		return tipoCultivoId;
	}

	public void setTipoCultivoId(BigInteger tipoCultivoId) {
		this.tipoCultivoId = tipoCultivoId;
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
