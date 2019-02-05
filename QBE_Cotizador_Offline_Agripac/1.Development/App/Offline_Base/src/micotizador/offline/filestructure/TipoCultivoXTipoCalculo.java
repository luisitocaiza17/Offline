package micotizador.offline.filestructure;

import java.math.BigInteger;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class TipoCultivoXTipoCalculo {
	
	private BigInteger TipoCalculoId;
	
	private BigInteger TipoCultivoId;
	
	private byte[] coberturaTexto;
	
	private byte[] deducibleTexto;
	
	private byte[] metodoIndemnizacionTexto;

	public BigInteger getTipoCalculoId() {
		return TipoCalculoId;
	}

	public void setTipoCalculoId(BigInteger tipoCalculoId) {
		TipoCalculoId = tipoCalculoId;
	}

	public BigInteger getTipoCultivoId() {
		return TipoCultivoId;
	}

	public void setTipoCultivoId(BigInteger tipoCultivoId) {
		TipoCultivoId = tipoCultivoId;
	}

	public byte[] getCoberturaTexto() {
		return coberturaTexto;
	}

	public void setCoberturaTexto(byte[] coberturaTexto) {
		this.coberturaTexto = coberturaTexto;
	}

	public byte[] getDeducibleTexto() {
		return deducibleTexto;
	}

	public void setDeducibleTexto(byte[] deducibleTexto) {
		this.deducibleTexto = deducibleTexto;
	}

	public byte[] getMetodoIndemnizacionTexto() {
		return metodoIndemnizacionTexto;
	}

	public void setMetodoIndemnizacionTexto(byte[] metodoIndemnizacionTexto) {
		this.metodoIndemnizacionTexto = metodoIndemnizacionTexto;
	}
}
