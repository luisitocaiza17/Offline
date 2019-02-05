//package com.qbe.cotizador.servlets.producto.agricola;
package micotizador.offline.filestructure;
import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class Provincia {
	
	private String ProvinciaId;
	
	private String nombre;

	public String getProvinciaId() {
		return ProvinciaId;
	}

	public void setProvinciaId(String provinciaId) {
		ProvinciaId = provinciaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public  String toString () {
		return nombre;
	}
}
