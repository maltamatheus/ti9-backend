package com.ti9.ti9_backend.utils;

import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacoesUtils {
    public static String validaCnpj(String cnpj){
        if(isCNPJValido(cnpj)){
            return StringUtils.filtraDigitosNumericos(cnpj);
        } else {
            throw new ValorNaoPermitidoException("CNPJ é inválido");
        }
    }

    public static String validaTelefone(String numero){
        if(isCelularValido(numero) || isTelefoneValido(numero)){
            return StringUtils.filtraDigitosNumericos(numero);
        } else {
            throw new ValorNaoPermitidoException("Telefone é inválido");
        }
    }

    private static boolean isCNPJValido(String cnpj) {
        if(!StringUtils.filtraDigitosNumericos(cnpj).matches("\\d{14}")){
            return false;
        }
        // Remove caracteres especiais (pontos, barras, hifens)
        String cnpjLimpo = StringUtils.filtraDigitosNumericos(cnpj);

        // Verifica se tem 14 dígitos ou se é uma sequência inválida
        if (cnpjLimpo.length() != 14 ||
                cnpjLimpo.equals("00000000000000") || cnpjLimpo.equals("11111111111111") ||
                cnpjLimpo.equals("22222222222222") || cnpjLimpo.equals("33333333333333") ||
                cnpjLimpo.equals("44444444444444") || cnpjLimpo.equals("55555555555555") ||
                cnpjLimpo.equals("66666666666666") || cnpjLimpo.equals("77777777777777") ||
                cnpjLimpo.equals("88888888888888") || cnpjLimpo.equals("99999999999999")) {
            return false;
        }

        // Cálculo do 1º dígito verificador
        int soma = 0;
        int digito;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += Integer.parseInt(cnpjLimpo.substring(i, i + 1)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        digito = 11 - (soma % 11);
        if (digito > 9) digito = 0;
        if (digito != Integer.parseInt(cnpjLimpo.substring(12, 13))) {
            return false;
        }

        // Cálculo do 2º dígito verificador
        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += Integer.parseInt(cnpjLimpo.substring(i, i + 1)) * peso;
            peso = (peso == 9) ? 2 : peso + 1;
        }
        digito = 11 - (soma % 11);
        if (digito > 9) digito = 0;
        if (digito != Integer.parseInt(cnpjLimpo.substring(13, 14))){
            return false;
        }

        return true;
    }

    public static boolean isCelularValido(String celular) {
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
        final String FIXO_REGEX = "^\\(?[1-9]{2}\\)?\\s?[2-5]-?[0-9]{4}$";
        final Pattern PATTERN = Pattern.compile(FIXO_REGEX);

        if (!telefone.replaceAll("\\D","").matches("d{10}")) {
            return false;
        }
        // Remove parênteses, traços e espaços em branco
        String telefoneLimpo = telefone.replaceAll("\\D", "");

        Matcher matcher = PATTERN.matcher(telefoneLimpo);
        return matcher.matches();
    }

    public static String emailValidador(String email) {

        // Regex para validar formato de e-mail
        final String EMAIL_PATTERN = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return email;
        } else {
            return null;
        }
    }
}
