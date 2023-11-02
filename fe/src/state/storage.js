export function storeAuthState(auth) {
    localStorage.setItem('auth', JSON.stringify(auth));
}

export function loadAuthState() {
    const defaultState = {id: 0}
    let authStateInStorage = localStorage.getItem('auth');
    if (!authStateInStorage) return defaultState;
    try {
        return JSON.parse(authStateInStorage);
    } catch (err) {
        return defaultState;
    }
}

export function storeToken(token) {
    if (token) {
        localStorage.setItem('token', JSON.stringify(token));
    } else {
        localStorage.removeItem('token');
    }
}

export function loadToken() {
    try {
        const tokenInString = JSON.parse(localStorage.getItem('token'));
        if (!tokenInString) {
            return null;
        }
        return tokenInString
    } catch (err) {
        return null;
    }

}