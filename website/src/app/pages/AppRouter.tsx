import { Route, Routes } from "react-router-dom"

import Cats from "src/app/pages/cats"
import Home from "src/app/pages/home"
import CatOwners from "src/app/pages/owners"
import Profile from "src/app/pages/profile"

export default function AppRouter() {
  return (
    <Routes>
      <Route index Component={Home} />
      <Route path="/cats" Component={Cats} />
      <Route path="/owners" Component={CatOwners} />
      <Route path="/profile" Component={Profile} />
    </Routes>
  )
}
