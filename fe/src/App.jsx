import {Link, Outlet} from "react-router-dom";
import React from "react";
import {LanguageSelector} from "@/locales/LanguageSelector.jsx";
import {NavBar} from "@/shared/component/NavBar.jsx";

function App() {

    return (
        <>
            <NavBar/>
            <div className="container my-3">
                <div><Link to={'userTest/1'}>asdf</Link></div>
                <div><Link to={'userTest/2'}>asdf</Link></div>
                <Outlet/>
                <LanguageSelector/>
            </div>
        </>
    );
}

export default App
