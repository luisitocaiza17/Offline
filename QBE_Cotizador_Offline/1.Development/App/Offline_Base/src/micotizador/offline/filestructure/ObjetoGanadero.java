package micotizador.offline.filestructure;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Veronica Zhagui on 14/08/2015.
 */
public class ObjetoGanadero {
    private String ObjetoGanaderoID;
    private String NumeroIdentificacion;
    private String Nombres;
    private String Apellidos;
    private String Telefono;
    private String Celular;
    private String Email;
    private String Ubicacion;
    private String Region;
    private String Tipo_Produccion;
    private String Pasto_Observaciones;
    private String Mortalidad_Vacas_Causa;
    private String Mortalidad_Vaconasv_Causa;
    private String Mortalidad_Vaconasf_Causa;
    private String Mortalidad_Vaconasm_Causa;
    private String Mortalidad_Toros_Causa;
    private String Mortalidad_Toretes_Causa;
    private String Mortalidad_Terneros_Causa;
    private String Mortalidad_Terneras_Causa;
    private String Vacunaciones_Otras;
    private String Parasitos_Internos_Trata;
    private String Parasitos_Internos_Frecu;
    private String Parasitos_Externos_Trata;
    private String Parasitos_Externos_Frecu;
    private String Asistencia_Veterinaria_Frec;
    private String Asistencia_Veterinaria_Prof;
    private String Asistencia_Veterinaria_Tele;
    private String Acceso_Al_Agua;
    private String Recinto;
    private String Pasto_Tipoid;
    private String Enfermedad_Cual;

    private BigInteger ProvinciaId;
    private BigInteger CantonId;
    private BigInteger ParroquiaId;
    private BigInteger Puntoventaid;
    private BigInteger Usuarioid;

    private Integer Finca_Altitud;
    private Integer Animales_Vacunos;
    private Integer Mortalidad_Vacas;
    private Integer Mortalidad_Vaconasv;
    private Integer Mortalidad_Vaconasf;
    private Integer Mortalidad_Vaconasm;
    private Integer Mortalidad_Toros;
    private Integer Mortalidad_Toretes;
    private Integer Mortalidad_Terneros;
    private Integer Mortalidad_Terneras;
    private Integer Enfermedad_Mastisis;
    private Integer Enfermedad_Panadizo;
    private Integer Enfermedad_Fiebreleche;
    private Integer Enfermedad_Lesionubres;
    private Integer Enfermedad_Neumonias;
    private Integer Enfermedad_Otras;
    private Integer Origen;
    private Integer Experiencia_Ganadero_Anios;

    private Double Finca_Topografia1;
    private Double Finca_Topografia2;
    private Double Finca_Topografia3;
    private Double Pasto_Hectareas;
    private Double Pasto_Volumneanio;

    private Character Alimentacion_Pastoreo;
    private Character Alimentacion_Corte;
    private Character Alimentacion_Sogueo;
    private Character Alimentacion_Otros;
    private Character Vacunaciones_Aftosa;
    private Character Vacunaciones_Brucelosis;
    private Character Vacunaciones_Triple;
    private Character Vacunaciones_Leptospirosis;
    private Character Vacunaciones_Ibrbvd;
    private Character Parasitos_Internos;
    private Character Parasitos_Externos;
    private Character Asistencia_Veterinaria;
    private Character Esprincipal_Ingreso;

    private Date Fecha_Registro;

    public String getObjetoGanaderoID() {
        return ObjetoGanaderoID;
    }

