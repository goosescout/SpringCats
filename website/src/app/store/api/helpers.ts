import { fetchBaseQuery } from "@reduxjs/toolkit/dist/query"

import type { RootState } from "src/app/store/store"

export const BASE_URL = "http://localhost:8080/api"

type SchemaT<Keys extends string, Args extends Record<Keys, unknown>> = {
  [k in Keys]?: (arg: Args[k]) => string
}

export function withQueryParams<
  Keys extends string,
  Args extends Partial<Record<Keys, unknown>> = Partial<Record<Keys, unknown>>
>({
  url,
  params,
  schema = {},
}: {
  url: string
  params: Args
  schema?: SchemaT<Keys, Args>
}): string {
  const query = Object.entries(params)
    .filter(([_, value]) => value !== undefined)
    .map(([key, value]) => {
      const fn = schema[key as Keys] ?? String
      return `${key}=${fn(value as Args[Keys])}`
    })
    .join("&")

  return `${url}?${query}`
}

export const catsBaseQuery = fetchBaseQuery({
  baseUrl: BASE_URL,
  mode: "cors",
  prepareHeaders: (headers, api) => {
    const {
      user: { token },
    } = api.getState() as RootState

    const next = new Headers(headers)

    if (token) {
      next.set("Authorization", `Bearer ${token}`)
    }

    return next
  },
})

export const toQueryBoolean = (x: boolean) => (x ? "1" : "0")
export const toQueryArray = (x: number[]) => x.map(String).join(",")
