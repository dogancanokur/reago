import {Link} from "react-router-dom";
import reagoLogo from "@/assets/reago.svg";
import React from "react";
import {useTranslation} from "react-i18next";

export function NavBar() {

    const {t} = useTranslation();

    return (
        <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
            <div className="container-fluid">
                <Link className={'navbar-brand'} to={'/'}>
                    <img src={reagoLogo} alt="Reago logo" height={40} className="d-inline-block align-text-top"/>
                    &nbsp;Reago
                </Link>
                <ul className={'navbar-nav'}>
                    <li className={'nav-item'}>
                        <Link className={'nav-link'} to={'/users'}>{t('user-list')}</Link>
                    </li>
                    <li className={'nav-item'}>
                        <Link className={'nav-link'} to={'/signup'}>{t('sign-up')}</Link>
                    </li>
                </ul>
            </div>
        </nav>);
}