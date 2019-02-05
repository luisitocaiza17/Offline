package micotizador.offline.filestructure;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Veronica Zhagui on 06/07/2015.
 */
public class CotizacionAgricola {


    //private BigInteger Entidad;
    private Boolean DisponeRiego;
    private Boolean AgricultorTecnificado;
    private Boolean DisponeAsistencia;
    private Date FechaCredito;
    private Date FechaSiembra;
    private Date FechaCreacionCotizacion;
    private String DireccionSiembra;
    private String NumeroIdentificacion;
    private String Nombres;
    private String Apellidos;
    private String Telefono;
    private String Celular;
    private String Email;
    private String Observaciones;
    //private String Variedad;
    private String UsuarioNombre;
    private String UsuarioApellido;
    //enviar el punto de venta
    private String PuntoVentaId;

    private Double HectareasLote;
    private Double HectareasAsegurables;
    private Double MontoCreditoSinSeguro;
    private Double AnalisisMontoAsegurado;
    private Double AnalisisMontoRecom;
    private Double PrimaNeta;
    private Double SuperBancos;
    private Double DerechoEmision;
    //private Double ImpuestoAprox;
    private Double SeguroCampesino;
    private Double Iva;
    //private Double RecargoSegCamp;
    private Double TotalPrima;
    private Double MontoCredito;
    private Double TotalCredito;

    private Double Latitud;
    private Double Longitud;
    private Double Tasa;


    private String ObjetoCotizacionId;
    private BigInteger Agencia;
    private BigInteger ProvinciaId;
    private BigInteger CantonId;
    private BigInteger ParroquiaId;
    private BigInteger TipoCultivoId;
    private BigInteger TipoIdentificacion;
    private Integer TipoSeguro;
    private Integer EdadCultivo;
    private Integer Estado;
    private BigInteger Variedad;
    private BigInteger ReglaId;

    private Boolean TieneObservacion;

    public BigInteger getReglaId() {
        return ReglaId;
    }

    public void setReglaId(BigInteger reglaId) {
        ReglaId = reglaId;
    }

    public BigInteger getAgencia() {
        return Agencia;
    }

    public void setAgencia(BigInteger agencia) {
        Agencia = agencia;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        Estado = estado;
    }


    public Date getFechaCreacionCotizacion() {
        return FechaCreacionCotizacion;
    }

    public void setFechaCreacionCotizacion(Date fechaCreacionCotizacion) {
        FechaCreacionCotizacion = fechaCreacionCotizacion;
    }

    public String getUsuarioNombre() {
        return UsuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        UsuarioNombre = usuarioNombre;
    }

    public String getUsuarioApellido() {
        return UsuarioApellido;
    }

    public void setUsuarioApellido(String usuarioApellido) {
        UsuarioApellido = usuarioApellido;
    }
    public Integer getTipoSeguro() {
        return TipoSeguro;
    }

    public void setTipoSeguro(Integer tipoSeguro) {
        TipoSeguro = tipoSeguro;
    }

    public Integer getEdadCultivo() {
        return EdadCultivo;
    }

    public void setEdadCultivo(Integer edadCultivo) {
        EdadCultivo = edadCultivo;
    }

    public BigInteger getTipoIdentificacion() {
        return TipoIdentificacion;
    }

