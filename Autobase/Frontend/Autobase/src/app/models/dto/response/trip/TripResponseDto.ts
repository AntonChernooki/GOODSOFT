import { TripStatus } from "../../../enums/TripStatus";

export interface TripResponseDto {
route: any;
dateTime: string|number|Date;
car: any;
  id: number;
  origin: string;
  destination: string;
  status: TripStatus;
  driverId: number;
  carId: number;
  startedAt?: string;           
  completedAt?: string;         
  createdAt: string;
  updatedAt: string;
}