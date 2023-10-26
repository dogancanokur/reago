// eslint-disable-next-line no-unused-vars
import React, {useEffect, useState} from "react";
import {getUser} from "@/pages/User/api.js";
import {useNavigate, useParams} from "react-router-dom";
import {useTranslation} from "react-i18next";

import defaultImage from "@/assets/profile.png";
import {Alert} from "@/shared/component/Alert.jsx";

export function UserPage() {

    const {t} = useTranslation();
    const [errorMessage, setErrorMessage] = useState();
    const [user, setUser] = useState();
    const {userId} = useParams();


    useEffect(() => {
        const timeout = setTimeout(() => {
            async function callUser() {

                try {
                    const response = await getUser(userId);
                    setUser(response.data);
                } catch (err) {
                    setUser(undefined);
                    if (err.response.status === 400) {
                        setErrorMessage(err.response.data.message);
                    } else {
                        setErrorMessage(t("genericError"));
                    }
                }
            }

            callUser();

        }, 0);

        return () => {
            clearTimeout(timeout);
        }
    }, []);

    let page = "";
    let navigate = useNavigate();

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