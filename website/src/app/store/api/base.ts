import { createApi } from "@reduxjs/toolkit/dist/query/react"

import { catsBaseQuery } from "src/app/store/api/helpers"

const baseApi = createApi({
  reducerPath: "api",
  baseQuery: catsBaseQuery,
  tagTypes: ["Owner", "Cat"],
  endpoints: () => ({}),
})

export default baseApi
