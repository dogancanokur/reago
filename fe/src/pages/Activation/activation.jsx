import {useTranslation} from "react-i18next";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {activateUser} from "./api.js";
import {Alert} from "@/shared/component/Alert.jsx";
import {Spinner} from "@/shared/component/Spinner.jsx";

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

    return (<div>
        {apiProgress && (<Alert message={<Spinner big={true}/>} center={true} styleType={'success'}/>)}
        {successMessage && (<Alert message={successMessage} styleType={'success'}/>)}
        {errorMessage && (<Alert message={errorMessage} styleType={'danger'}/>)}
    </div>);
}