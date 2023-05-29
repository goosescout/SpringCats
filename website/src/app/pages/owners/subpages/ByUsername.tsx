import { useMemo } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import Hr from "src/app/components/Hr"
import Input from "src/app/components/Input"
import OwnersList from "src/app/pages/owners/OwnersList"
import { useGetOwnerByUsernameQuery } from "src/app/store/api/owners"
import { text16Medium } from "src/app/utils/fonts"
import useSearch from "src/app/utils/hooks/useSearch"

interface IError {
  data: {
    error: string
  }
}

export default function ByUsername() {
  const [username, searchUsername, handleChange, handleSearchClick] =
    useSearch<string>("admin")

  const {
    data,
    isLoading: isDataLoading,
    isFetching,
    error,
  } = useGetOwnerByUsernameQuery(searchUsername)

  const arrayData = useMemo(() => {
    if (!data) return undefined

    return [data]
  }, [data])

  const isLoading = isDataLoading || isFetching

  return (
    <Wrapper>
      <label>
        Имя пользователя
        <Input
          value={username}
          onChange={handleChange}
          placeholder="Имя пользователя"
        />
        <Button disabled={!username} onClick={handleSearchClick}>
          Найти
        </Button>
      </label>
      <Hr />
      {error ? (
        <Error>Ошибка: {(error as IError).data.error}</Error>
      ) : (
        <OwnersList isLoading={isLoading} owners={arrayData} />
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
