import styled from "styled-components"

import { text18Medium } from "src/app/utils/fonts"

const Sidebar = styled.div`
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);

  grid-area: sidebar;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  gap: 16px;
  padding: 20px;
`

const SidebarItem = styled.div<{ active: boolean }>`
  border-radius: 95px;
  cursor: pointer;
  user-select: none;

  padding: 8px 16px;
  background-color: ${({ active }) => (active ? "#ebebeb" : "transparent")};
  transition: background-color var(--transition-duration)
    var(--transition-function);

  ${text18Medium};
  text-decoration: none;
  color: #222222;

  &:hover {
    background-color: #ebebeb;
  }

  > span {
    margin-left: 8px;
    color: #cf222e;
  }
`

export default Sidebar
export { SidebarItem }
