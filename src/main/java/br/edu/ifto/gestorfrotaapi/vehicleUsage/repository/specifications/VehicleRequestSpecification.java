package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public class VehicleRequestSpecification {

    public static Specification<VehicleRequest> hasRequesterName(String requesterName) {
        return (root, query, cb) -> requesterName == null ? null
                : cb.equal(root.get("requester").get("name"), requesterName);
    }

    public static Specification<VehicleRequest> hasVehicleLicensePlate(String licensePlate) {
        return (root, query, cb) -> licensePlate == null ? null : cb.equal(root.get("vehicle").get("licensePlate"), licensePlate);
    }

    public static Specification<VehicleRequest> hasVehicleMake(String make) {
        return (root, query, cb) -> make == null ? null : cb.equal(root.get("vehicle").get("make"), make);
    }

    public static Specification<VehicleRequest> hasVehicleModel(String model) {
        return (root, query, cb) -> model == null ? null : cb.equal(root.get("vehicle").get("model"), model);
    }

    public static Specification<VehicleRequest> hasStatus(RequestStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<VehicleRequest> hasPriority(RequestPriority priority) {
        return (root, query, cb) -> priority == null ? null : cb.equal(root.get("priority"), priority);
    }

    public static Specification<VehicleRequest> hasPurpose(VehicleRequestPurpose purpose) {
        return (root, query, cb) -> purpose == null ? null : cb.equal(root.get("purpose"), purpose);
    }

    public static Specification<VehicleRequest> hasProcessNumber(String processNumber) {
        return (root, query, cb) -> (processNumber == null || processNumber.isBlank())
                ? null
                : cb.like(
                        cb.lower(root.get("processNumber")),
                        "%" + processNumber.toLowerCase() + "%");
    }

    public static Specification<VehicleRequest> createdBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start != null && end != null)
                return cb.between(root.get("createdAt"), start, end);
            if (start != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
            return cb.lessThanOrEqualTo(root.get("createdAt"), end);
        };
    }

    public static Specification<VehicleRequest> usageBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start != null && end != null)
                return cb.and(
                        cb.lessThanOrEqualTo(root.get("startDateTime"), end),
                        cb.greaterThanOrEqualTo(root.get("endDateTime"), start));
            return null;
        };
    }
}
