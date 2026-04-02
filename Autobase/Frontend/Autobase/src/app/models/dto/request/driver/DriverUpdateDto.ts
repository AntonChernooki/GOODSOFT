import { DriverStatus } from '../../../enums/DriverStatus';
export interface DriverUpdateDto {
  name?: string;
  phone?: string;
  experienceYears?: number;
  notes?: string;
  status?: DriverStatus;
}
