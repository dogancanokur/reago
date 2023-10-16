package net.okur.reagobs.service;

import net.okur.reagobs.entity.User;

public interface UserService {

  User createUser(User user);

  User updateUser(User user);

  void deleteUser(Long id);
}
