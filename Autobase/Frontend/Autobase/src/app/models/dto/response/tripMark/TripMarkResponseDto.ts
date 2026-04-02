export interface TripMarkResponseDto {
  id: number;
  tripId: number;
  fuelConsumed: number;
  conditionNotes?: string;
  markDate: string;
}
