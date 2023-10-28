// eslint-disable-next-line no-unused-vars
import React from "react";
import {getUser} from "@/pages/User/api.js";
import {useNavigate} from "react-router-dom";
import {useTranslation} from "react-i18next";

import defaultImage from "@/assets/profile.png";
import {Alert} from "@/shared/component/Alert.jsx";
import {useRouteParamApiRequest} from "@/shared/hooks/useRouteParamApiRequest.js";
import {Spinner} from "@/shared/component/Spinner.jsx";

export function UserPage() {

    const {t} = useTranslation();

    const {apiProgress, data: user, error: errorMessage} = useRouteParamApiRequest("userId", getUser);

    let page = "";
    let navigate = useNavigate();

    if (apiProgress) {
        return (<Alert message={<Spinner big={true}/>} center={true} styleType={'warning'}/>);
    }
    if (user) {
        page = (<div className="card" style={{'width': '100%'}}>
            <img src={defaultImage} className="mx-auto" alt="profile" height={100} width={100}/>
            <div className="card-body">
                <h5 className="card-title">{user?.username}</h5>
                <p className="card-text">{user?.email}</p>
                <a href="#" className="btn btn-primary" onClick={() => {
                    navigate(-1);
                }}>{t('go-back')}</a>
            </div>
        </div>);
    }

    return (
        <>
            {errorMessage && (<Alert styleType={'danger'} message={errorMessage} center={false}/>)}
            {page}
        </>
    );
}