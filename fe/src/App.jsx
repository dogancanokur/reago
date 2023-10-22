import {Outlet} from "react-router-dom";
import React from "react";
import {useTranslation} from "react-i18next";
import {LanguageSelector} from "./locales/LanguageSelector.jsx";
import {NavBar} from "./component/NavBar.jsx";

function App() {

    const {t} = useTranslation();
    return (
        <>
            <NavBar/>
            <div className="container my-3">
                <Outlet/>
                <LanguageSelector/>
            </div>
        </>
    );
}

export default App
