import {useState} from "react";
import {signUp} from "./api.js";

export function SignUp() {

    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordRepeat, setPasswordRepeat] = useState();
    const [apiProgress, setApiProgress] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const signUpSubmit = async (e) => {
        e.preventDefault();
        setApiProgress(true);
        setSuccessMessage('');
        setErrorMessage('');
        try {
            const response = await signUp({username, email, password});
            setSuccessMessage(response.data.message);

        } catch (error) {
            console.error(error);
            setErrorMessage(error.message);

        } finally {
            setApiProgress(false);
        }
    }

    let isSignUpButtonDisabled = apiProgress || (!password || password !== passwordRepeat);

    return (
        <div className={'container'}>
            <div className={'col-8 offset-2'}>
                <form className={'card'} onSubmit={signUpSubmit}>
                    <h1 className={'card-header text-center'}>Sign Up</h1>
                    <div className={'card-body'}>
                        <div className="mb-3">
                            <label htmlFor={'username'} className="form-label">Username</label>
                            <input className="form-control" id={'username'} name={'username'}
                                   onChange={(event) => setUsername(event.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor={'email'} className="form-label">Email address</label>
                            <input className="form-control" id={'email'} name={'email'}
                                   onChange={(event) => setEmail(event.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor={'password'} className="form-label">Password</label>
                            <input className="form-control" id={'password'} name={'password'} type={'password'}
                                   onChange={(event) => setPassword(event.target.value)}/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor={'passwordRepeat'} className="form-label">Password Repeat</label>
                            <input className="form-control" id={'passwordRepeat'} name={'passwordRepeat'}
                                   type={'password'}
                                   onChange={(event) => setPasswordRepeat(event.target.value)}/>
                        </div>
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