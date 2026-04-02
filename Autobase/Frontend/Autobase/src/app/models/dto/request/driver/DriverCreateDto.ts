export interface DriverCreateDto {
  userId: number;
  name: string;
  phone: string;
  experienceYears: number;
  notes?: string;
}