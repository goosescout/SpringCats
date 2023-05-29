import { ChangeEventHandler, useCallback, useState } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import FormWrapper from "src/app/components/FormWrapper"
import Hr from "src/app/components/Hr"
import Input from "src/app/components/Input"
import { useLoginMutation, useRegisterMutation } from "src/app/store/api/auth"
import { header48, text16Medium } from "src/app/utils/fonts"
import { text14Medium } from "src/app/utils/fonts"

interface IError {
  data: {
    error: string
  }
}

export default function Register() {
  const [isLogin, setIsLogin] = useState(true)

  const [name, setName] = useState("")
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [birthDate, setBirthDate] = useState("")

  const [error, setError] = useState("")

  const [login] = useLoginMutation()
  const [register] = useRegisterMutation()

  const handleNameChange = useCallback<ChangeEventHandler<HTMLInputElement>>(
    event => {
      setName(event.target.value)
    },
    []
  )

  const handleUsernameChange = useCallback<
    ChangeEventHandler<HTMLInputElement>
  >(event => {
    setUsername(event.target.value)
  }, [])

  const handlePasswordChange = useCallback<
    ChangeEventHandler<HTMLInputElement>
  >(event => {
    setPassword(event.target.value)
  }, [])

  const handleBirthDateChange = useCallback<
    ChangeEventHandler<HTMLInputElement>
  >(event => {
    setBirthDate(event.target.value)
  }, [])

  const handleLoginSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      const result = await login({
        username,
        password,
      })

      if ("error" in result) {
        setError(result.error as string)
        return
      }

      setUsername("")
      setPassword("")
    },
    [login, username, password]
  )

  const handleRegisterSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      const result = await register({
        name,
        username,
        password,
        birthDate,
      })

      if ("error" in result) {
        setError((result.error as IError).data.error)
        return
      }

      setName("")
      setBirthDate("")
      setError("")
      setIsLogin(true)
    },
    [register, name, username, password, birthDate]
  )

  const isActive =
    (isLogin && !!username && !!password) ||
    (!!username && !!password && !!name && !!birthDate)

  return (
    <StyledFormWrapper>
      <h1>{isLogin ? "Войдите" : "Зарегистрируйтесь"}</h1>
      <form onSubmit={isLogin ? handleLoginSubmit : handleRegisterSubmit}>
        {!isLogin && (
          <label>
            Настоящее имя
            <Input
              value={name}
              onChange={handleNameChange}
              placeholder="Настоящее имя"
            />
          </label>
        )}
        <label>
          Имя пользователя
          <Input
            value={username}
            onChange={handleUsernameChange}
            placeholder="Имя пользователя"
          />
        </label>
        <label>
          Пароль
          <Input
            type="password"
            value={password}
            onChange={handlePasswordChange}
            placeholder="Пароль"
          />
        </label>
        {!isLogin && (
          <label>
            Дата рождения
            <Input
              type="date"
              value={birthDate}
              onChange={handleBirthDateChange}
              placeholder="Дата рождения"
            />
          </label>
        )}
        <Button type="submit" disabled={!isActive}>
          {isLogin ? "Войти" : "Зарегистрироваться"}
        </Button>
      </form>
      {error && <Error>Ошибка: {error}</Error>}
      <Hr />
      <Switch>
        {isLogin ? "Нет аккаунта?" : "Уже есть аккаунт?"}{" "}
        <button onClick={() => setIsLogin(login => !login)}>
          {isLogin ? "Зарегистрируйтесь" : "Войдите"}
        </button>
      </Switch>
    </StyledFormWrapper>
  )
}

const StyledFormWrapper = styled(FormWrapper)`
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
    margin: 0;
    text-align: center;

    color: #222222;
    ${header48};
  }

  > form {
    width: 100%;
  }
`

const Switch = styled.div`
  display: flex;
  justify-content: center;
  gap: 8px;

  color: #222222;
  ${text14Medium};

  > button {
    background: none;
    border: none;
    color: #6db33f;
    ${text14Medium};
    padding: 0;
    cursor: pointer;

    &:hover {
      text-decoration: underline;
    }
  }
`

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
`
