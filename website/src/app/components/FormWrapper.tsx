import styled from "styled-components"

import { text16Medium } from "src/app/utils/fonts"

const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;

  > form {
    width: 560px;
    display: flex;
    flex-direction: column;
    gap: 20px;

    > label {
      display: flex;
      flex-wrap: wrap;
      justify-content: flex-end;
      align-items: center;
      gap: 16px;

      ${text16Medium};
      color: #222222;
      text-align: right;

      > input:not(input[type="checkbox"]) {
        width: 380px;
      }
    }

    > button {
      width: 380px;
      margin-left: auto;
    }
  }
`

export default FormWrapper
