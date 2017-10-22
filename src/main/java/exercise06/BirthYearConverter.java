package exercise06;

import java.util.*;
import javax.persistence.*;

@Converter
public class BirthYearConverter implements AttributeConverter<Date, String> {

    @Override
    public String convertToDatabaseColumn(Date attribute) {
        if (attribute == null) return null;
        final Calendar cal = Calendar.getInstance();
        cal.setTime(attribute);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    @Override
    public Date convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        final Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dbData), 1, 1);
        return cal.getTime();
        
    }

}
