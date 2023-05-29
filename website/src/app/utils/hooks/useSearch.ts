import { ChangeEventHandler, useCallback, useState } from "react"

export default function useSearch<T extends string | number>(
  defaultValue: T
): [T, T, ChangeEventHandler<HTMLInputElement>, () => void] {
  const [value, setValue] = useState(defaultValue)
  const [search, setSearch] = useState(value)

  const handleChange = useCallback<ChangeEventHandler<HTMLInputElement>>(
    event => {
      if (typeof defaultValue === "string") setValue(event.target.value as T)
      else if (Number(event.target.value) >= 0) {
        setValue(Number(event.target.value) as T)
      }
    },
    // eslint-disable-next-line react-hooks/exhaustive-deps
    []
  )

  const handleSearchClick = useCallback(() => {
    if (value) setSearch(value)
  }, [value])

  return [value, search, handleChange, handleSearchClick]
}
