import { RepairRequestStatus } from '../enums/RepairRequestStatus';

export interface RepairRequest {
  id: number;
  driverId: number;
  carId: number;
  description: string;
  status: RepairRequestStatus;
  createdAt: string;
  completedAt?: string;
  updatedAt: string;
}
