package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public class VehicleUsageSpecification {

    public static Specification<VehicleUsage> hasDriverId(Long driverId) {
        return (root, query, cb) -> driverId == null ? null : cb.equal(root.get("driver").get("id"), driverId);
    }

    public static Specification<VehicleUsage> hasVehicleRequestId(Long requestId) {
        return (root, query, cb) -> requestId == null ? null
                : cb.equal(root.get("vehicleRequest").get("id"), requestId);
    }

    public static Specification<VehicleUsage> hasVehicleId(Long vehicleId) {
        return (root, query, cb) -> vehicleId == null ? null
                : cb.equal(
                        root.get("vehicleRequest")
                                .get("vehicle")
                                .get("id"),
                        vehicleId);
    }

    public static Specification<VehicleUsage> checkInBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start != null && end != null)
                return cb.between(root.get("checkInAt"), start, end);
            if (start != null)
                return cb.greaterThanOrEqualTo(root.get("checkInAt"), start);
            return cb.lessThanOrEqualTo(root.get("checkInAt"), end);
        };
    }

    public static Specification<VehicleUsage> checkOutBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start != null && end != null)
                return cb.between(root.get("checkOutAt"), start, end);
            if (start != null)
                return cb.greaterThanOrEqualTo(root.get("checkOutAt"), start);
            return cb.lessThanOrEqualTo(root.get("checkOutAt"), end);
        };
    }

    public static Specification<VehicleUsage> hasStatus(VehicleUsageStatus status) {
        return (root, query, cb) -> status == null ? null
                : cb.equal(
                        root.get("vehicleRequest").get("status"),
                        status);
    }

}
