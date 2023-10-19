package net.okur.reagobs.service;

import net.okur.reagobs.entity.User;

import java.util.List;

public interface UserService {

  List<User> getAllUser();
  User createUser(User user);

  User updateUser(User user);

  void deleteUser(Long id);
}
