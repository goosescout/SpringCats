import styled from "styled-components"

import { header48, text16Medium } from "src/app/utils/fonts"

const PageHeader = styled.div`
  background-color: #ffffff;
  border: 1px solid #ebebeb;
  border-radius: 24px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.05);
  padding: 30px 20px;
  gap: 12px;

  grid-area: header;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;

  > h1 {
    max-width: 100%;
    margin: 0;
    text-align: center;
    overflow-wrap: break-word;

    color: #222222;
    ${header48};
  }

  > p {
    margin: 0;
    width: 1000px;

    color: #222222;
    ${text16Medium};
    text-align: center;
  }
`

export default PageHeader
