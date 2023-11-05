import { createContext, useContext, useEffect, useReducer } from 'react';
import { loadAuthState, storeAuthState } from '@/state/storage.js';
import { setToken } from '@/lib/http.js';

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
      setToken(action.data.token);
      return action.data.user;
    }
    case 'logoutSuccess': {
      setToken(null);
      return { id: 0 };
    }
    case 'user-update-success': {
      return {
        ...authState,
        username: action.data.username,
        email: action.data.email,
        name: action.data.name,
        image: action.data.image,
      };
    }
    default:
      throw new Error(`unknown action: ${action.type}`);
  }
};

export function AuthenticationContext({ children }) {
  const [authState, dispatch] = useReducer(authReducer, loadAuthState());

  useEffect(() => {
    storeAuthState(authState);
  }, [authState]);

  return (
    <AuthContext.Provider value={{ ...authState }}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthContext.Provider>
  );
}
