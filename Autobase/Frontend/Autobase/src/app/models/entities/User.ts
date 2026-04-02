import { Role } from './Role';

export interface User {
  id: number;
  login: string;
  password?: string;
  enabled: boolean;
  createdAt: string;
  updatedAt: string;
  roles: Role[];
}
