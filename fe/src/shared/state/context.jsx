import {createContext, useContext, useEffect, useReducer} from "react";
import {loadAuthState, storeAuthState} from "@/state/storage.js";

export const AuthContext = createContext();
export const AuthDispatchContext = createContext();

export function useAuthState() {
    return useContext(AuthContext);
}

export function useAuthDispatch() {
    return useContext(AuthDispatchContext);
}


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


    return <AuthContext.Provider value={{...authState}}>
        <AuthDispatchContext.Provider value={dispatch}>
            {children}
        </AuthDispatchContext.Provider>
    </AuthContext.Provider>
}