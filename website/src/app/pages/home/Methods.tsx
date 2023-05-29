import styled from "styled-components"

import { code16 } from "src/app/utils/fonts"

export function POST() {
  return (
    <Method>
      {"<"}POST{">"}
    </Method>
  )
}

export function GET() {
  return (
    <Method>
      {"<"}GET{">"}
    </Method>
  )
}

const Method = styled.span`
  ${code16};
  color: #6db33f;
  font-weight: 600;
`
