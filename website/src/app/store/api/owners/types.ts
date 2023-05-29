import { RoleT } from "src/app/store/slices/user/types"

export interface IOwner {
  id: number
  name: string
  username: string
  birthDate: string
  roles: RoleT[]
  catIds: number[]
}

export interface IOwnerCreate {
  name: string
  username: string
  password: string
  birthDate: string
  roles: RoleT[]
}

export interface IOwnerUpdate {
  id: number
  name: string
  username: string
  password: string
  birthDate: string
  roles: RoleT[]
}
