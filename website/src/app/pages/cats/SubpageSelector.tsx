import Add from "./subpages/Add"
import All from "./subpages/All"
import ByBirthDate from "./subpages/ByBirthDate"
import ByBreed from "./subpages/ByBreed"
import ByColor from "./subpages/ByColor"
import ById from "./subpages/ById"
import ByName from "./subpages/ByName"
import ByOwnerId from "./subpages/ByOwnerId"
import Create from "./subpages/Create"
import Delete from "./subpages/Delete"
import Edit from "./subpages/Edit"
import Friendship from "./subpages/Friendship"
import Update from "./subpages/Update"
import { Subpage } from "./types"

interface ISubpageSelectorProps {
  subpage: Subpage
}

export default function SubpageSelector({ subpage }: ISubpageSelectorProps) {
  switch (subpage) {
    case Subpage.Create:
      return <Create />
    case Subpage.Add:
      return <Add />
    case Subpage.All:
      return <All />
    case Subpage.ById:
      return <ById />
    case Subpage.ByName:
      return <ByName />
    case Subpage.ByColor:
      return <ByColor />
    case Subpage.ByBirthDate:
      return <ByBirthDate />
    case Subpage.ByOwnerId:
      return <ByOwnerId />
    case Subpage.ByBreed:
      return <ByBreed />
    case Subpage.Update:
      return <Update />
    case Subpage.Edit:
      return <Edit />
    case Subpage.Friendship:
      return <Friendship />
    default:
      return <Delete />
  }
}
