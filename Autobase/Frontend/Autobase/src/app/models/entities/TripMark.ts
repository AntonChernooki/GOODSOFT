export interface TripMark {
  id: number;
  tripId: number;
  fuelConsumed?: number;
  conditionNotes?: string;
  markDate: string;
}
