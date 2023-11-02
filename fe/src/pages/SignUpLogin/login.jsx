import {useTranslation} from "react-i18next";
import {Input} from "@/shared/component/Input.jsx";
import {useEffect, useState} from "react";
import {login} from "@/pages/SignUpLogin/api.js";
import {Alert} from "@/shared/component/Alert.jsx";
import {Button} from "@/shared/component/Button.jsx";
// import {useAuthDispatch} from "@/shared/state/context.jsx";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {loginSuccess} from "@/state/redux.js";

export function Login() {

    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [apiProgress, setApiProgress] = useState(false);
    const [errorMessage, setErrorMessage] = useState();
    const [validationErrors, setValidationErrors] = useState({});
    const {t} = useTranslation();
    const authDispatch = useDispatch();
    // const authDispatch = useAuthDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, email: undefined}));
    }, [email]);

    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, password: undefined}));
    }, [password]);

    const loginSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage();
        setApiProgress(true);
        setValidationErrors({});

        try {
            let body = {
                email, password
            };
            let response = await login(body);
            const {token, userOutput} = response.data;
            // authDispatch({type: 'loginSuccess', data: userOutput});
            authDispatch(loginSuccess(userOutput));
            navigate("/");
            console.log(token);

        } catch (error) {
            if (error.response?.data) {
                if (error.response.data.validationError) {
                    const {validationError} = error.response.data;
                    setValidationErrors(validationError);

                } else {
                    setErrorMessage(error.response.data.message);
                }
            } else {
                setErrorMessage(t("genericError"));
            }

        } finally {
            setApiProgress(false);
        }
    }

    let buttonDisabled = apiProgress || (!email || !password);

    return (<>
        {errorMessage && (<Alert styleType={'danger'} message={errorMessage}></Alert>)}
        <div className={'container'}>
            <div className={'col-8 offset-2'}>
                <form className={'card'} onSubmit={loginSubmit}>
                    <h1 className={'card-header text-center'}>{t('login')}</h1>
                    <div className={'card-body'}>
                        <div className="mb-3">
                            <Input id={'email'}
                                   name={'email'}
                                   labelText={t('email')}
                                   validationError={validationErrors?.email}
                                   onChange={() => setEmail(event.target.value)}
                                   type={'email'}></Input>
                        </div>
                        <div className="mb-3">
                            <Input id={'password'}
                                   name={'password'}
                                   type={'password'}
                                   labelText={t('password')}
                                   validationError={validationErrors?.password}
                                   onChange={() => setPassword(event.target.value)}></Input>
                        </div>
                    </div>
                    <div className="card-footer">
                        <div className={'text-center'}>
                            <Button disabled={buttonDisabled} type={'submit'}
                                    className={'btn-primary'} text={t('login')}
                                    apiProgress={apiProgress}/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </>);
}