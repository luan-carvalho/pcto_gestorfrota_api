package br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects;

import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class LicensePlate {

    private static final Pattern OLD_FORMAT_PATTERN = Pattern.compile("[A-Z]{3}[0-9]{4}");
    private static final Pattern MERCOSUL_PATTERN = Pattern.compile("[A-Z]{3}[0-9][A-Z][0-9]{2}");

    @Column(nullable = false, length = 7, unique = true)
    private String value;

    protected LicensePlate() {

    }

    public LicensePlate(String value) {

        Objects.requireNonNull(value, "License plate cannot be null");

        String cleaned = value.toUpperCase().replace("-", "").trim();

        if (!isValid(cleaned)) {
            throw new IllegalArgumentException("Invalid license plate format: " + value);
        }

        this.value = cleaned;
    }

    private boolean isValid(String plate) {

        return OLD_FORMAT_PATTERN.matcher(plate).matches() ||
                MERCOSUL_PATTERN.matcher(plate).matches();
    }

    @Override
    public String toString() {
        if (OLD_FORMAT_PATTERN.matcher(value).matches()) {
            return value.substring(0, 3) + "-" + value.substring(3);
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LicensePlate that = (LicensePlate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
