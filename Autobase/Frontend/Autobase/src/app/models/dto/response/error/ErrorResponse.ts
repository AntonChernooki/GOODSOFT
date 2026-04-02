export interface ErrorResponse {
  status: number;
  error: string;
  message?: string;                          
  validationErrors?: Record<string, string>; 
  timestamp: string;                          
}