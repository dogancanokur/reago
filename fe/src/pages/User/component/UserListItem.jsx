// eslint-disable-next-line no-unused-vars
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { ProfileImage } from '@/pages/User/component/ProfileImage.jsx';
import { useAuthState } from '@/shared/state/context.jsx';

export function UserListItem({ user }) {
  let authState = useAuthState();
  let spreadElements = {};
  // const [selected, setSelected] = useState(false);
  // if (selected) {
  //     spreadElements = {/*'backgroundColor': '#ff8b8b',*/ 'cursor': 'pointer'};
  // }
  const navigate = useNavigate();
  // note : tablo degilde baska bisi olsa idi <Link> ile yapacaktim.
  return (
    <tr
      /*className={selected ? 'table-primary' : ''}*/ onClick={() => {
        // setSelected(!selected)
        navigate('/user/' + user.id);
      }}
      style={{ cursor: 'pointer' }}
    >
      <th
        className={user.id === authState.id ? 'selected' : ''}
        style={{ ...spreadElements }}
      >
        <ProfileImage alt={'profile'} image={user.image} />
        <span className={'ms-2'}>{user.username}</span>
      </th>
      <td
        className={user.id === authState.id ? 'selected' : ''}
        style={{ ...spreadElements }}
      >
        {user.email}
      </td>

      <td
        className={user.id === authState.id ? 'selected' : ''}
        style={{ ...spreadElements }}
      >
        {user.name}
      </td>
    </tr>
  );
}
