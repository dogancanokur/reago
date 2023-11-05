import http from '@/lib/http.js';

export function loadUsers(page = 0, size = 10) {
  const config = {
    params: { page, size },
  };
  return http.get('/api/v1/users/', config);
}

export function getUser(userId) {
  return http.get(`/api/v1/users/${userId}`);
}
