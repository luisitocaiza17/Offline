package micotizador.offline.filestructure;

import java.math.BigInteger;
import java.util.Date;
/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class Regla {

	private BigInteger ReglaId;

	private Date aceptabilidadDesde;

	private Date aceptabilidadHasta;

	private BigInteger CantonId;

	private BigInteger CicloId;

	private float costoProduccion;

	private String observaciones;

	private float costoMantenimiento;

	private BigInteger ProvinciaId;

	private float tasa;

	private BigInteger TipoCalculoId;

	private BigInteger TipoCultivoId;


	public BigInteger getReglaId() {
		return ReglaId;
	}

	public void setReglaId(BigInteger reglaId) {
		ReglaId = reglaId;
	}

	public Date getAceptabilidadDesde() {
		return aceptabilidadDesde;
	}

	public void setAceptabilidadDesde(Date aceptabilidadDesde) {
		this.aceptabilidadDesde = aceptabilidadDesde;
	}

	public Date getAceptabilidadHasta() {
		return aceptabilidadHasta;
	}

	public void setAceptabilidadHasta(Date aceptabilidadHasta) {
		this.aceptabilidadHasta = aceptabilidadHasta;
	}

	public BigInteger getCantonId() {
		return CantonId;
	}

	public void setCantonId(BigInteger cantonId) {
		CantonId = cantonId;
	}

	public BigInteger getCicloId() {
		return CicloId;
	}

	public void setCicloId(BigInteger cicloId) {
		CicloId = cicloId;
	}

	public float getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(float costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public float getCostoMantenimiento() {
		return costoMantenimiento;
	}

	public void setCostoMantenimiento(float precioPorUnidad) {
		this.costoMantenimiento = costoMantenimiento;
	}

	public BigInteger getProvinciaId() {
		return ProvinciaId;
	}

	public void setProvinciaId(BigInteger provinciaId) {
		ProvinciaId = provinciaId;
	}

	public float getTasa() {
		return tasa;
	}

	public void setTasa(float tasa) {
		this.tasa = tasa;
	}

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

}
