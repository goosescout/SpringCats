import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import Cookies from "js-cookie"

import parseToken from "src/app/store/slices/user/helpers"

import { loadToken } from "./token"
import { IUserInfo, IUserSlice } from "./types"

const token = Cookies.get("token")

const initialState: IUserSlice = {
  username: token ? parseToken(token).username : null,
  roles: token ? parseToken(token).roles : null,
  token: token ?? null,
}

const userSlice = createSlice({
  name: "user",
  initialState: initialState,
  reducers: {
    setUser(state, { payload }: PayloadAction<IUserInfo>) {
      state.username = payload.username
      state.roles = payload.roles
    },
    setToken(state, { payload }: PayloadAction<string>) {
      state.token = payload
    },
  },
  extraReducers: builder => {
    builder.addCase(loadToken.pending, (state, { meta }) => {
      state.token = meta.arg
    })

    builder.addCase(loadToken.fulfilled, (state, { payload }) => {
      state.username = payload.username
      state.roles = payload.roles
    })
  },
})

export const { setUser, setToken } = userSlice.actions

export default userSlice.reducer
