export type RoleT = "ADMIN" | "USER"

export interface IUserSlice {
  username: string | null
  roles: RoleT[] | null
  token: string | null
}

export interface IUserInfo {
  username: string
  roles: RoleT[]
}
