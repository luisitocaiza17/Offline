package micotizador.offline.windows;

/**
 * Created by Veronica Zhagui on 23/07/2015.
 */
public class Validate_Identification {
    public enum TiposIdentificacionValidador { Cedula(1), RUC (2), Error (3);
        private final int tipo; //Color de la madera
        TiposIdentificacionValidador (int tipo) {

            this.tipo = tipo;

        } //Cierre del constructor
        public int tipo() { return tipo; }
    }

    public enum TiposOrigenRUCValidador { PersonaNatural (1), Privada (2), Publica (3), Error (4);
        private final int tipo; //Color de la madera
        TiposOrigenRUCValidador (int tipo) {

            this.tipo = tipo;

        } //Cierre del constructor
        public int tipo() { return tipo; }
    }
    public static String Validate_Identification(String numero)
    {
        TiposIdentificacionValidador tipoIdentificacion = TiposIdentificacionValidador.Error;
        TiposOrigenRUCValidador tipoOrigen = TiposOrigenRUCValidador.Error;
        return Validate_Identification(numero, tipoIdentificacion.tipo, tipoOrigen.tipo);
    }
    public static String Validate_Identification(String numero,int tipoIdentificacion, int  tipoOrigen)
    {
        tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
        tipoOrigen = TiposOrigenRUCValidador.Error.tipo;

            /* IdType: 1 para cédula, 2 para RUC */
        int IdType = 0;
        if (numero.length() == 10)
            IdType = 1;
        if (numero.length() == 13)
            IdType = 2;
        if (IdType == 0)
        {
            return "La identificación ingresada es incorrecta";
        }

        tipoIdentificacion = IdType == 1 ? TiposIdentificacionValidador.Cedula.tipo : TiposIdentificacionValidador.RUC.tipo;

        Integer suma = 0;
        Integer residuo = 0;
        Boolean Private = false;
        Boolean Public = false;
        Boolean Natural = false;
        Integer NumProvincias = 24;
        Integer Modulo = 11;

        ///Al recibir el numero de cedula se verifica que sean solo números
        ///ya no necesita verificar el procedimiento
            /* Verifico que el campo no contenga letras */
        /*for(Character item : numero.toCharArray() )
        {
            if (item < '0' || item > '9')
            {
                tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                return "La identificación no puede contener letras, sólo números";
            }
        }*/
        //Validación de los dos primeros dígitos (Código de Provincia)
        if (Integer.parseInt(numero.substring(0, 2)) > NumProvincias)
        {
            tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
            tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
            return "El código de la provincia (dos primeros dígitos) es inválido";
        }

            /* Aqui almacenamos los digitos de la cedula en variables. */
        Integer d1 = Integer.parseInt(numero.substring(0, 1));

        Integer d2 = Integer.parseInt(numero.substring(1, 2));
        Integer d3 = Integer.parseInt(numero.substring(2, 3));
        Integer d4 = Integer.parseInt(numero.substring(3, 4));
        Integer d5 = Integer.parseInt(numero.substring(4, 5));
        Integer d6 = Integer.parseInt(numero.substring(5, 6));
        Integer d7 = Integer.parseInt(numero.substring(6, 7));
        Integer d8 = Integer.parseInt(numero.substring(7, 8));
        Integer d9 = Integer.parseInt(numero.substring(8, 9));
        Integer d10 = Integer.parseInt(numero.substring(9, 10));

        if (d3 == 7 || d3 == 8)
        {
            tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
            tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
            return "El tercer dígito ingresado es inválido";
        }

        int p1 = 0, p2 = 0, p3 = 0, p4 = 0, p5 = 0, p6 = 0, p7 = 0, p8 = 0, p9 = 0;
        if (IdType == 1)
        {
            //Validación sólo para cédulas
                /* El tercer digito es menor que 6 (0,1,2,3,4,5) para personas naturales */
                /* Solo para personas naturales (modulo 10) */
            if (d3 < 6)
            {
                Natural = true;
                p1 = d1 * 2; if (p1 >= 10) p1 -= 9;
                p2 = d2 * 1; if (p2 >= 10) p2 -= 9;
                p3 = d3 * 2; if (p3 >= 10) p3 -= 9;
                p4 = d4 * 1; if (p4 >= 10) p4 -= 9;
                p5 = d5 * 2; if (p5 >= 10) p5 -= 9;
                p6 = d6 * 1; if (p6 >= 10) p6 -= 9;
                p7 = d7 * 2; if (p7 >= 10) p7 -= 9;
                p8 = d8 * 1; if (p8 >= 10) p8 -= 9;
                p9 = d9 * 2; if (p9 >= 10) p9 -= 9;
                Modulo = 10;
            }
            else
            {
                tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                return "El tercer dígito ingresado es inválido";
            }

            suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
            residuo = suma % Modulo;

                /* Si residuo=0, dig.ver.=0, caso contrario 10 - residuo*/
            int digitoVerificador;
            if (residuo == 0) digitoVerificador = 0;
            else digitoVerificador = Modulo - residuo;

                /* ahora comparamos el elemento de la posicion 10 con el dig. verificador */
            if (digitoVerificador != d10)
            {
                tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                return "El número de cédula es incorrecto.";
            }
        }
        else
        {
            //Validación sólo para RUC's
                /* El tercer digito es: */
                /* 9 para sociedades privadas y extranjeros */
                /* 6 para sociedades publicas */
                /* Solo para sociedades publicas (modulo 11) */
                /* Aqui el digito verficador esta en la posicion 9, en las otras 2 en la pos. 10 */
            if (d3 == 6)
            {
                Public = true;
                p1 = d1 * 3;
                p2 = d2 * 2;
                p3 = d3 * 7;
                p4 = d4 * 6;
                p5 = d5 * 5;
                p6 = d6 * 4;
                p7 = d7 * 3;
                p8 = d8 * 2;
                p9 = 0;
            }

                /* Solo para entidades privadas (modulo 11) */
            if (d3 == 9)
            {
                Private = true;
                p1 = d1 * 4;
                p2 = d2 * 3;
                p3 = d3 * 2;
                p4 = d4 * 7;
                p5 = d5 * 6;
                p6 = d6 * 5;
                p7 = d7 * 4;
                p8 = d8 * 3;
                p9 = d9 * 2;
            }

                /* El tercer digito es menor que 6 (0,1,2,3,4,5) para personas naturales */
                /* Solo para personas naturales (modulo 10) */
            if (d3 < 6)
            {
                Natural = true;
                p1 = d1 * 2; if (p1 >= 10) p1 -= 9;
                p2 = d2 * 1; if (p2 >= 10) p2 -= 9;
                p3 = d3 * 2; if (p3 >= 10) p3 -= 9;
                p4 = d4 * 1; if (p4 >= 10) p4 -= 9;
                p5 = d5 * 2; if (p5 >= 10) p5 -= 9;
                p6 = d6 * 1; if (p6 >= 10) p6 -= 9;
                p7 = d7 * 2; if (p7 >= 10) p7 -= 9;
                p8 = d8 * 1; if (p8 >= 10) p8 -= 9;
                p9 = d9 * 2; if (p9 >= 10) p9 -= 9;
                Modulo = 10;
            }

            suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
            residuo = suma % Modulo;

                /* Si residuo=0, dig.ver.=0, caso contrario 10 - residuo*/
            int digitoVerificador;
            if (residuo == 0) digitoVerificador = 0;
            else digitoVerificador = Modulo - residuo;

                /* ahora comparamos el elemento de la posicion 10 con el dig. verificador */
            if (Public)
            {
                tipoOrigen = TiposOrigenRUCValidador.Publica.tipo;
                if (digitoVerificador != d9)
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la empresa del sector público es incorrecto.";
                    return "El RUC ingresado es incorrecto.";
                }

                    /* El ruc de las empresas del sector publico terminan con 0001*/
                if (numero.substring(9, 13) == "0000")
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la empresa del sector público debe terminar con una secuencia de 0001";
                    return "El RUC ingresado es incorrecto.";
                }
            }
            if (Private)
            {
                tipoOrigen = TiposOrigenRUCValidador.Privada.tipo;
                if (digitoVerificador != d10)
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la empresa del sector privado es incorrecto.";
                    return "El RUC ingresado es incorrecto.";
                }
                if (numero.substring(10, 13) == "000")
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la empresa del sector privado debe terminar con una secuencia de 001";
                    return "El RUC ingresado es incorrecto.";
                }
            }
            if (Natural)
            {
                tipoOrigen = TiposOrigenRUCValidador.PersonaNatural.tipo;
                if (digitoVerificador != d10)
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la persona natural es incorrecto.";
                    return "El RUC ingresado es incorrecto.";
                }
                if (numero.substring(10, 13) == "000")
                {
                    tipoIdentificacion = TiposIdentificacionValidador.Error.tipo;
                    tipoOrigen = TiposOrigenRUCValidador.Error.tipo;
                    //return "El RUC de la persona natural debe terminar con una secuencia de 001";
                    return "El RUC ingresado es incorrecto.";
                }
            }
        }

        return "";
    }
}
