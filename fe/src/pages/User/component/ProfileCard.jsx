import { useTranslation } from 'react-i18next';
import { useAuthState } from '@/shared/state/context.jsx';
import { Button } from '@/shared/component/Button.jsx';
import { useState } from 'react';
import { Alert } from '@/shared/component/Alert.jsx';
import { ProfileImage } from '@/pages/User/component/ProfileImage.jsx';
import { UserEditMode } from '@/pages/User/component/UserEditMode.jsx';

export function ProfileCard({ user }) {
  const authState = useAuthState();
  const [editMode, setEditMode] = useState(false);
  const { t } = useTranslation();
  const [generalError, setGeneralError] = useState();
  const [tempImage, setTempImage] = useState();

  const isEditButtonVisible = !editMode && authState.id === user.id;

  const visibleUsername =
    authState.id === user.id ? authState.username : user.username;
  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImage
          alt={'profileImage'}
          width={'200'}
          height={'200'}
          tempImage={tempImage}
          image={user.image}
        />
      </div>
      <div className="card-body text-center">
        {generalError && (
          <Alert message={generalError} center={true} styleType={'danger'} />
        )}
        {!editMode && (
          <span className="fs-3 d-block mb-2">{visibleUsername}</span>
        )}
        {isEditButtonVisible && (
          <Button onClick={() => setEditMode(true)} text={t('edit')} />
        )}
        {editMode && (
          <UserEditMode
            setGeneralError={setGeneralError}
            setEditMode={setEditMode}
            setTempImage={setTempImage}
          />
        )}
      </div>
    </div>
  );
}
