import React from "react";
import {useTranslation} from "react-i18next";

export function LanguageSelector() {

    const {i18n} = useTranslation();

    const onSelectLanguage = (language) => {
        i18n.changeLanguage(language);
        localStorage.setItem("lang", language);
    };

    return (<>
            <img alt={'Türkçe'} src="https://flagsapi.com/TR/shiny/64.png" height={34} role={"button"}
                 onClick={() => onSelectLanguage('tr')}/>
            <img alt={'English'} src="https://flagsapi.com/GB/shiny/64.png" height={34} role={"button"}
                 onClick={() => onSelectLanguage('en')}/>
        </>
    );
}