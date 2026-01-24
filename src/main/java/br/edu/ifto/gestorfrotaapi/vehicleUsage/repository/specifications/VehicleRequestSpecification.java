package br.edu.ifto.gestorfrotaapi.vehicleUsage.repository.specifications;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.VehicleRequest;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestPriority;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.RequestStatus;
import br.edu.ifto.gestorfrotaapi.vehicleUsage.model.enums.VehicleRequestPurpose;

public class VehicleRequestSpecification {

    public static Specification<VehicleRequest> hasRequestId(Long requestId) {

        return (root, query, cb) -> requestId == null ? null : cb.equal(root.get("id"), requestId);

    }

    public static Specification<VehicleRequest> hasRequesterName(String requesterName) {
        return (root, query, cb) -> {

            if (requesterName == null || requesterName.isBlank()) {

                return null;

            }

            var searchName = cb.lower(root.get("requester").get("name"));
            return cb.like(searchName, "%" + requesterName.toLowerCase() + "%");

        };
    }

    public static Specification<VehicleRequest> hasVehicleLicensePlate(String licensePlate) {
        return (root, query, cb) -> {

            if (licensePlate == null || licensePlate.isBlank()) {

                return null;

            }

            return cb.like(cb.lower(root.get("vehicle").get("licensePlate")), "%" + licensePlate.toLowerCase() + "%");

        };
    }

    public static Specification<VehicleRequest> hasVehicleDescription(String description) {

        return (root, query, cb) -> {

            if (description == null || description.isBlank()) {

                return null;

            }

            var pattern = "%" + description.toLowerCase() + "%";
            var licensePlate = cb.lower(root.get("vehicleRequest").get("vehicle").get("licensePlate"));
            var make = cb.lower(root.get("vehicleRequest").get("vehicle").get("make"));
            var model = cb.lower(root.get("vehicleRequest").get("vehicle").get("model"));

            var concat = cb.concat(
                    cb.concat(make, " "),
                    cb.concat(model, ""));
                    
            var searchDescription = cb.concat(concat, licensePlate);

            return cb.like(searchDescription, pattern);

        };

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
