import React from 'react';
import ReactDOM from 'react-dom/client';
import reagoLogo from './assets/reago.svg';
import {SignUp} from "./pages/SignUp/index.jsx";
import './styles.scss';
import {LanguageSelector} from "./locales/LanguageSelector.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <div style={{'borderBottom': '1px black solid'}} className={'my-2 pb-2 text-center'}>
            <img src={reagoLogo} className="logo" alt="Reago logo" height={75}/>
        </div>
        <SignUp/>
        <LanguageSelector/>

    </React.StrictMode>,
)
