import styled from "styled-components"

import Greeting from "src/app/pages/profile/Greeting"
import Register from "src/app/pages/profile/Register"
import { useAppSelector } from "src/app/store"

export default function Profile() {
  const username = useAppSelector(({ user }) => user.username)

  return <Wrapper>{username ? <Greeting /> : <Register />}</Wrapper>
}

const Wrapper = styled.div`
  padding: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
`
