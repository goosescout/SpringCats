import All from "./subpages/All"
import ByBirthDate from "./subpages/ByBirthDate"
import ById from "./subpages/ById"
import ByName from "./subpages/ByName"
import ByUsername from "./subpages/ByUsername"
import Create from "./subpages/Create"
import Delete from "./subpages/Delete"
import Update from "./subpages/Update"
import { Subpage } from "./types"

interface ISubpageSelectorProps {
  subpage: Subpage
}

export default function SubpageSelector({ subpage }: ISubpageSelectorProps) {
  switch (subpage) {
    case Subpage.Create:
      return <Create />
    case Subpage.All:
      return <All />
    case Subpage.ById:
      return <ById />
    case Subpage.ByName:
      return <ByName />
    case Subpage.ByUsername:
      return <ByUsername />
    case Subpage.ByBirthDate:
      return <ByBirthDate />
    case Subpage.Update:
      return <Update />
    default:
      return <Delete />
  }
}
