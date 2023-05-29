import { useCallback, useState } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import FormWrapper from "src/app/components/FormWrapper"
import Input from "src/app/components/Input"
import {
  useAddFriendMutation,
  useRemoveFriendMutation,
} from "src/app/store/api/cats"
import { text16Medium } from "src/app/utils/fonts"
import useInput from "src/app/utils/hooks/useInput"

interface IError {
  data: {
    error: string
  }
}

export default function Friendship() {
  const [ownId, _setOwnId, handleOwnIdChange] = useInput<number>(1)
  const [friendId, _setFriendId, handleFriendIdChange] = useInput<number>(2)

  const [error, setError] = useState("")

  const [addFriend] = useAddFriendMutation()
  const [removeFriend] = useRemoveFriendMutation()

  const handleAddFriend = useCallback(async () => {
    const result = await addFriend({ catId: ownId, friendId })

    if ("error" in result) {
      setError((result.error as IError).data.error)
      return
    }

    setError("")
  }, [addFriend, friendId, ownId])

  const handleRemoveFriend = useCallback(async () => {
    const result = await removeFriend({ catId: ownId, friendId })

    if ("error" in result) {
      setError((result.error as IError).data.error)
      return
    }

    setError("")
  }, [friendId, ownId, removeFriend])

  const isActive = ownId > 0 && friendId > 0 && ownId !== friendId

  return (
    <StyledFormWrapper>
      <form onSubmit={event => event.preventDefault()}>
        <label>
          ID котика
          <Input type="number" value={ownId} onChange={handleOwnIdChange} />
        </label>
        <label>
          ID друга
          <Input
            type="number"
            value={friendId}
            onChange={handleFriendIdChange}
          />
        </label>
        <div>
          <Button onClick={handleAddFriend} disabled={!isActive}>
            Подружиться
          </Button>
          <StyledButton onClick={handleRemoveFriend} disabled={!isActive}>
            Разорвать дружбу
          </StyledButton>
        </div>
      </form>
      {error && <Error>Ошибка: {error}</Error>}
    </StyledFormWrapper>
  )
}

const StyledFormWrapper = styled(FormWrapper)`
  > form {
    width: 480px;

    > div {
      display: flex;
      justify-content: space-between;
      width: 380px;
      margin-left: auto;

      > button {
        width: 180px;
      }
    }
  }
`

const StyledButton = styled(Button)`
  background-color: #e50b0b;
`

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
`
