package br.edu.ifto.gestorfrotaapi.vehicle.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.vehicle.model.Vehicle;
import br.edu.ifto.gestorfrotaapi.vehicle.model.enums.VehicleStatus;

public class VehicleSpecification {

    public static Specification<Vehicle> hasId(Long id) {

        return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);

    }

    public static Specification<Vehicle> hasDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.isBlank()) {
                return null;
            }

            String pattern = "%" + description.toLowerCase() + "%";

            var makePredicate = cb.like(cb.lower(root.get("make")), pattern);
            var modelPredicate = cb.like(cb.lower(root.get("model")), pattern);
            var platePredicate = cb.like(cb.lower(root.get("licensePlate").get("value")), pattern);

            return cb.or(makePredicate, modelPredicate, platePredicate);
        };
    }

    public static Specification<Vehicle> hasStatus(VehicleStatus status) {

        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);

    }

}
