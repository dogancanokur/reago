import {Outlet} from "react-router-dom";
import {LanguageSelector} from "@/locales/LanguageSelector.jsx";
import {NavBar} from "@/shared/component/NavBar.jsx";
import {Provider} from "react-redux";
import {store} from "@/state/redux.js";

function App() {

    return (
        // <AuthenticationContext>
        <Provider store={store}>
            <NavBar/>
            <div className="container my-3">
                <Outlet/>
                <LanguageSelector/>
            </div>
        </Provider>
        // </AuthenticationContext>
    );
}

export default App
