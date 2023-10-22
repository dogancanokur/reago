import {useEffect, useState} from "react";
import axios from "axios";
import {useTranslation} from "react-i18next";
import {Alert} from "@/shared/component/Alert.jsx";

export function User() {

    const {t} = useTranslation();

    const [userList, setUserList] = useState([]);
    const [errorMessage, setErrorMessage] = useState();

    useEffect(() => {
        async function getAllUsers() {
            try {
                let axiosResponse = await axios.get("/api/v1/users/");
                setUserList(axiosResponse.data);
            } catch (error) {
                setErrorMessage(error.response.data.message);
            }
        }

        getAllUsers();
    }, []);

    return (
        <>
            {errorMessage && (<Alert message={errorMessage} styleType={'danger'} center={true}/>)}
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">{t('id')}</th>
                    <th scope="col">{t('username')}</th>
                    <th scope="col">{t('email')}</th>
                    <th scope="col">{t('isActive')}</th>
                    <th scope="col">{t('activationToken')}</th>
                </tr>
                </thead>
                <tbody>
                {
                    userList.map((value, index) => {
                        return (<tr key={index}>
                            <th>{index + 1}</th>
                            <td>{value.username}</td>
                            <td>{value.email}</td>
                            <td>{value.active ? t('active') : t('not-active')}</td>
                            <td>{value.activationToken || t('already activated')}</td>
                        </tr>)

                    })
                }
                </tbody>
            </table>
        </>
    )
}