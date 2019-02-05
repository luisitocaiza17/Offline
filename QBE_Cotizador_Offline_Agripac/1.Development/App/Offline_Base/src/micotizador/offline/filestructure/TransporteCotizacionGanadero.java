package micotizador.offline.filestructure;

import java.util.List;

/**
 * Created by Veronica Zhagui on 14/08/2015.
 */
public class TransporteCotizacionGanadero {
    private List<ObjetoGanadero> CotizacionGanadero;
    private List<ObjetoGanaderoDetalle> CotizacionGanaderoDetalle;

    public List<ObjetoGanadero> getCotizacionGanadero() {
        return CotizacionGanadero;
    }

    public void setCotizacionGanadero(List<ObjetoGanadero> cotizacionGanadero) {
        CotizacionGanadero = cotizacionGanadero;
    }

    public List<ObjetoGanaderoDetalle> getCotizacionGanaderoDetalle() {
        return CotizacionGanaderoDetalle;
    }

    public void setCotizacionGanaderoDetalle(List<ObjetoGanaderoDetalle> cotizacionGanaderoDetalle) {
        CotizacionGanaderoDetalle = cotizacionGanaderoDetalle;
    }
}