    public void setTipoIdentificacion(BigInteger tipoIdentificacion) {
        TipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return NumeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        NumeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public Double getMontoCreditoSinSeguro() {
        return MontoCreditoSinSeguro;
    }

    public void setMontoCreditoSinSeguro(Double montoCreditoSinSeguro) {
        MontoCreditoSinSeguro = montoCreditoSinSeguro;
    }

    public Double getAnalisisMontoAsegurado() {
        return AnalisisMontoAsegurado;
    }

    public void setAnalisisMontoAsegurado(Double analisisMontoAsegurado) {
        AnalisisMontoAsegurado = analisisMontoAsegurado;
    }

    public Double getAnalisisMontoRecom() {
        return AnalisisMontoRecom;
    }

    public void setAnalisisMontoRecom(Double analisisMontoRecom) {
        AnalisisMontoRecom = analisisMontoRecom;
    }

    public Double getPrimaNeta() {
        return PrimaNeta;
    }

    public void setPrimaNeta(Double primaNeta) {
        PrimaNeta = primaNeta;
    }

    public Double getSuperBancos() {
        return SuperBancos;
    }

    public void setSuperBancos(Double superBancos) {
        SuperBancos = superBancos;
    }

    public Double getDerechoEmision() {
        return DerechoEmision;
    }

    public void setDerechoEmision(Double derechoEmision) {
        DerechoEmision = derechoEmision;
    }

    public Double getSeguroCampesino() {
        return SeguroCampesino;
    }

    public void setSeguroCampesino(Double seguroCampesino) {
        SeguroCampesino = seguroCampesino;
    }

    public Double getIva() {
        return Iva;
    }

    public void setIva(Double iva) {
        Iva = iva;
    }

    public Double getTotalPrima() {
        return TotalPrima;
    }

    public void setTotalPrima(Double totalPrima) {
        TotalPrima = totalPrima;
    }

    public Double getTotalCredito() {
        return TotalCredito;
    }

    public void setTotalCredito(Double totalCredito) {
        TotalCredito = totalCredito;
    }
    public String getObjetoCotizacionId() {
        return ObjetoCotizacionId;
    }

    public void setObjetoCotizacionId(String objetoCotizacionId) {
        ObjetoCotizacionId = objetoCotizacionId;
    }

    public BigInteger getProvinciaId() {
        return ProvinciaId;
    }

    public void setProvinciaId(BigInteger provinciaId) {
        ProvinciaId = provinciaId;
    }

    public BigInteger getCantonId() {
        return CantonId;
    }

    public void setCantonId(BigInteger cantonId) {
        CantonId = cantonId;
    }

    public BigInteger getTipoCultivoId() {
        return TipoCultivoId;
    }

    public void setTipoCultivoId(BigInteger tipoCultivoId) {
        TipoCultivoId = tipoCultivoId;
    }


    public Boolean getDisponeRiego() {
        return DisponeRiego;
    }

    public void setDisponeRiego(Boolean disponeRiego) {
        DisponeRiego = disponeRiego;
    }

    public Boolean getAgricultorTecnificado() {
        return AgricultorTecnificado;
    }

    public void setAgricultorTecnificado(Boolean agricultorTecnificado) {
        AgricultorTecnificado = agricultorTecnificado;
    }

    public Boolean getDisponeAsistencia() {
        return DisponeAsistencia;
    }

    public void setDisponeAsistencia(Boolean disponeAsistencia) {
        DisponeAsistencia = disponeAsistencia;
    }

    public Date getFechaCredito() {
        return FechaCredito;
    }

    public void setFechaCredito(Date fechaCredito) {
        FechaCredito = fechaCredito;
    }

    public Date getFechaSiembra() {
        return FechaSiembra;
    }

    public void setFechaSiembra(Date fechaSiembra) {
        FechaSiembra = fechaSiembra;
    }

    public String getDireccionSiembra() {
        return DireccionSiembra;
    }

    public void setDireccionSiembra(String direccionSiembra) {
        DireccionSiembra = direccionSiembra;
    }

    public Double getHectareasLote() {
        return HectareasLote;
    }

    public void setHectareasLote(Double hectareasLote) {
        HectareasLote = hectareasLote;
    }

    public Double getHectareasAsegurables() {
        return HectareasAsegurables;
    }

    public void setHectareasAsegurables(Double hectareasAsegurables) {
        HectareasAsegurables = hectareasAsegurables;
    }

    public Double getMontoCredito() {
        return MontoCredito;
    }

    public void setMontoCredito(Double montoCredito) {
        MontoCredito = montoCredito;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public Double getTasa() {
        return Tasa;
    }

    public void setTasa(Double tasa) {
        Tasa = tasa;
    }

    public BigInteger getParroquiaId() {
        return ParroquiaId;
    }

    public void setParroquiaId(BigInteger parroquiaId) {
        ParroquiaId = parroquiaId;
    }
    public BigInteger getVariedad() {
        return Variedad;
    }

    public void setVariedad(BigInteger variedad) {
        Variedad = variedad;
    }

    public String getPuntoVentaId() {
        return PuntoVentaId;
    }

    public void setPuntoVentaId(String puntoVentaId) {
        PuntoVentaId = puntoVentaId;
    }

    public Boolean getTieneObservacion() {
        return TieneObservacion;
    }

    public void setTieneObservacion(Boolean tieneObservacion) {
        TieneObservacion = tieneObservacion;
    }
}
