// eslint-disable-next-line no-unused-vars
import React from "react";
import defaultProfileImage from "@/assets/profile.png";
import {useNavigate} from "react-router-dom";

export interface User {
    id: number,
    username: string;
    email: string;
    image: string;
    name: string;
}

interface UserListItemProps {
    user: User;
}

export function UserListItem({user}: UserListItemProps) {

    let spreadElements = {};
    // const [selected, setSelected] = useState(false);
    // if (selected) {
    //     spreadElements = {/*'backgroundColor': '#ff8b8b',*/ 'cursor': 'pointer'};
    // }
    const navigate = useNavigate();
    // note : tablo degilde baska bisi olsa idi <Link> ile yapacaktim.
    return (
        <tr /*className={selected ? 'table-primary' : ''}*/ onClick={() => {
            // setSelected(!selected)
            navigate("/user/" + user.id);
        }} style={{'cursor': 'pointer'}}>
            <th style={{...spreadElements}}>
                <img src={defaultProfileImage} alt={'profile'} width={24}
                     className={'img-fluid rounded-circle shadow-sm me-2'}/>
                {user.username}
            </th>
            <td style={{...spreadElements}}>{user.email}</td>
            <td style={{...spreadElements}}>{user.image}</td>
            <td style={{...spreadElements}}>{user.name}</td>
        </tr>
    );
}