import {Link} from "react-router-dom";
import reagoLogo from "@/assets/reago.svg";
import {useTranslation} from "react-i18next";
import {useAuthDispatch, useAuthState} from "@/shared/state/context.jsx";

export function NavBar() {

    const {t} = useTranslation();
    const authState = useAuthState();
    const authDispatch = useAuthDispatch();

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
                                          authDispatch({type: 'logoutSuccess'})
                                      }}>{t('logout')}</Link>
                            </li>
                        </>
                    }

                </ul>
            </div>
        </nav>);
}