import {Link, useNavigate} from "react-router-dom";
import reagoLogo from "@/assets/reago.svg";
import {useTranslation} from "react-i18next";
import {useDispatch, useSelector} from "react-redux";
import {logoutSuccess} from "@/state/redux.js";

export function NavBar() {

    const {t} = useTranslation();
    const authState = useSelector((store) => store.auth);
    const authDispatch = useDispatch();
    // const authState = useAuthState();
    // const authDispatch = useAuthDispatch();
    const navigate = useNavigate();


    return (
        <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
            <div className="container-fluid">
                <Link className={'navbar-brand'} to={'/'}>
                    <img src={reagoLogo} alt="Reago logo" height={40} className="d-inline-block align-text-top"/>
                    &nbsp;Reago
                </Link>
                <ul className={'navbar-nav'}>
                    <li className={'nav-item'}>
                        <Link className={'nav-link'} reloadDocument to={'/users'}>{t('user-list')}</Link>
                    </li>
                    {authState.id === 0 &&
                        <>
                            <li className={'nav-item'}>
                                <Link className={'nav-link'} to={'/signup'}>{t('sign-up')}</Link>
                            </li>
                            <li className={'nav-item'}>
                                <Link className={'nav-link'} to={'/login'}>{t('login')}</Link>
                            </li>
                        </>
                    }
                    {authState.id !== 0 &&
                        <>
                            <li className={'nav-item'}>
                                <Link className={'nav-link'} to={'/'}>@{authState.username}</Link>
                            </li>
                            <li className={'nav-item'}>
                                <Link className={'nav-link'} to={'/'}
                                      onClick={() => {
                                          authDispatch(logoutSuccess())
                                      }}>{t('logout')}</Link>
                            </li>
                        </>
                    }

                </ul>
            </div>
        </nav>);
}