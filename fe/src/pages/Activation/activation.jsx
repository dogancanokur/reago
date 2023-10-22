import {useTranslation} from "react-i18next";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {activateUser} from "./api.js";

export function Activation() {
    const {t} = useTranslation();
    const [apiProgress, setApiProgress] = useState();
    const [successMessage, setSuccessMessage] = useState();
    const [errorMessage, setErrorMessage] = useState();
    const {token} = useParams();

    useEffect(() => {
        const timeout = setTimeout(() => {
            async function activate() {
                setApiProgress(true);
                setSuccessMessage(undefined);
                setErrorMessage(undefined);
                try {
                    let response = await activateUser(token);
                    setSuccessMessage(response.data.message);
                } catch (error) {
                    setErrorMessage(error.response.data.message);

                } finally {
                    setApiProgress(false);
                }
            }

            activate();

        }, 0);

        return () => {
            clearTimeout(timeout);
        }
    }, []);

    return (<div className={'text-center'}>
        {apiProgress && (<span className={'spinner-border spinner-border'}></span>)}
        {successMessage && (<div className={'alert alert-success'}>{successMessage}</div>)}
        {errorMessage && (<div className={'alert alert-danger'}>{errorMessage}</div>)}
    </div>);
}