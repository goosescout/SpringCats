import styled from "styled-components"

import Table from "src/app/components/Table"
import { ICat } from "src/app/store/api/cats/types"
import { text16Medium } from "src/app/utils/fonts"

interface IOwnersListProps {
  isLoading: boolean
  cats: ICat[] | undefined
}

const columns = [
  { name: "id", title: "ID" },
  { name: "name", title: "Имя" },
  { name: "color", title: "Цвет" },
  { name: "breed", title: "Порода" },
  { name: "birthDate", title: "Дата рождения" },
  { name: "ownerId", title: "Хозяин" },
  { name: "friends", title: "ID друзей" },
]

export default function CatsList({ isLoading, cats }: IOwnersListProps) {
  if (isLoading || !cats) return <Info>Загрузка...</Info>

  if (cats.length === 0) return <Info>Ничего не найдено</Info>

  return (
    <Table>
      <thead>
        <tr>
          {columns.map(({ name, title }, index) => (
            <th key={name}>
              {index === 0 ? (
                title
              ) : (
                <div>
                  <Divider />
                  {title}
                </div>
              )}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {cats.map(
          ({ id, name, color, breed, birthDate, catOwnerId, friendIds }) => (
            <tr key={id}>
              {[
                id,
                name,
                color,
                breed ?? "❓",
                birthDate,
                catOwnerId,
                friendIds.length > 0 ? friendIds.join(", ") : "😭",
              ].map((value, index) => (
                <td key={index}>
                  <div>
                    {index !== 0 && <Divider />}
                    {value}
                  </div>
                </td>
              ))}
            </tr>
          )
        )}
      </tbody>
    </Table>
  )
}

const Info = styled.p`
  margin: 0;

  ${text16Medium};
  color: #222222;
  text-align: center;
`

const Divider = styled.div`
  border-radius: 1px;
  width: 1px;
  height: 20px;
  background-color: #222222;
`
