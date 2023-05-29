import styled from "styled-components"

import Button from "src/app/components/Button"
import Hr from "src/app/components/Hr"
import Input from "src/app/components/Input"
import CatsList from "src/app/pages/cats/CatsList"
import { useGetByColorQuery } from "src/app/store/api/cats"
import { ColorT, isColorT } from "src/app/store/api/cats/types"
import { text16Medium } from "src/app/utils/fonts"
import useSearch from "src/app/utils/hooks/useSearch"

interface IError {
  data: {
    error: string
  }
}

export default function ByColor() {
  const [color, searchColor, handleChange, handleSearchClick] =
    useSearch<string>("BLACK")

  const {
    data,
    isLoading: isDataLoading,
    isFetching,
    error,
  } = useGetByColorQuery(searchColor as ColorT)

  const isLoading = isDataLoading || isFetching

  return (
    <Wrapper>
      <label>
        Цвет
        <Input value={color} onChange={handleChange} placeholder="Цвет" />
        <Button disabled={!isColorT(color)} onClick={handleSearchClick}>
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
      width: 160px;
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
