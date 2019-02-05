package micotizador.offline.filestructure;

import java.math.BigInteger;

/**
 * Created by Verónica Zhagui on 09/07/2015.
 */
public class Agencia {

    private BigInteger AgenciaId;

    private BigInteger PuntoVentaId;

    private String NombreAgencia;

    private String Codigo;

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public BigInteger getAgenciaId() {
        return AgenciaId;
    }

    public void setAgenciaId(BigInteger AgenciaId) {
        this.AgenciaId = AgenciaId;
    }

    public BigInteger getPuntoVentaId() {
        return PuntoVentaId;
    }

    public void setPuntoVentaId(BigInteger PuntoVentaId) {
        this.PuntoVentaId = PuntoVentaId;
    }

    public String getNombreAgencia() {
        return NombreAgencia;
    }

    public void setNombreAgencia(String NombreAgencia) {
        this.NombreAgencia = NombreAgencia;
    }

    public  String toString() {
        return NombreAgencia;
    }
}