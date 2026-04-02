export interface UserUpdateDto {
  login?: string;           
  password?: string;       
  enabled?: boolean;
  roles?: string[];        
}