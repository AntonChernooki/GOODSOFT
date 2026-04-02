import { DriverStatus } from '../../../enums/DriverStatus';

export interface DriverResponseDto {
  id: number;
  userId: number;
  name: string;
  phone: string;
  status: DriverStatus;
  experienceYears: number;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}
