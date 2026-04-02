import { CarStatus } from '../../../enums/CarStatus';

export interface CarResponseDto {
  id: number;
  model: string;
  mark: string;
  color: string;
  yearOfRelease: number;
  stateNumber: string;
  status: CarStatus;
  mileage: number;
  notes?: string;
  createdAt: string;
  updatedAt: string;
}
