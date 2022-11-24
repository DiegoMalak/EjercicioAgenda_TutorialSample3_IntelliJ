package com.address.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Funciones de ayuda para el manejo de fechas.
public class DateUtil {

    //El patrón de fecha que se utiliza para la conversión. Cambie como desee.
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    //El formato de fecha utilizado para la conversión.
    // La clase SimpleDateFormat no es hilo seguro,
    // por lo que es mejor crearlo como un campo estático.
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    //Devuelve la fecha dada como una cadena bien formateada.
    //Esto se usa principalmente por las propiedades de la clase.

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Convierte una cadena con el formato definido por {@link DateUtil#DATE_PATTERN}
     * a un objeto {@link LocalDate}.
     *
     * Devuelve null si la Cadena no pudo ser convertida.
     *
     * @param dateString la fecha como String
     * @retorna el objeto fecha o null si no se pudo convertir
     */

    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    //Comprueba si la cadena es una fecha valida.
    public static boolean validDate(String dateString) {
        //Intenta analizar la cadena.
        return DateUtil.parse(dateString) != null;
    }

}
