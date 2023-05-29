import styled from "styled-components"

import Table from "src/app/components/Table"
import { IOwner } from "src/app/store/api/owners/types"
import { text16Medium } from "src/app/utils/fonts"

interface IOwnersListProps {
  isLoading: boolean
  owners: IOwner[] | undefined
}

const columns = [
  { name: "id", title: "ID" },
  { name: "name", title: "Имя" },
  { name: "username", title: "Логин" },
  { name: "birthDate", title: "Дата рождения" },
  { name: "roles", title: "Роли" },
]

export default function OwnersList({ isLoading, owners }: IOwnersListProps) {
  if (isLoading || !owners) return <Info>Загрузка...</Info>

  if (owners.length === 0) return <Info>Ничего не найдено</Info>

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
        {owners.map(({ id, name, username, birthDate, roles }) => (
          <tr key={id}>
            {[id, name, username, birthDate, roles.join(", ")].map(
              (value, index) => (
                <td key={index}>
                  <div>
                    {index !== 0 && <Divider />}
                    {value}
                  </div>
                </td>
              )
            )}
          </tr>
        ))}
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
