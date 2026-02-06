package br.edu.ifto.gestorfrotaapi.vehicle.exception;

import br.edu.ifto.gestorfrotaapi.vehicle.model.valueObjects.LicensePlate;

public class LiscensePlateAlreadyRegistered extends RuntimeException {

    public LiscensePlateAlreadyRegistered(LicensePlate plate) {

        super("There is already a vehicle with the license plate " + plate);

    }

}
