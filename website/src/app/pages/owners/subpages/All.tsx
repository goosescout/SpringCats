import OwnersList from "src/app/pages/owners/OwnersList"
import { useGetAllOwnersQuery } from "src/app/store/api/owners"

export default function All() {
  const { data, isLoading: isDataLoading, isFetching } = useGetAllOwnersQuery()

  const isLoading = isDataLoading || isFetching

  return <OwnersList isLoading={isLoading} owners={data} />
}
