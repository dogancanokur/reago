import {createContext, useState} from "react";
import {loadAuthState, storeAuthState} from "@/state/storage.js";

export const AuthContext = createContext();

export function AuthenticationContext({children}) {
    const initialState = {
        id: 0
    };
    const [auth, setAuth] = useState(loadAuthState);

    const onLoginSuccess = (data) => {
        setAuth(data);
        storeAuthState(data);
    }

    const onLogoutSuccess = () => {
        setAuth(initialState);
        storeAuthState(initialState);
    }

    return <AuthContext.Provider value={{
        ...auth,
        onLoginSuccess,
        onLogoutSuccess
    }}>
        {children}
    </AuthContext.Provider>
}