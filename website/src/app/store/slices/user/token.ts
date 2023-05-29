import { createAsyncThunk } from "@reduxjs/toolkit"
import Cookies from "js-cookie"

import parseToken from "./helpers"
import { IUserInfo } from "./types"

const TOKEN_COOKIE = "token"

const loadToken = createAsyncThunk<IUserInfo, string>(
  "auth/loadToken",
  token => {
    const { exp, username, roles } = parseToken(token)

    Cookies.set(TOKEN_COOKIE, token, {
      expires: new Date(exp * 1000),
      path: "/",
    })

    return {
      username,
      roles,
    }
  }
)

const removeToken = createAsyncThunk<void, void>("auth/removeToken", () => {
  Cookies.remove(TOKEN_COOKIE)
  window.location.href = "/"
})

export { loadToken, removeToken }
