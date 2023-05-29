import baseApi from "src/app/store/api/base"
import { BASE_URL } from "src/app/store/api/helpers"
import { loadToken } from "src/app/store/slices/user/token"

import { ILoginParams, IRegisterParams } from "./types"

const authApi = baseApi.injectEndpoints({
  endpoints: builder => ({
    register: builder.mutation<null, IRegisterParams>({
      query: ({ name, username, password, birthDate }) => ({
        url: "/auth/register",
        method: "POST",
        body: { name, username, password, birthDate: birthDate },
      }),
    }),
    login: builder.mutation<null, ILoginParams>({
      queryFn: async ({ username, password }, { dispatch }) => {
        const body = await fetch(`${BASE_URL}/auth/login`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ username, password }),
        }).then(res => res.json())

        if (body.token) dispatch(loadToken(body.token))

        if (body.error) return { error: body.error }

        return { data: null }
      },
    }),
  }),
})

export default authApi
export const { useLoginMutation, useRegisterMutation } = authApi
