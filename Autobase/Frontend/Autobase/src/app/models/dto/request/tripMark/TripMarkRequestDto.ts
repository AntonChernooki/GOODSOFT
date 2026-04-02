export interface TripMarkRequestDto {
  tripId: number;
  fuelConsumed: number;      
  conditionNotes?: string;
}