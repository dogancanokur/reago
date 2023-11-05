import { useTranslation } from 'react-i18next';
import { useEffect, useState } from 'react';
import { useAuthDispatch, useAuthState } from '@/shared/state/context.jsx';
import { updateUser } from '@/pages/User/component/api.js';
import { Input } from '@/shared/component/Input.jsx';
import { Button } from '@/shared/component/Button.jsx';

export function UserEditMode({ setGeneralError, setEditMode, setTempImage }) {
  const { t } = useTranslation();
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const authState = useAuthState();
  const dispatch = useAuthDispatch();
  const [newUsername, setNewUsername] = useState(authState.username);
  const [newImage, setNewImage] = useState();
  useEffect(() => {
    setTempImage(newImage);
  }, [newImage]);

  const onChangeUsername = (event) => {
    setNewUsername(event.target.value);
    setErrors({});
  };
  const onClickSave = async () => {
    event.preventDefault();
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      const { data } = await updateUser({
        id: authState.id,
        username: newUsername,
        image: newImage,
      });
      dispatch({
        type: 'user-update-success',
        data: { username: data.username, image: data.image },
      });
      setEditMode(false);
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          if (axiosError.response.data.validationError)
            setErrors(axiosError.response.data.validationError);
          setGeneralError(axiosError.response.data.message);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t('genericError'));
      }
    } finally {
      setApiProgress(false);
    }
  };
  const onClickCancel = () => {
    setEditMode(false);
    setNewUsername(authState.username);
    setErrors({});
    setGeneralError();
    setTempImage();
  };

  const onSelectImage = () => {
    if (event.target.files.length < 1) {
      return;
    }
    const file = event.target.files[0];
    const fileReader = new FileReader();

    fileReader.onloadend = () => {
      const data = fileReader.result;
      setNewImage(data);
    };

    fileReader.readAsDataURL(file); // onloadend'i çağırır
  };

  return (
    <form onSubmit={onClickSave}>
      <Input
        id={'username'}
        name={'username'}
        labelText={t('username')}
        defaultValue={authState.username}
        validationError={errors['username']}
        onChange={onChangeUsername}
      />
      {/*Select Image*/}
      <Input
        id={'image'}
        name={'image'}
        labelText={t('image')}
        type={'file'}
        onChange={onSelectImage}
      />

      <Button text={t('save')} apiProgress={apiProgress} />
      <div className="d-inline m-1"></div>
      <Button
        className="btn-outline-secondary"
        text={t('cancel')}
        type={'button'}
        onClick={onClickCancel}
      />
    </form>
  );
}
