import {useEffect, useMemo, useState} from "react";
import {signUp} from "./api.js";
import {Input} from "./components/Input.jsx";
import {useTranslation} from "react-i18next";
import {Alert} from "../../component/Alert.jsx";
import {Spinner} from "../../component/Spinner.jsx";

export function SignUp() {

    // useStates
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordRepeat, setPasswordRepeat] = useState();
    const [apiProgress, setApiProgress] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [validationErrors, setValidationErrors] = useState({});
    const {t} = useTranslation();

    // useEffects
    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, username: undefined}));
    }, [username]);

    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, email: undefined}));
    }, [email]);

    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, password: undefined}));
    }, [password]);

    const signUpSubmit = async (e) => {
        e.preventDefault();
        setApiProgress(true);
        setSuccessMessage('');
        setErrorMessage('');
        setValidationErrors({});
        try {
            const response = await signUp({username, email, password});
            setSuccessMessage(response.data.message);

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
    const passwordRepeatError = useMemo(() => {
        if (password && password !== passwordRepeat) {
            return t("passwordMismatch");
        }
    }, [password, passwordRepeat]);

    let isSignUpButtonDisabled = apiProgress || (!password || password !== passwordRepeat);

    return (
        <div className={'container'}>
            <div className={'col-8 offset-2'}>
                <form className={'card'} onSubmit={signUpSubmit}>
                    <h1 className={'card-header text-center'}>{t('sign-up')}</h1>
                    <div className={'card-body'}>
                        <Input id={'username'} name={'username'} labelText={t('username')}
                               validationError={validationErrors?.username}
                               onChange={(event) => setUsername(event.target.value)}></Input>

                        <Input id={'email'} name={'email'} labelText={t('email')}
                               validationError={validationErrors?.email}
                               onChange={(event) => setEmail(event.target.value)}></Input>

                        <Input id={'password'} name={'password'} labelText={t('password')}
                               validationError={validationErrors?.password} type={'password'}
                               onChange={(event) => setPassword(event.target.value)}></Input>

                        <Input id={'passwordRepeat'} name={'passwordRepeat'} labelText={t('passwordRepeat')}
                               validationError={passwordRepeatError} type={'password'}
                               onChange={(event) => setPasswordRepeat(event.target.value)}></Input>

                        {successMessage && <Alert styleType={'success'} message={successMessage}/>}
                        {errorMessage && <Alert styleType={'danger'} message={errorMessage}/>}
                        <div className="mb-3 text-center">
                            <button className={'btn btn-primary'} disabled={isSignUpButtonDisabled}>{
                                apiProgress && <Spinner/>} {t('sign-up')}
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}