import { useCallback, useState } from "react"

import ReactSwitch from "react-switch"
import styled from "styled-components"

import Button from "src/app/components/Button"
import FormWrapper from "src/app/components/FormWrapper"
import Input from "src/app/components/Input"
import { useUpdateOwnerMutation } from "src/app/store/api/owners"
import { text16Medium } from "src/app/utils/fonts"
import useInput from "src/app/utils/hooks/useInput"

interface IError {
  data: {
    error: string
  }
}

export default function Update() {
  const [id, setId, handleIdChange] = useInput<number>(1)
  console.log(id)
  const [name, setName, handleNameChange] = useInput<string>("")
  const [username, setUsername, handleUsernameChange] = useInput<string>("")
  const [password, setPassword, handlePasswordChange] = useInput<string>("")
  const [birthDate, setBirthDate, handleBirthDateChange] = useInput<string>("")
  const [isAdmin, setIsAdmin] = useState(false)

  const [error, setError] = useState("")

  const [update] = useUpdateOwnerMutation()

  const handleIsAdminChange = useCallback((checked: boolean) => {
    setIsAdmin(checked)
  }, [])

  const handleSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      const result = await update({
        id,
        name,
        username,
        password,
        birthDate,
        roles: isAdmin ? ["ADMIN", "USER"] : ["USER"],
      })

      if ("error" in result) {
        setError((result.error as IError).data.error)
        return
      }

      setId(1)
      setName("")
      setUsername("")
      setPassword("")
      setBirthDate("")
      setIsAdmin(false)
      setError("")
    },
    [
      birthDate,
      id,
      isAdmin,
      name,
      password,
      setBirthDate,
      setId,
      setName,
      setPassword,
      setUsername,
      update,
      username,
    ]
  )

  const isActive = id > 0 && !!username && !!password && !!name && !!birthDate

  return (
    <FormWrapper>
      <form onSubmit={handleSubmit}>
        <label>
          ID
          <Input
            type="number"
            value={id.toString()}
            onChange={handleIdChange}
          />
        </label>
        <label>
          Настоящее имя
          <Input
            value={name}
            onChange={handleNameChange}
            placeholder="Настоящее имя"
          />
        </label>
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
        <label>
          Дата рождения
          <Input
            type="date"
            value={birthDate}
            onChange={handleBirthDateChange}
            placeholder="Дата рождения"
          />
        </label>
        <label>
          Администратор
          <Switch
            checked={isAdmin}
            onChange={handleIsAdminChange}
            checkedIcon={false}
            uncheckedIcon={false}
            onColor="#55cd37"
            offColor="#f8f8f8"
            handleDiameter={20}
            width={60}
          />
        </label>
        <Button type="submit" disabled={!isActive}>
          Обновить
        </Button>
      </form>
      {error && <Error>Ошибка: {error}</Error>}
    </FormWrapper>
  )
}

const Switch = styled(ReactSwitch)`
  margin-right: 320px;

  > div {
    border: 1px solid #dcdcdc !important;
    box-sizing: border-box;
    box-shadow: none !important;
  }
`

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
`
