import {Outlet} from "react-router-dom";
import React from "react";
import {LanguageSelector} from "@/locales/LanguageSelector.jsx";
import {NavBar} from "@/shared/component/NavBar.jsx";

function App() {

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
