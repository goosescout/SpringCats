import React from "react"

import ReactDOM from "react-dom/client"
import { BrowserRouter } from "react-router-dom"

import "src/index.css"
import App from "src/app/App"
import reportWebVitals from "src/app/reportWebVitals"

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement)
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
)

reportWebVitals()
