package com.ti9.ti9_backend.utils.annotations.impl;

import com.ti9.ti9_backend.utils.annotations.TelefoneValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class TelefoneValidator implements ConstraintValidator<TelefoneValido, String> {
    @Override
    public boolean isValid(String numero, ConstraintValidatorContext constraintValidatorContext) {
        return isCelularValido(numero);
    }
    private static boolean isCelularValido(String celular) {
        if(!celular.replaceAll("\\D","").matches("\\d{11}")){
            return false;
        }
        // Expressão regular para celulares brasileiros (9 dígitos + DDD)
        // Aceita: (11) 91234-5678, 11912345678, 11 912345678
        String regex = "^\\(?[1-9]{2}\\)?\\s?9[0-9]{4}-?[0-9]{4}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(celular);

        return matcher.matches();
    }

    public static boolean isTelefoneValido(String telefone) {
        // Regex para telefone fixo: 2 dígitos DDD + 8 dígitos (começando com 2-5)
        // Ex: (11) 3333-4444 ou 11 33334444
        final String FIXO_REGEX = "^[1-9]{2}[2-5]{1}[0-9]{3}[0-9]{4}$";
        final Pattern PATTERN = Pattern.compile(FIXO_REGEX);

        if (!telefone.replaceAll("\\D","").matches("d{10}")) {
            return false;
        }
        // Remove parênteses, traços e espaços em branco
        String telefoneLimpo = telefone.replaceAll("\\D", "");

        Matcher matcher = PATTERN.matcher(telefoneLimpo);
        return matcher.matches();
    }

}
