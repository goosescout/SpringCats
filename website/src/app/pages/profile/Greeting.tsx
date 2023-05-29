import { useCallback } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import { useAppDispatch, useAppSelector } from "src/app/store"
import { removeToken } from "src/app/store/slices/user/token"
import { header48, text16Medium } from "src/app/utils/fonts"

export default function Greeting() {
  const { username, roles } = useAppSelector(({ user }) => user)

  const dispatch = useAppDispatch()

  const logout = useCallback(() => dispatch(removeToken()), [dispatch])

  return (
    <Wrapper>
      <h1>Привет, {username}!</h1>
      <p>Ваши роли: {roles?.join(", ")}</p>
      <StyledButton onClick={logout}>Выйти</StyledButton>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);

  box-sizing: border-box;
  width: 600px;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;

  > h1 {
    max-width: 100%;
    margin: 0;
    text-align: center;
    overflow-wrap: break-word;

    color: #222222;
    ${header48};
  }

  > p {
    margin: 0;

    color: #222222;
    ${text16Medium};
  }
`

const StyledButton = styled(Button)`
  background-color: #e50b0b;
`
