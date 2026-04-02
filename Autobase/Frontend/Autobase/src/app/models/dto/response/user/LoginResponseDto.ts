import { UserResponseDto } from "./UserResponseDto";

export interface LoginResponseDto {
  token: string;
  user: UserResponseDto;
}