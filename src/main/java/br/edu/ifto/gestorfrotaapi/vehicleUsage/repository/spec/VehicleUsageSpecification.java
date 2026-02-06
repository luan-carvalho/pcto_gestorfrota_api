package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.spec;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleUsage;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleUsageStatus;

public class VehicleUsageSpecification {

    public static Specification<VehicleUsage> hasDriverId(Long driverId) {
        return (root, query, cb) -> driverId == null ? null : cb.equal(root.get("driver").get("id"), driverId);
    }

    public static Specification<VehicleUsage> hasRequesterName(String requesterName) {
        return (root, query, cb) -> {

            if (requesterName == null || requesterName.isBlank()) {

                return null;

            }

            var searchName = cb.lower(root.get("requester").get("name"));
            return cb.like(searchName, "%" + requesterName.toLowerCase() + "%");

        };
    }

    public static Specification<VehicleUsage> hasDriverName(String driverName) {
        return (root, query, cb) -> {

            if (driverName == null || driverName.isBlank()) {

                return null;

            }

            var searchName = cb.lower(root.get("requester").get("name"));
            return cb.like(searchName, "%" + driverName.toLowerCase() + "%");

        };
    }

    public static Specification<VehicleUsage> hasRequestId(Long requestId) {
        return (root, query, cb) -> requestId == null ? null
                : cb.equal(root.get("request").get("id"), requestId);
    }

    public static Specification<VehicleUsage> hasVehicleDescription(String description) {

        return (root, query, cb) -> {

            if (description == null || description.isBlank()) {

                return null;

            }

            var pattern = "%" + description.toLowerCase() + "%";
            var licensePlate = cb.lower(root.get("request").get("vehicle").get("licensePlate"));
            var make = cb.lower(root.get("request").get("vehicle").get("make"));
            var model = cb.lower(root.get("request").get("vehicle").get("model"));

            var concat = cb.concat(
                    cb.concat(make, " "),
                    cb.concat(model, ""));

            var searchDescription = cb.concat(concat, licensePlate);

            return cb.like(searchDescription, pattern);

        };

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

    public static Specification<VehicleUsage> usageBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return (root, query, cb) -> {
            if (start == null && end == null)
                return null;
            if (start != null && end != null)
                return cb.between(root.get("request").get("startDateTime"), start, end);
            if (start != null)
                return cb.greaterThanOrEqualTo(root.get("request").get("startDateTime"), start);
            return cb.lessThanOrEqualTo(root.get("request").get("endDateTime"), end);
        };
    }

    public static Specification<VehicleUsage> hasStatus(VehicleUsageStatus status) {
        return (root, query, cb) -> status == null ? null
                : cb.equal(
                        root.get("status"),
                        status);
    }

}
