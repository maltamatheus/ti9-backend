package com.ti9.ti9_backend.utils;

import java.util.UUID;

public class Validacoes {
    public static boolean idCorreto(UUID id1, UUID id2){
        return id1.compareTo(id2) == 0;
    }
}
