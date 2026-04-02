export interface CarCreateDto {
  model: string;
  mark: string;
  color: string;
  yearOfRelease: number;
  stateNumber: string;
  mileage: number;
  notes?: string;
}