import { useCallback } from "react"

import { useNavigate } from "react-router-dom"
import styled from "styled-components"

import { IEndpoints } from "src/app/pages/home"
import { GET, POST } from "src/app/pages/home/Methods"
import { code16, code30, header48 } from "src/app/utils/fonts"

interface ICardProps {
  title: string
  baseUrl: string
  navigateUrl: string
  endpoints: IEndpoints[]
}

export default function Card({
  title,
  baseUrl,
  navigateUrl,
  endpoints,
}: ICardProps) {
  const navigate = useNavigate()

  const handleClick = useCallback(() => {
    navigate(navigateUrl)
  }, [navigate, navigateUrl])

  return (
    <CardWrapper onClick={handleClick}>
      <h2>{title}</h2>
      <h3>{baseUrl}</h3>
      <ApiEndpoints>
        <tbody>
          {endpoints.map(({ method, path, type }, index) => (
            <tr key={path}>
              <td>{index + 1}.</td>
              <td>{method === "POST" ? <POST /> : <GET />}</td>
              <td>
                {path}
                {type && <Type>{type}</Type>}
              </td>
            </tr>
          ))}
        </tbody>
      </ApiEndpoints>
    </CardWrapper>
  )
}

const CardWrapper = styled.div`
  cursor: pointer;
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);
  transition: box-shadow var(--transition-duration) var(--transition-function),
    transform var(--transition-duration) var(--transition-function);

  box-sizing: border-box;
  padding: 30px 20px 20px 20px;
  min-width: 480px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;

  > * {
    opacity: 0.5;
    transition: opacity var(--transition-duration) var(--transition-function),
      color var(--transition-duration) var(--transition-function);
  }

  > h2 {
    margin: 0;
    ${header48};
    color: #222222;
  }

  > h3 {
    margin: 0;
    ${code30};
    color: #222222;
  }

  &:hover {
    box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
    transform: translate(0, -6px);

    > * {
      opacity: 1;
    }

    > h3 {
      color: #6db33f;
    }
  }
`

const ApiEndpoints = styled.table`
  background-color: #f8f8f8;
  border-radius: 24px;

  box-sizing: border-box;
  width: 100%;
  padding: 20px 16px;
  border-spacing: 8px 4px;

  ${code16};

  > tr {
    > td {
      padding: 0;
    }

    > td:first-of-type {
      text-align: right;
    }
  }
`

const Type = styled.span`
  background-color: #ebebeb;
  color: #cf222e;
`
