import {useCallback, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {Alert} from "@/shared/component/Alert.jsx";
import {loadUsers} from "@/pages/User/api.js";
import {Spinner} from "@/shared/component/Spinner.jsx";
import {UserListItem} from "@/pages/User/component/UserListItem.tsx";

export function UserListPage() {

    const {t} = useTranslation();

    const [userPage, setUserPage] = useState({
        "content": [],
        "first": false,
        "last": false,
        "number": 0
    });

    const [errorMessage, setErrorMessage] = useState();
    const [apiProgress, setApiProgress] = useState()

    const getUsers = useCallback(async number => {
        setApiProgress(true);
        try {
            let axiosResponse = await loadUsers(number);
            setUserPage(axiosResponse.data);
        } catch (error) {
            setErrorMessage(error.response.statusText);
        } finally {
            setApiProgress(false);
        }
    }, []);

    useEffect(() => {
        getUsers();
    }, []);

    return (
        <div className={'card'}>
            <div className={'card-header text-center fw-bold fs-4'}>{t('user-list')}</div>
            {errorMessage && (
                <div className={'m-2'}><Alert message={errorMessage} styleType={'danger'} center={true}/></div>)}
            <div className={'card-body'}>
                <table className="table">
                    <thead>
                    <tr>
                        <th scope="col">{t('username')}</th>
                        <th scope="col">{t('email')}</th>
                        <th scope="col">{t('image')}</th>
                        <th scope="col">{t('fullName')}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        userPage.content.map((user) => {
                            return <UserListItem user={user} key={user.id}/>
                        })
                    }
                    </tbody>
                </table>
            </div>
            <div className={'card-footer d-flex justify-content-around'}>
                {!apiProgress && (<button className={'btn btn-outline-secondary btn-sm'} disabled={userPage.first}
                                          onClick={() => getUsers(userPage.number - 1)}>{t('previous')}</button>)}
                {apiProgress && (<Spinner big={false}/>)}
                {!apiProgress && (<button className={'btn btn-outline-primary btn-sm'} disabled={userPage.last}
                                          onClick={() => getUsers(userPage.number + 1)}>{t('next')}</button>)}
            </div>
        </div>
    )
}