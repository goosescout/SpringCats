import { useCallback, useState } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import FormWrapper from "src/app/components/FormWrapper"
import Input from "src/app/components/Input"
import { useDeleteMutation } from "src/app/store/api/cats"
import { text16Medium } from "src/app/utils/fonts"
import useInput from "src/app/utils/hooks/useInput"

interface IError {
  data: {
    error: string
  }
}

export default function Delete() {
  const [id, setId, handleIdChange] = useInput<number>(1)

  const [error, setError] = useState("")

  const [deleteCat] = useDeleteMutation()

  const handleSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      const result = await deleteCat(id)

      if ("error" in result) {
        setError((result.error as IError).data.error)
        return
      }

      setId(1)
      setError("")
    },
    [deleteCat, id, setId]
  )

  const isActive = id > 0

  return (
    <StyledFormWrapper>
      <form onSubmit={handleSubmit}>
        <label>
          ID
          <Input
            type="number"
            value={id.toString()}
            onChange={handleIdChange}
          />
        </label>
        <StyledButton type="submit" disabled={!isActive}>
          Удалить
        </StyledButton>
      </form>
      {error && <Error>Ошибка: {error}</Error>}
    </StyledFormWrapper>
  )
}

const StyledFormWrapper = styled(FormWrapper)`
  > form {
    width: 420px;
  }
`

const StyledButton = styled(Button)`
  background-color: #e50b0b;
`

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
`
