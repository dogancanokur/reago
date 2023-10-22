import React from 'react';
import ReactDOM from 'react-dom/client';
import reagoLogo from './assets/reago.svg';
import './styles.scss';
import {LanguageSelector} from "./locales/LanguageSelector.jsx";
import {RouterProvider} from "react-router-dom";
import router from "./router/index.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <div style={{'borderBottom': '1px black solid'}} className={'my-2 pb-2 text-center'}>
            <img src={reagoLogo} className="logo" alt="Reago logo" height={75}/>
        </div>

        <RouterProvider router={router}/>
        {/*<BrowserRouter>*/}
        {/*    <Routes>*/}
        {/*        <Route path="/" element={<Home/>}></Route>*/}
        {/*        <Route path="/signup" element={<SignUp/>}></Route>*/}
        {/*    </Routes>*/}
        {/*</BrowserRouter>*/}

        <LanguageSelector/>
    </React.StrictMode>,
)
