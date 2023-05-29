import baseApi from "src/app/store/api/base"
import { withQueryParams } from "src/app/store/api/helpers"

import {
  ColorT,
  ICat,
  ICatAdd,
  ICatCreate,
  ICatEdit,
  ICatUpdate,
  IFriendshipDescriptor,
} from "./types"

const catsApi = baseApi.injectEndpoints({
  endpoints: builder => ({
    getById: builder.query<ICat, number>({
      query: id =>
        withQueryParams({
          url: "/cats/getById",
          params: { id },
        }),
      providesTags: ["Cat"],
    }),

    getAll: builder.query<ICat[], void>({
      query: () => "/cats/getAll",
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    getByName: builder.query<ICat[], string>({
      query: name =>
        withQueryParams({
          url: "/cats/getByName",
          params: { name },
        }),
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    getByBirthDate: builder.query<ICat[], string>({
      query: date =>
        withQueryParams({
          url: "/cats/getByBirthDate",
          params: { date },
        }),
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    getByBreed: builder.query<ICat[], string | null>({
      query: breed =>
        withQueryParams({
          url: "/cats/getByBreed",
          params: { breed },
        }),
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    getByColor: builder.query<ICat[], ColorT>({
      query: color =>
        withQueryParams({
          url: "/cats/getByColor",
          params: { color },
        }),
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    getByOwnerId: builder.query<ICat[], number>({
      query: catOwnerId =>
        withQueryParams({
          url: "/cats/getByCatOwnerId",
          params: { catOwnerId },
        }),
      providesTags: [{ type: "Cat", id: "LIST" }],
    }),

    create: builder.mutation<ICat, ICatCreate>({
      query: body => ({
        url: "/cats/create",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),

    add: builder.mutation<ICat, ICatAdd>({
      query: body => ({
        url: "/cats/add",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),

    update: builder.mutation<ICat, ICatUpdate>({
      query: body => ({
        url: "/cats/update",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),

    edit: builder.mutation<ICat, ICatEdit>({
      query: body => ({
        url: "/cats/edit",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),

    delete: builder.mutation<void, number>({
      query: id => ({
        url: "/cats/delete?id=" + id,
        method: "POST",
      }),
      invalidatesTags: ["Cat"],
    }),

    addFriend: builder.mutation<void, IFriendshipDescriptor>({
      query: body => ({
        url: "/cats/addFriend",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),

    removeFriend: builder.mutation<void, IFriendshipDescriptor>({
      query: body => ({
        url: "/cats/removeFriend",
        method: "POST",
        body,
      }),
      invalidatesTags: ["Cat"],
    }),
  }),
})

export default catsApi
export const {
  useGetByIdQuery,
  useGetAllQuery,
  useGetByNameQuery,
  useGetByBirthDateQuery,
  useGetByBreedQuery,
  useGetByColorQuery,
  useGetByOwnerIdQuery,
  useCreateMutation,
  useAddMutation,
  useUpdateMutation,
  useEditMutation,
  useDeleteMutation,
  useAddFriendMutation,
  useRemoveFriendMutation,
} = catsApi
