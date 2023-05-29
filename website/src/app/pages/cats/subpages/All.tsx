import CatsList from "src/app/pages/cats/CatsList"
import { useGetAllQuery } from "src/app/store/api/cats"

export default function All() {
  const { data, isLoading: isDataLoading, isFetching } = useGetAllQuery()

  const isLoading = isDataLoading || isFetching

  return <CatsList isLoading={isLoading} cats={data} />
}
