import http from "@/lib/http.js";

export function signUp(body) {
    return http.post('/api/v1/users/', body);
}

export function login(body) {
    return http.post('/api/v1/auth', body);
}