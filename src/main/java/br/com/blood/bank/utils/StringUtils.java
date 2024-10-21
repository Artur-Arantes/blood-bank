package br.com.blood.bank.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringUtils {
    public static final String USER_NOT_FOUND = "User Not Found";



    public static LocalDate convertStringToLocalDate(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateFromString = null;

        try {
            localDateFromString = LocalDate.parse(string.replace("\"", ""), formatter);
        } catch (DateTimeParseException e) {
            //TODO LOG
        }

        return localDateFromString;
    }
}
