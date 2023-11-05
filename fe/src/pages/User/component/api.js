import http from '@/lib/http.js';

export function updateUser(body) {
  return http.put(`/api/v1/users/`, body);
}
