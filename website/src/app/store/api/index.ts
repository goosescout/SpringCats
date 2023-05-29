import catsApi from "src/app/store/api/cats"

import baseApi from "./base"

const reducers = {
  [catsApi.reducerPath]: catsApi.reducer,
}

export const middlewares = [baseApi.middleware] as const

export default reducers
