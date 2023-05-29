import { useState } from "react"

import { Navigate } from "react-router-dom"
import styled from "styled-components"

import PageHeader from "src/app/components/PageHeader"
import Sidebar, { SidebarItem } from "src/app/components/Sidebar"
import { useAppSelector } from "src/app/store"

import SubpageSelector from "./SubpageSelector"
import { Subpage } from "./types"

interface ISubpageDescription {
  subpage: Subpage
  title: string
  adminOnly: boolean
}

const subpages: ISubpageDescription[] = [
  { subpage: Subpage.Create, title: "Создать", adminOnly: true },
  { subpage: Subpage.All, title: "Все", adminOnly: false },
  { subpage: Subpage.ById, title: "По ID", adminOnly: false },
  { subpage: Subpage.ByName, title: "По имени", adminOnly: false },
  { subpage: Subpage.ByUsername, title: "По логину", adminOnly: false },
  { subpage: Subpage.ByBirthDate, title: "По дате рождения", adminOnly: false },
  { subpage: Subpage.Update, title: "Обновить", adminOnly: true },
  { subpage: Subpage.Delete, title: "Удалить", adminOnly: true },
]

export default function CatOwners() {
  const { username, roles } = useAppSelector(({ user }) => user)

  const [currentSubpage, setCurrentSubpage] = useState<Subpage>(Subpage.All)

  if (!username) return <Navigate to="/profile" replace />

  return (
    <Wrapper>
      <PageHeader>
        <h1>Хозяева</h1>
        <p>
          На данном экране вы можете узнать информацию о хозяевах, создать
          нового пользователя, а также изменить или удалить уже имеющихся.
        </p>
      </PageHeader>
      <Sidebar>
        {subpages.map(({ subpage, title, adminOnly }) => {
          if (adminOnly && !roles?.includes("ADMIN")) return null

          return (
            <SidebarItem
              key={subpage}
              onClick={() => setCurrentSubpage(subpage)}
              active={subpage === currentSubpage}
            >
              {title}
              <span>{adminOnly && "(админ)"}</span>
            </SidebarItem>
          )
        })}
      </Sidebar>
      <Content>
        <SubpageSelector subpage={currentSubpage} />
      </Content>
    </Wrapper>
  )
}

const Wrapper = styled.div`
  padding: 32px;
  display: grid;
  grid-template-areas:
    "header header"
    "sidebar content";
  gap: 40px;
  grid-template-columns: 250px 1fr;

  margin: 0 auto;
  max-width: 1200px;
`

const Content = styled.div`
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);

  grid-area: content;
  padding: 20px;
`
