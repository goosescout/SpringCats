import { Helmet } from "react-helmet-async"
import styled from "styled-components"

import Card from "src/app/pages/home/Card"
import { header60, text18Medium } from "src/app/utils/fonts"

export interface IEndpoints {
  method: "POST" | "GET"
  path: string
  type?: string
}

const catsEndpoints: IEndpoints[] = [
  { method: "POST", path: "/create" },
  { method: "POST", path: "/add" },
  { method: "GET", path: "/getById?id=", type: "int" },
  { method: "GET", path: "/getAll" },
  { method: "GET", path: "/getByName?name=", type: "string" },
  { method: "GET", path: "/getByBirthDate?date=", type: "date" },
  { method: "GET", path: "/getByBreed?breed=", type: "string" },
  { method: "GET", path: "/getByColor?color=", type: "string" },
  { method: "GET", path: "/getByCatOwnerId?ownerId=", type: "int" },
  { method: "POST", path: "/addFriend" },
  { method: "POST", path: "/removeFriend" },
  { method: "POST", path: "/update" },
  { method: "POST", path: "/edit" },
  { method: "POST", path: "/delete" },
]

const ownersEndpoints: IEndpoints[] = [
  { method: "POST", path: "/create" },
  { method: "GET", path: "/getById?id=", type: "int" },
  { method: "GET", path: "/getAll" },
  { method: "GET", path: "/getByName?name=", type: "string" },
  { method: "GET", path: "/getByBirthDate?date=", type: "date" },
  { method: "GET", path: "/getByUsername?username=", type: "string" },
  { method: "POST", path: "/update" },
  { method: "POST", path: "/delete" },
]

export default function Home() {
  return (
    <>
      <Helmet>
        <title>Главная страница</title>
      </Helmet>

      <Wrapper>
        <Header>
          <h1>Добро пожаловать!</h1>
          <p>
            Это приложение позволяет управлять списком котиков и их хозяев.{" "}
            <br />
            Добавляйте новых котиков, удаляйте их или редактируйте информацию.
            Также вы можете добавлять новых хозяев и назначать котиков им в
            ответственность.
          </p>
        </Header>
        <div>
          <Card
            title="Котики"
            baseUrl="/api/cats"
            navigateUrl="/cats"
            endpoints={catsEndpoints}
          />
          <Card
            title="Хозяева"
            baseUrl="/api/catowners"
            navigateUrl="/owners"
            endpoints={ownersEndpoints}
          />
        </div>
      </Wrapper>
    </>
  )
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 40px;
  padding: 32px;
  height: 100%;

  > div:last-child {
    width: 100%;
    display: grid;
    justify-content: center;
    grid-template-columns: repeat(auto-fit, 480px);
    grid-auto-rows: 1fr;
    gap: 40px;
  }
`

const Header = styled.div`
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);

  box-sizing: border-box;
  padding: 40px 30px;
  text-align: center;
  width: 1000px;

  h1 {
    margin: 0 0 12px 0;

    ${header60};
    color: #222222;
  }

  p {
    grid-area: description;
    width: 800px;
    margin: 0 auto;

    ${text18Medium};
    color: #222222;
    text-align: center;
  }
`
