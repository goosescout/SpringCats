export type ColorT =
  | "BLACK"
  | "WHITE"
  | "BROWN"
  | "GREY"
  | "ORANGE"
  | "MULTICOLORED"

export function isColorT(value: string): value is ColorT {
  return ["BLACK", "WHITE", "BROWN", "GREY", "ORANGE", "MULTICOLORED"].includes(
    value
  )
}

export interface ICat {
  id: number
  name: string
  birthDate: string
  breed: string | null
  color: ColorT
  catOwnerId: number
  friendIds: number[]
}

export interface ICatCreate {
  name: string
  birthDate: string
  breed: string | null
  color: ColorT
  catOwnerId: number
}

export interface ICatAdd {
  name: string
  birthDate: string
  breed: string | null
  color: ColorT
}

export interface ICatUpdate {
  id: number
  name: string
  birthDate: string
  breed: string | null
  color: ColorT
  catOwnerId: number
}

export interface ICatEdit {
  id: number
  name: string
  birthDate: string
  breed: string | null
  color: ColorT
}

export interface IFriendshipDescriptor {
  catId: number
  friendId: number
}
