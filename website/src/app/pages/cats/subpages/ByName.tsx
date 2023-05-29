import styled from "styled-components"

import Button from "src/app/components/Button"
import Hr from "src/app/components/Hr"
import Input from "src/app/components/Input"
import CatsList from "src/app/pages/cats/CatsList"
import { useGetByNameQuery } from "src/app/store/api/cats"
import { text16Medium } from "src/app/utils/fonts"
import useSearch from "src/app/utils/hooks/useSearch"

interface IError {
  data: {
    error: string
  }
}

export default function ByName() {
  const [name, searchName, handleChange, handleSearchClick] =
    useSearch<string>("")

  const {
    data,
    isLoading: isDataLoading,
    isFetching,
    error,
  } = useGetByNameQuery(searchName)

  const isLoading = isDataLoading || isFetching

  return (
    <Wrapper>
      <label>
        Имя
        <Input value={name} onChange={handleChange} placeholder="Имя" />
        <Button disabled={!name} onClick={handleSearchClick}>
          Найти
        </Button>
      </label>
      <Hr />
      {error ? (
        <Error>Ошибка: {(error as IError).data.error}</Error>
      ) : (
        <CatsList isLoading={isLoading} cats={data} />
      )}
    </Wrapper>
  )
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;

  > label {
    display: flex;
    gap: 16px;
    align-items: center;

    margin-bottom: 16px;

    ${text16Medium};
    color: #222222;

    > input {
      width: 320px;
    }
  }

  > hr {
    margin-bottom: 16px;
  }
`

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
  text-align: center;
`
