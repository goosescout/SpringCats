import { HelmetProvider } from "react-helmet-async"
import { Provider as ReduxProvider } from "react-redux"

import Layout from "src/app/components/Layout"
import AppRouter from "src/app/pages/AppRouter"
import { makeReduxStore } from "src/app/store/store"

export default function App() {
  const store = makeReduxStore()

  return (
    <HelmetProvider>
      <ReduxProvider store={store}>
        <Layout>
          <AppRouter />
        </Layout>
      </ReduxProvider>
    </HelmetProvider>
  )
}
