import { ChangeEventHandler } from "react"

import styled from "styled-components"

import { text16Medium } from "src/app/utils/fonts"

interface IInputProps {
  type?: string
  value: string | number
  onChange: ChangeEventHandler<HTMLInputElement>
  placeholder?: string
}

export default function Input({
  type = "text",
  value,
  onChange,
  placeholder = "",
}: IInputProps) {
  return (
    <StyledInput
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
    />
  )
}

const StyledInput = styled.input`
  outline: none;
  border: 1px solid #dcdcdc;
  border-radius: 8px;
  background-color: #f8f8f8;

  box-sizing: border-box;
  height: 40px;
  padding: 12px;

  color: #222222;
  ${text16Medium};

  &::placeholder {
    opacity: 0.5;
  }

  &[type="date"]::-webkit-inner-spin-button,
  &[type="date"]::-webkit-calendar-picker-indicator {
    display: none;
    -webkit-appearance: none;
  }
`
