import styled from "styled-components"

import Navbar from "src/app/components/Navbar"

interface ILayoutProps {
  children: React.ReactNode
}

export default function Layout({ children }: ILayoutProps) {
  return (
    <>
      <Navbar />

      <PageWrapper>{children}</PageWrapper>
    </>
  )
}

const PageWrapper = styled.div`
  box-sizing: border-box;
  min-height: calc(100vh - 70px);
  height: max-content;
  margin-top: 70px;
`
