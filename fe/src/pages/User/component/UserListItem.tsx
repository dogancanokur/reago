// eslint-disable-next-line no-unused-vars
import React from "react";

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
    return (
        <tr>
            <th>{user.username}</th>
            <td>{user.email}</td>
            <td>{user.image}</td>
            <td>{user.fullName}</td>
            <td>{user.fullName}</td>
        </tr>
    );
}