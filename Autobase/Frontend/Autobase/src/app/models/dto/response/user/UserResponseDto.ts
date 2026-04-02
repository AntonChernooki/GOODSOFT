export interface UserResponseDto {
  id: number;
  login: string;
  enabled: boolean;
  createAt: string;             
  updateAt: string;             
  roles: string[];             
}