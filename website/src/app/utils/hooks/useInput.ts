import { ChangeEventHandler, useCallback, useState } from "react"

export default function useInput<T extends string | number>(
  defaultValue: T,
  transform: (value: T) => T = value => value
): [
  T,
  React.Dispatch<React.SetStateAction<T>>,
  ChangeEventHandler<HTMLInputElement>
] {
  const [value, setValue] = useState(defaultValue)

  const handleChange = useCallback<ChangeEventHandler<HTMLInputElement>>(
    event => {
      if (typeof defaultValue === "string")
        setValue(transform(event.target.value as T))
      else if (Number(event.target.value) >= 0)
        setValue(Number(event.target.value) as T)
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    []
  )

  return [value, setValue, handleChange]
}
