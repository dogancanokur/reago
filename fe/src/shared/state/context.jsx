import {createContext, useEffect, useReducer} from "react";
import {loadAuthState, storeAuthState} from "@/state/storage.js";

export const AuthContext = createContext();

const authReducer = (authState, action) => {
    switch (action.type) {
        case 'loginSuccess': {
            return action.data;
        }
        case 'logoutSuccess': {
            return {id: 0};
        }
        default:
            throw new Error(`unknown action: ${action.type}`);
    }
}

export function AuthenticationContext({children}) {

    const [authState, dispatch] = useReducer(authReducer, loadAuthState());

    useEffect(() => {
        storeAuthState(authState);
    }, [authState]);


    return <AuthContext.Provider value={{...authState, dispatch}}>
        {children}
    </AuthContext.Provider>
}