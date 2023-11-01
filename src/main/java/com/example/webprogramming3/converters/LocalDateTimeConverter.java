package com.example.webprogramming3.converters;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.DateTimeConverter;
import jakarta.faces.convert.FacesConverter;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@FacesConverter("localDateTimeConverter")
public class LocalDateTimeConverter extends DateTimeConverter {

    public LocalDateTimeConverter(){
        setPattern("dd/MM/yyyy HH:mm:ss");
        setLocale(Locale.US);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if(value!=null && !value.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            return LocalDateTime.parse(value, formatter);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof LocalDateTime){
            LocalDateTime localDateTime = (LocalDateTime) value;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPattern());
            return localDateTime.format(formatter);
        }
        return null;
    }

}
