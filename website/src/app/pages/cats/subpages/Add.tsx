import { useCallback, useState } from "react"

import styled from "styled-components"

import Button from "src/app/components/Button"
import FormWrapper from "src/app/components/FormWrapper"
import Input from "src/app/components/Input"
import { useAddMutation } from "src/app/store/api/cats"
import { isColorT } from "src/app/store/api/cats/types"
import { text16Medium } from "src/app/utils/fonts"
import useInput from "src/app/utils/hooks/useInput"

interface IError {
  data: {
    error: string
  }
}

export default function Add() {
  const [name, setName, handleNameChange] = useInput<string>("")
  const [color, setColor, handleColorChange] = useInput<string>(
    "",
    (value: string) => value.toUpperCase()
  )
  const [breed, setBreed, handleBreedChange] = useInput<string>("")
  const [birthDate, setBirthDate, handleBirthDateChange] = useInput<string>("")

  const [error, setError] = useState("")

  const [add] = useAddMutation()

  const handleSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault()

      if (isColorT(color)) {
        const result = await add({
          name,
          color,
          breed: breed || null,
          birthDate,
        })

        if ("error" in result) {
          setError((result.error as IError).data.error)
          return
        }

        setName("")
        setColor("")
        setBreed("")
        setBirthDate("")
      }
    },
    [
      color,
      add,
      name,
      breed,
      birthDate,
      setName,
      setColor,
      setBreed,
      setBirthDate,
    ]
  )

  const isActive = !!name && !!birthDate && isColorT(color)

  return (
    <FormWrapper>
      <form onSubmit={handleSubmit}>
        <label>
          Имя котика
          <Input
            value={name}
            onChange={handleNameChange}
            placeholder="Имя котика"
          />
        </label>
        <label>
          Цвет
          <Input
            value={color}
            onChange={handleColorChange}
            placeholder="Цвет"
          />
        </label>
        <label>
          Порода
          <Input
            value={breed}
            onChange={handleBreedChange}
            placeholder="Порода"
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
        <Button type="submit" disabled={!isActive}>
          Создать
        </Button>
      </form>
      {error && <Error>Ошибка: {error}</Error>}
    </FormWrapper>
  )
}

const Error = styled.span`
  color: #e50b0b;
  ${text16Medium};
`
