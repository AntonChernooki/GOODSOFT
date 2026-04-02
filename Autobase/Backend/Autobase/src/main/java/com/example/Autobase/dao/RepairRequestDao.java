package com.example.Autobase.dao;

import com.example.Autobase.model.entities.RepairRequest;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RepairRequestDao {
    Optional<RepairRequest> getRepairRequestById(Long id);

    List<RepairRequest> getAllRepairRequest();

    List<RepairRequest> getRepairRequestByDriverId(Long driverId);

    List<RepairRequest> getRepairRequestByCarId(Long carId);

    List<RepairRequest> getRepairRequestByStatus(String status);

    void addRepairRequest(RepairRequest request);

    void updateRepairRequestStatus(@Param("id") Long id, @Param("status") String status);

    void completeRepairRequest(@Param("id") Long id, @Param("completedAt") LocalDateTime completedAt);

    void updateRepairRequest(RepairRequest request);

    void deleteRepairRequest(Long id);
}