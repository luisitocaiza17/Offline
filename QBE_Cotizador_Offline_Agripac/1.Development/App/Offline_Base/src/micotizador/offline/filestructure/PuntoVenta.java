package micotizador.offline.filestructure;

import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class PuntoVenta 
{

	private String puntoVentaID;
	
	private String nombre;

	public String getPuntoVentaID() {
		return puntoVentaID;
	}

	public void setPuntoVentaID(String puntoVentaID) {
		this.puntoVentaID = puntoVentaID;
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
