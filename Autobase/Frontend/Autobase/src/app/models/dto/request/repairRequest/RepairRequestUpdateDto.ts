import { RepairRequestStatus } from "../../../enums/RepairRequestStatus";

export interface RepairRequestUpdateDto {
  description?: string;
  status?: RepairRequestStatus;
}