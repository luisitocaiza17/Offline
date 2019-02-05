package micotizador.offline.filestructure;

import java.util.List;

/**
 * Created by Veronica Zhagui on 24/07/2015.
 */
public class TransporteCotizaciones {

    private List<ListadoProcesado>listadoCotizaciones;
    private List<CotizacionAgricola> CotizacionAgricola;

    public List<CotizacionAgricola> getCotizacionAgricola() {
        return CotizacionAgricola;
    }

    public void setCotizacionAgricola(List<CotizacionAgricola> cotizacionAgricola) {
        CotizacionAgricola = cotizacionAgricola;
    }


    public List<ListadoProcesado> getlistadoCotizaciones() {
        return listadoCotizaciones;
    }

    public void setlistadoCotizaciones(List<ListadoProcesado> cotizacionAgricolaProcesada) {
        listadoCotizaciones = cotizacionAgricolaProcesada;
    }





}
