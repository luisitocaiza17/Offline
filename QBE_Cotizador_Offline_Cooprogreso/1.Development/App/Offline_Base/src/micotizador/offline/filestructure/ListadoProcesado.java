package micotizador.offline.filestructure;

/**
 * Created by Veronica Zhagui on 04/08/2015.
 */
public class ListadoProcesado {
    private String objetoCotizacionID;
    private String cotizacionID;
    private String facturaID;
    private String comentario;
    private int Estado;
    private String Beneficiario;

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public String getBeneficiario() {
        return Beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        Beneficiario = beneficiario;
    }

    public String getObjetoCotizacionID() {
        return objetoCotizacionID;
    }

    public void setObjetoCotizacionID(String objetoCotizacionID) {
        this.objetoCotizacionID = objetoCotizacionID;
    }

    public String getCotizacionID() {
        return cotizacionID;
    }

    public void setCotizacionID(String cotizacionID) {
        this.cotizacionID = cotizacionID;
    }

    public String getFacturaID() {
        return facturaID;
    }

    public void setFacturaID(String facturaID) {
        this.facturaID = facturaID;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}
