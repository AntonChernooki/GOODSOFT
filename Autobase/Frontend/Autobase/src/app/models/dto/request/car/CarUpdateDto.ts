import { CarStatus } from "../../../enums/CarStatus";

export interface CarUpdateDto {
  model?: string;
  mark?: string;
  color?: string;
  yearOfRelease?: number;
  stateNumber?: string;
  mileage?: number;
  notes?: string;
  status?: CarStatus;     
}