package br.edu.ifto.gestorfrotaapi.authentication.model.valueObjects;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Cpf {

    @Column(name = "cpf", length = 11, nullable = false)
    private String value;

    protected Cpf() {

    }

    public Cpf(String value) {

        Objects.requireNonNull(value);

        String cleaned = value.replaceAll("\\D", "");

        if (cleaned.length() != 11 || isAllDigitsEqual(cleaned)) {
            throw new IllegalArgumentException("Invalid CPF format");
        }

        if (!calculateCheckDigits(cleaned)) {
            throw new IllegalArgumentException("Invalid CPF digits");
        }

        this.value = cleaned;
    }

    public String getValue() {
        return value;
    }

    public String getFormatted() {
        return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private static boolean isAllDigitsEqual(String cpf) {
        char first = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != first)
                return false;
        }
        return true;
    }

    private static boolean calculateCheckDigits(String cpf) {
        try {

            int digit1, digit2, remainder;

            int sum = 0;
            int weight = 10;
            for (int i = 0; i < 9; i++) {
                int num = (int) (cpf.charAt(i) - 48);
                sum = sum + (num * weight);
                weight = weight - 1;
            }

            remainder = 11 - (sum % 11);
            if ((remainder == 10) || (remainder == 11))
                digit1 = 0;
            else
                digit1 = remainder;

            sum = 0;
            weight = 11;
            for (int i = 0; i < 10; i++) {
                int num = (int) (cpf.charAt(i) - 48);
                sum = sum + (num * weight);
                weight = weight - 1;
            }

            remainder = 11 - (sum % 11);
            if ((remainder == 10) || (remainder == 11))
                digit2 = 0;
            else
                digit2 = remainder;

            return (digit1 == (cpf.charAt(9) - 48) &&
                    digit2 == (cpf.charAt(10) - 48));

        } catch (Exception e) {
            return false;
        }
    }

}
