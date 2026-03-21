package com.ti9.ti9_backend.utils;

public class StringUtils {
    public static String limpaCaracteresEspeciais(String cnpj){
        return cnpj.replaceAll("[^a-zA-Z0-9]", "");
    }
}
