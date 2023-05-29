import baseApi from "src/app/store/api/base"
import { withQueryParams } from "src/app/store/api/helpers"

import { IOwner, IOwnerCreate, IOwnerUpdate } from "./types"

const ownersApi = baseApi.injectEndpoints({
  endpoints: builder => ({
    getOwnerById: builder.query<IOwner, number>({
      query: id =>
        withQueryParams({
          url: "/owners/getById",
          params: { id },
        }),
      providesTags: ["Owner"],
    }),

    getAllOwners: builder.query<IOwner[], void>({
      query: () => "/owners/getAll",
      providesTags: [{ type: "Owner", id: "LIST" }],
    }),

    getOwnersByName: builder.query<IOwner[], string>({
      query: name =>
        withQueryParams({
          url: "/owners/getByName",
          params: { name },
        }),
      providesTags: [{ type: "Owner", id: "LIST" }],
    }),

    getOwnerByUsername: builder.query<IOwner, string>({
      query: username =>
        withQueryParams({
          url: "/owners/getByUsername",
          params: { username },
        }),
      providesTags: [{ type: "Owner", id: "LIST" }],
    }),

    getOwnersByBirthDate: builder.query<IOwner[], string>({
      query: birthDate =>
        withQueryParams({
          url: "/owners/getByBirthDate",
          params: { date: birthDate },
        }),
      providesTags: [{ type: "Owner", id: "LIST" }],
    }),

    createOwner: builder.mutation<IOwner, IOwnerCreate>({
      query: body => ({
        url: "/owners/create",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Owner"],
    }),

    updateOwner: builder.mutation<IOwner, IOwnerUpdate>({
      query: body => ({
        url: "/owners/update",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Owner"],
    }),

    deleteOwner: builder.mutation<void, number>({
      query: id => ({
        url: "/owners/delete?id=" + id,
        method: "POST",
      }),
      invalidatesTags: ["Owner"],
    }),
  }),
})

export default ownersApi
export const {
  useGetOwnerByIdQuery,
  useGetAllOwnersQuery,
  useGetOwnersByNameQuery,
  useGetOwnerByUsernameQuery,
  useGetOwnersByBirthDateQuery,
  useCreateOwnerMutation,
  useUpdateOwnerMutation,
  useDeleteOwnerMutation,
} = ownersApi
