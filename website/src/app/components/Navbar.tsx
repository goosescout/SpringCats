import { useCallback } from "react"

import { Link, NavLink, useNavigate } from "react-router-dom"
import styled from "styled-components"

import { text18Medium } from "src/app/utils/fonts"
import SpringIcon from "src/assets/icons/SpringIcon"
import UserIcon from "src/assets/icons/UserIcon"

export default function Navbar() {
  const navigate = useNavigate()

  const handleLogoClick = useCallback(() => {
    navigate("/")
  }, [navigate])

  return (
    <Wrapper>
      <Logo width={42} height={42} onClick={handleLogoClick} />
      <NavCategory to="/cats">Котики</NavCategory>
      <NavCategory to="/owners">Хозяева</NavCategory>
      <ProfileCategory to="/profile">
        <span>Профиль</span>
        <UserIcon width={42} height={42} />
      </ProfileCategory>
    </Wrapper>
  )
}

const Wrapper = styled.nav`
  background-color: #ffffff;
  border-bottom: 1px solid #ebebeb;

  box-sizing: border-box;
  height: 70px;
  padding: 0 24px;
  width: 100%;
  display: flex;
  align-items: center;
  column-gap: 24px;

  position: fixed;
  z-index: 1000;
  top: 0;
`

const Logo = styled(SpringIcon)`
  cursor: pointer;
`

const NavCategory = styled(NavLink)`
  border-radius: 95px;
  cursor: pointer;
  user-select: none;

  padding: 8px 16px;
  background-color: transparent;
  transition: background-color var(--transition-duration)
    var(--transition-function);

  ${text18Medium};
  text-decoration: none;
  color: #222222;

  &.active,
  &:hover {
    background-color: #ebebeb;
  }
`

const ProfileCategory = styled(Link)`
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px;

  margin-left: auto;
  position: relative;
  right: -8px;

  text-decoration: none;

  > span {
    ${text18Medium};
    color: #222222;
  }
`