    public void setObjetoGanaderoID(String objetoGanaderoID) {
        ObjetoGanaderoID = objetoGanaderoID;
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

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getTipo_Produccion() {
        return Tipo_Produccion;
    }

    public void setTipo_Produccion(String tipo_Produccion) {
        Tipo_Produccion = tipo_Produccion;
    }

    public String getPasto_Observaciones() {
        return Pasto_Observaciones;
    }

    public void setPasto_Observaciones(String pasto_Observaciones) {
        Pasto_Observaciones = pasto_Observaciones;
    }

    public String getMortalidad_Vacas_Causa() {
        return Mortalidad_Vacas_Causa;
    }

    public void setMortalidad_Vacas_Causa(String mortalidad_Vacas_Causa) {
        Mortalidad_Vacas_Causa = mortalidad_Vacas_Causa;
    }

    public String getMortalidad_Vaconasv_Causa() {
        return Mortalidad_Vaconasv_Causa;
    }

    public void setMortalidad_Vaconasv_Causa(String mortalidad_Vaconasv_Causa) {
        Mortalidad_Vaconasv_Causa = mortalidad_Vaconasv_Causa;
    }

    public String getMortalidad_Vaconasf_Causa() {
        return Mortalidad_Vaconasf_Causa;
    }

    public void setMortalidad_Vaconasf_Causa(String mortalidad_Vaconasf_Causa) {
        Mortalidad_Vaconasf_Causa = mortalidad_Vaconasf_Causa;
    }

    public String getMortalidad_Vaconasm_Causa() {
        return Mortalidad_Vaconasm_Causa;
    }

    public void setMortalidad_Vaconasm_Causa(String mortalidad_Vaconasm_Causa) {
        Mortalidad_Vaconasm_Causa = mortalidad_Vaconasm_Causa;
    }

    public String getMortalidad_Toros_Causa() {
        return Mortalidad_Toros_Causa;
    }

    public void setMortalidad_Toros_Causa(String mortalidad_Toros_Causa) {
        Mortalidad_Toros_Causa = mortalidad_Toros_Causa;
    }

    public String getMortalidad_Toretes_Causa() {
        return Mortalidad_Toretes_Causa;
    }

    public void setMortalidad_Toretes_Causa(String mortalidad_Toretes_Causa) {
        Mortalidad_Toretes_Causa = mortalidad_Toretes_Causa;
    }

    public String getMortalidad_Terneros_Causa() {
        return Mortalidad_Terneros_Causa;
    }

    public void setMortalidad_Terneros_Causa(String mortalidad_Terneros_Causa) {
        Mortalidad_Terneros_Causa = mortalidad_Terneros_Causa;
    }

    public String getMortalidad_Terneras_Causa() {
        return Mortalidad_Terneras_Causa;
    }

    public void setMortalidad_Terneras_Causa(String mortalidad_Terneras_Causa) {
        Mortalidad_Terneras_Causa = mortalidad_Terneras_Causa;
    }

    public String getVacunaciones_Otras() {
        return Vacunaciones_Otras;
    }

    public void setVacunaciones_Otras(String vacunaciones_Otras) {
        Vacunaciones_Otras = vacunaciones_Otras;
    }

    public String getParasitos_Internos_Trata() {
        return Parasitos_Internos_Trata;
    }

    public void setParasitos_Internos_Trata(String parasitos_Internos_Trata) {
        Parasitos_Internos_Trata = parasitos_Internos_Trata;
    }

    public String getParasitos_Internos_Frecu() {
        return Parasitos_Internos_Frecu;
    }

    public void setParasitos_Internos_Frecu(String parasitos_Internos_Frecu) {
        Parasitos_Internos_Frecu = parasitos_Internos_Frecu;
    }

    public String getParasitos_Externos_Trata() {
        return Parasitos_Externos_Trata;
    }

    public void setParasitos_Externos_Trata(String parasitos_Externos_Trata) {
        Parasitos_Externos_Trata = parasitos_Externos_Trata;
    }

    public String getParasitos_Externos_Frecu() {
        return Parasitos_Externos_Frecu;
    }

    public void setParasitos_Externos_Frecu(String parasitos_Externos_Frecu) {
        Parasitos_Externos_Frecu = parasitos_Externos_Frecu;
    }

    public String getAsistencia_Veterinaria_Frec() {
        return Asistencia_Veterinaria_Frec;
    }

    public void setAsistencia_Veterinaria_Frec(String asistencia_Veterinaria_Frec) {
        Asistencia_Veterinaria_Frec = asistencia_Veterinaria_Frec;
    }

    public String getAsistencia_Veterinaria_Prof() {
        return Asistencia_Veterinaria_Prof;
    }

    public void setAsistencia_Veterinaria_Prof(String asistencia_Veterinaria_Prof) {
        Asistencia_Veterinaria_Prof = asistencia_Veterinaria_Prof;
    }

    public String getAsistencia_Veterinaria_Tele() {
        return Asistencia_Veterinaria_Tele;
    }

    public void setAsistencia_Veterinaria_Tele(String asistencia_Veterinaria_Tele) {
        Asistencia_Veterinaria_Tele = asistencia_Veterinaria_Tele;
    }

    public String getAcceso_Al_Agua() {
        return Acceso_Al_Agua;
    }

    public void setAcceso_Al_Agua(String acceso_Al_Agua) {
        Acceso_Al_Agua = acceso_Al_Agua;
    }

    public String getRecinto() {
        return Recinto;
    }

    public void setRecinto(String recinto) {
        Recinto = recinto;
    }

    public String getPasto_Tipoid() {
        return Pasto_Tipoid;
    }

    public void setPasto_Tipoid(String pasto_Tipoid) {
        Pasto_Tipoid = pasto_Tipoid;
    }

    public String getEnfermedad_Cual() {
        return Enfermedad_Cual;
    }

    public void setEnfermedad_Cual(String enfermedad_Cual) {
        Enfermedad_Cual = enfermedad_Cual;
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

    public BigInteger getParroquiaId() {
        return ParroquiaId;
    }

    public void setParroquiaId(BigInteger parroquiaId) {
        ParroquiaId = parroquiaId;
    }

    public BigInteger getPuntoventaid() {
        return Puntoventaid;
    }

    public void setPuntoventaid(BigInteger puntoventaid) {
        Puntoventaid = puntoventaid;
    }

    public BigInteger getUsuarioid() {
        return Usuarioid;
    }

    public void setUsuarioid(BigInteger usuarioid) {
        Usuarioid = usuarioid;
    }

    public Integer getFinca_Altitud() {
        return Finca_Altitud;
    }

    public void setFinca_Altitud(Integer finca_Altitud) {
        Finca_Altitud = finca_Altitud;
    }

    public Integer getAnimales_Vacunos() {
        return Animales_Vacunos;
    }

    public void setAnimales_Vacunos(Integer animales_Vacunos) {
        Animales_Vacunos = animales_Vacunos;
    }

    public Integer getMortalidad_Vacas() {
        return Mortalidad_Vacas;
    }

    public void setMortalidad_Vacas(Integer mortalidad_Vacas) {
        Mortalidad_Vacas = mortalidad_Vacas;
    }

    public Integer getMortalidad_Vaconasv() {
        return Mortalidad_Vaconasv;
    }

    public void setMortalidad_Vaconasv(Integer mortalidad_Vaconasv) {
        Mortalidad_Vaconasv = mortalidad_Vaconasv;
    }

    public Integer getMortalidad_Vaconasf() {
        return Mortalidad_Vaconasf;
    }

    public void setMortalidad_Vaconasf(Integer mortalidad_Vaconasf) {
        Mortalidad_Vaconasf = mortalidad_Vaconasf;
    }

    public Integer getMortalidad_Vaconasm() {
        return Mortalidad_Vaconasm;
    }

    public void setMortalidad_Vaconasm(Integer mortalidad_Vaconasm) {
        Mortalidad_Vaconasm = mortalidad_Vaconasm;
    }

    public Integer getMortalidad_Toros() {
        return Mortalidad_Toros;
    }

    public void setMortalidad_Toros(Integer mortalidad_Toros) {
        Mortalidad_Toros = mortalidad_Toros;
    }

    public Integer getMortalidad_Toretes() {
        return Mortalidad_Toretes;
    }

    public void setMortalidad_Toretes(Integer mortalidad_Toretes) {
        Mortalidad_Toretes = mortalidad_Toretes;
    }

    public Integer getMortalidad_Terneros() {
        return Mortalidad_Terneros;
    }

    public void setMortalidad_Terneros(Integer mortalidad_Terneros) {
        Mortalidad_Terneros = mortalidad_Terneros;
    }

    public Integer getMortalidad_Terneras() {
        return Mortalidad_Terneras;
    }

    public void setMortalidad_Terneras(Integer mortalidad_Terneras) {
        Mortalidad_Terneras = mortalidad_Terneras;
    }

    public Integer getEnfermedad_Mastisis() {
        return Enfermedad_Mastisis;
    }

    public void setEnfermedad_Mastisis(Integer enfermedad_Mastisis) {
        Enfermedad_Mastisis = enfermedad_Mastisis;
    }

    public Integer getEnfermedad_Panadizo() {
        return Enfermedad_Panadizo;
    }

    public void setEnfermedad_Panadizo(Integer enfermedad_Panadizo) {
        Enfermedad_Panadizo = enfermedad_Panadizo;
    }

    public Integer getEnfermedad_Fiebreleche() {
        return Enfermedad_Fiebreleche;
    }

    public void setEnfermedad_Fiebreleche(Integer enfermedad_Fiebreleche) {
        Enfermedad_Fiebreleche = enfermedad_Fiebreleche;
    }

    public Integer getEnfermedad_Lesionubres() {
        return Enfermedad_Lesionubres;
    }

    public void setEnfermedad_Lesionubres(Integer enfermedad_Lesionubres) {
        Enfermedad_Lesionubres = enfermedad_Lesionubres;
    }

    public Integer getEnfermedad_Neumonias() {
        return Enfermedad_Neumonias;
    }

    public void setEnfermedad_Neumonias(Integer enfermedad_Neumonias) {
        Enfermedad_Neumonias = enfermedad_Neumonias;
    }

    public Integer getEnfermedad_Otras() {
        return Enfermedad_Otras;
    }

    public void setEnfermedad_Otras(Integer enfermedad_Otras) {
        Enfermedad_Otras = enfermedad_Otras;
    }

    public Integer getOrigen() {
        return Origen;
    }

    public void setOrigen(Integer origen) {
        Origen = origen;
    }

    public Integer getExperiencia_Ganadero_Anios() {
        return Experiencia_Ganadero_Anios;
    }

    public void setExperiencia_Ganadero_Anios(Integer experiencia_Ganadero_Anios) {
        Experiencia_Ganadero_Anios = experiencia_Ganadero_Anios;
    }

    public Double getFinca_Topografia1() {
        return Finca_Topografia1;
    }

    public void setFinca_Topografia1(Double finca_Topografia1) {
        Finca_Topografia1 = finca_Topografia1;
    }

    public Double getFinca_Topografia2() {
        return Finca_Topografia2;
    }

    public void setFinca_Topografia2(Double finca_Topografia2) {
        Finca_Topografia2 = finca_Topografia2;
    }

    public Double getFinca_Topografia3() {
        return Finca_Topografia3;
    }

    public void setFinca_Topografia3(Double finca_Topografia3) {
        Finca_Topografia3 = finca_Topografia3;
    }

    public Double getPasto_Hectareas() {
        return Pasto_Hectareas;
    }

    public void setPasto_Hectareas(Double pasto_Hectareas) {
        Pasto_Hectareas = pasto_Hectareas;
    }

    public Double getPasto_Volumneanio() {
        return Pasto_Volumneanio;
    }

    public void setPasto_Volumneanio(Double pasto_Volumneanio) {
        Pasto_Volumneanio = pasto_Volumneanio;
    }

    public Character getAlimentacion_Pastoreo() {
        return Alimentacion_Pastoreo;
    }

    public void setAlimentacion_Pastoreo(Character alimentacion_Pastoreo) {
        Alimentacion_Pastoreo = alimentacion_Pastoreo;
    }

    public Character getAlimentacion_Corte() {
        return Alimentacion_Corte;
    }

    public void setAlimentacion_Corte(Character alimentacion_Corte) {
        Alimentacion_Corte = alimentacion_Corte;
    }

    public Character getAlimentacion_Sogueo() {
        return Alimentacion_Sogueo;
    }

    public void setAlimentacion_Sogueo(Character alimentacion_Sogueo) {
        Alimentacion_Sogueo = alimentacion_Sogueo;
    }

    public Character getAlimentacion_Otros() {
        return Alimentacion_Otros;
    }

    public void setAlimentacion_Otros(Character alimentacion_Otros) {
        Alimentacion_Otros = alimentacion_Otros;
    }

    public Character getVacunaciones_Aftosa() {
        return Vacunaciones_Aftosa;
    }

    public void setVacunaciones_Aftosa(Character vacunaciones_Aftosa) {
        Vacunaciones_Aftosa = vacunaciones_Aftosa;
    }

    public Character getVacunaciones_Brucelosis() {
        return Vacunaciones_Brucelosis;
    }

    public void setVacunaciones_Brucelosis(Character vacunaciones_Brucelosis) {
        Vacunaciones_Brucelosis = vacunaciones_Brucelosis;
    }

    public Character getVacunaciones_Triple() {
        return Vacunaciones_Triple;
    }

    public void setVacunaciones_Triple(Character vacunaciones_Triple) {
        Vacunaciones_Triple = vacunaciones_Triple;
    }

    public Character getVacunaciones_Leptospirosis() {
        return Vacunaciones_Leptospirosis;
    }

    public void setVacunaciones_Leptospirosis(Character vacunaciones_Leptospirosis) {
        Vacunaciones_Leptospirosis = vacunaciones_Leptospirosis;
    }

    public Character getVacunaciones_Ibrbvd() {
        return Vacunaciones_Ibrbvd;
    }

    public void setVacunaciones_Ibrbvd(Character vacunaciones_Ibrbvd) {
        Vacunaciones_Ibrbvd = vacunaciones_Ibrbvd;
    }

    public Character getParasitos_Internos() {
        return Parasitos_Internos;
    }

    public void setParasitos_Internos(Character parasitos_Internos) {
        Parasitos_Internos = parasitos_Internos;
    }

    public Character getParasitos_Externos() {
        return Parasitos_Externos;
    }

    public void setParasitos_Externos(Character parasitos_Externos) {
        Parasitos_Externos = parasitos_Externos;
    }

    public Character getAsistencia_Veterinaria() {
        return Asistencia_Veterinaria;
    }

    public void setAsistencia_Veterinaria(Character asistencia_Veterinaria) {
        Asistencia_Veterinaria = asistencia_Veterinaria;
    }

    public Character getEsprincipal_Ingreso() {
        return Esprincipal_Ingreso;
    }

    public void setEsprincipal_Ingreso(Character esprincipal_Ingreso) {
        Esprincipal_Ingreso = esprincipal_Ingreso;
    }

    public Date getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(Date fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }
}
