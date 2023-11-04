import { Outlet } from "react-router-dom";
import { LanguageSelector } from "@/locales/LanguageSelector.jsx";
import { NavBar } from "@/shared/component/NavBar.jsx";
import { AuthenticationContext } from "@/shared/state/context.jsx";

function App() {
  return (
    <AuthenticationContext>
      <NavBar />
      <div className="container my-3">
        <Outlet />
        <LanguageSelector />
      </div>
    </AuthenticationContext>
  );
}

export default App;
