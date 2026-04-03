package com.example.Autobase.dto.request.repairRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RepairRequestStatusUpdateDto {
    @NotBlank(message = "Status is required")
    private String status;
}
