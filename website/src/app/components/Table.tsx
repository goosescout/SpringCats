import styled from "styled-components"

import { text16Medium } from "src/app/utils/fonts"

const Table = styled.table`
  width: 100%;
  border-collapse: separate;
  border-spacing: 0 12px;

  > thead {
    th {
      padding: 0;

      ${text16Medium};
      color: #222222;
      opacity: 0.5;
      text-align: left;
    }
  }

  > tbody {
    td {
      background-color: #f8f8f8;

      padding: 10px 0;
      max-width: 400px;
      overflow: hidden;

      ${text16Medium};
      color: #222222;
    }

    td:first-child {
      border-radius: 12px 0 0 12px;

      width: 30px;
      padding-left: 16px;
    }

    td:last-child {
      border-radius: 0 12px 12px 0;

      padding-right: 16px;
    }
  }

  div {
    display: flex;
    gap: 8px;
  }
`

export default Table
