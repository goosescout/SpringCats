import styled from "styled-components"

import { text14Medium } from "src/app/utils/fonts"

const CustomButton = styled.button`
  height: 40px;
  padding: 0 24px;

  border: none;
  border-radius: 8px;

  cursor: pointer;

  ${text14Medium};

  color: #ffffff;
  background: #55cd37;

  &:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
`

export default CustomButton
