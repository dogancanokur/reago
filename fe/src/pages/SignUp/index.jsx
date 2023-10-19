import {useEffect, useMemo, useState} from "react";
import {signUp} from "./api.js";
import {Input} from "./components/Input.jsx";

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

    // useEffects
    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, username: undefined}));
    }, [username]);

    useEffect(() => {
        setValidationErrors(lastErrors => ({...lastErrors, email: undefined}));
    }, [email]);

    useEffect(() => {
        console.log("useEff");
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
            if (error.response?.data && error.response.data.status === 400) {
                const {validationError} = error.response.data;
                setValidationErrors(validationError);
            } else {
                setErrorMessage(error.message);
            }

            console.warn(error);

        } finally {
            setApiProgress(false);
        }
    }
    const passwordRepeatError = useMemo(() => {
        if (password && password !== passwordRepeat) {
            return "Password mismatch.";
        }
    }, [password, passwordRepeat]);


    let isSignUpButtonDisabled = apiProgress || (!password || password !== passwordRepeat);

    return (
        <div className={'container'}>
            <div className={'col-8 offset-2'}>
                <form className={'card'} onSubmit={signUpSubmit}>
                    <h1 className={'card-header text-center'}>Sign Up</h1>
                    <div className={'card-body'}>
                        <Input id={'username'} name={'username'} labelText={'Username'}
                               validationError={validationErrors?.username}
                               onChange={(event) => setUsername(event.target.value)}></Input>

                        <Input id={'email'} name={'email'} labelText={'Email'}
                               validationError={validationErrors?.email}
                               onChange={(event) => setEmail(event.target.value)}></Input>

                        <Input id={'password'} name={'password'} labelText={'Password'}
                               validationError={validationErrors?.password} type={'password'}
                               onChange={(event) => setPassword(event.target.value)}></Input>

                        <Input id={'passwordRepeat'} name={'passwordRepeat'} labelText={'Password Repeat'}
                               validationError={passwordRepeatError} type={'password'}
                               onChange={(event) => setPasswordRepeat(event.target.value)}></Input>

                        {successMessage && <div className={'alert alert-success'}>{successMessage}</div>}
                        {errorMessage && <div className={'alert alert-danger'}>{errorMessage}</div>}
                        <div className="mb-3 text-center">
                            <button className={'btn btn-primary'} disabled={isSignUpButtonDisabled}>{
                                apiProgress && <span className={'spinner-border spinner-border-sm'}></span>
                            } Sign Up
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
}