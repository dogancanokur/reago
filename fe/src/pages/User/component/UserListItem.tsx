// eslint-disable-next-line no-unused-vars
import React, {useState} from "react";
import defaultProfileImage from "@/assets/profile.png";

export interface User {
    id: number,
    username: string;
    email: string;
    image: string;
    fullName: string;
}

interface UserListItemProps {
    user: User;
}

export function UserListItem({user}: UserListItemProps) {
    let spreadElements = {};
    const [selected, setSelected] = useState(false);
    if (selected) {
        spreadElements = {/*'backgroundColor': '#ff8b8b',*/ 'cursor': 'pointer'};
    }
    return (
        <tr className={selected ? 'table-primary' : ''} onClick={() => setSelected(!selected)}>
            <th style={{...spreadElements}}>
                <img src={defaultProfileImage} alt={'profile'} width={24}
                     className={'img-fluid rounded-circle shadow-sm me-2'}/>
                {user.username}
            </th>
            <td style={{...spreadElements}}>{user.email}</td>
            <td style={{...spreadElements}}>{user.fullName}</td>
            <td style={{...spreadElements}}>{user.fullName}</td>
        </tr>
    );
}