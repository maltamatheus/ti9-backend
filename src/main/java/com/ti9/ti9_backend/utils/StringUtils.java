package com.ti9.ti9_backend.utils;

public class StringUtils {
    public static String filtraDigitosNumericos(String valor){
        return valor.replaceAll("\\D", "");
    }
}
