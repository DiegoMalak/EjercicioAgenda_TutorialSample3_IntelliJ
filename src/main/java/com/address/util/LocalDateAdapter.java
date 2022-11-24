package com.address.util;


import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/*Adapter (for JAXB) to convert between the LocalDate and the ISO 8601
 * String representation of the date such as '2012-12-03'.
 * Adaptador (para JAXB) para convertir entre la fecha local y la representación de la fecha ISO 8601
 * String representativa de la fecha como '2022-11-17'.
 */



public class LocalDateAdapter  extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }

}
