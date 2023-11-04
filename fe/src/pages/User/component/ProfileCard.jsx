import defaultProfileImage from "@/assets/profile.png";
import {useTranslation} from "react-i18next";
import {useAuthDispatch, useAuthState} from "@/shared/state/context.jsx";
import {Button} from "@/shared/component/Button.jsx";
import {useState} from "react";
import {Input} from "@/shared/component/Input.jsx";
import {updateUser} from "@/pages/User/component/api.js";
import {Alert} from "@/shared/component/Alert.jsx";

export function ProfileCard({user}) {
    const authState = useAuthState();
    const [editMode, setEditMode] = useState(false);
    const {t} = useTranslation();
    const [newUsername, setNewUsername] = useState(user.username);
    const [apiProgress, setApiProgress] = useState(false);
    const [generalError, setGeneralError] = useState();
    const [errors, setErrors] = useState({});
    const dispatch = useAuthDispatch();

    const onChangeUsername = (event) => {
        setNewUsername(event.target.value);
        setErrors({});
    };

    const visibleUsername = authState.id === user.id ? authState.username : user.username;
    const onClickSave = async () => {
        setApiProgress(true);
        setErrors({});
        setGeneralError();
        try {
            await updateUser(user.id, {id: user.id, username: newUsername});
            dispatch({type: 'user-update-success', data: {username: newUsername}});
            setEditMode(false);
        } catch (axiosError) {
            if (axiosError.response?.data) {
                if (axiosError.response.data.status === 400) {
                    if (axiosError.response.data.validationError)
                        setErrors(axiosError.response.data.validationError);
                    setGeneralError(axiosError.response.data.message)
                } else {
                    setGeneralError(axiosError.response.data.message);
                }
            } else {
                setGeneralError(t("genericError"));
            }
        } finally {
            setApiProgress(false);
        }
    };
    const onClickCancel = () => {
        setEditMode(false);
        setNewUsername(user.username);
        setErrors({});
        setGeneralError();
    }

    const isEditButtonVisible = !editMode && authState.id === user.id;


    return (
        <div className="card">
            <div className="card-header text-center">
                <img
                    src={defaultProfileImage}
                    width="200"
                    className="img-fluid rounded-circle shadow-sm"
                    alt={''}/>
            </div>
            <div className="card-body text-center">
                {generalError && (<Alert message={generalError} center={true} styleType={'danger'}/>)}
                {!editMode && <span className="fs-3 d-block mb-2">{visibleUsername}</span>}
                {isEditButtonVisible && (<Button onClick={() => setEditMode(true)} text={t('edit')}/>)}
                {editMode && (
                    <>
                        <Input
                            id={'username'}
                            name={'username'}
                            labelText={t("username")}
                            defaultValue={visibleUsername}
                            validationError={errors["username"]}
                            onChange={onChangeUsername}/>
                        <Button text={t('save')} apiProgress={apiProgress} onClick={onClickSave}/>
                        <div className="d-inline m-1"></div>
                        <Button className="btn-outline-secondary" text={t('cancel')}
                                onClick={onClickCancel}/>
                    </>
                )
                }
            </div>
        </div>
    )
        ;
}