package micotizador.offline.filestructure;

import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class ParametrosXCanal {
	
	private BigInteger CanalId;
	
	private BigInteger TipoCultivoId;
	
	private String NombrePlantilla;

	private String contenedorNumero;

	public String getContenedorNumero() {
		return contenedorNumero;
	}

	public void setContenedorNumero(String contenedorNumero) {
		this.contenedorNumero = contenedorNumero;
	}

	private byte[] plantillaHtml;

	public byte[] getPlantillaHtml() {
		return plantillaHtml;
	}

	public void setPlantillaHtml(byte[] plantillaHtml) {
		this.plantillaHtml = plantillaHtml;
	}

	public BigInteger getCanalId() {
		return CanalId;
	}

	public void setCanalId(BigInteger canalId) {
		CanalId = canalId;
	}

	public BigInteger getTipoCultivoId() {
		return TipoCultivoId;
	}

	public void setTipoCultivoId(BigInteger tipoCultivoId) {
		TipoCultivoId = tipoCultivoId;
	}

	public String getNombrePlantilla() {
		return NombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		NombrePlantilla = nombrePlantilla;
	}

}
