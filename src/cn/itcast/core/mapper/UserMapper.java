package cn.itcast.core.mapper;

import java.util.List;

import cn.itcast.core.pojo.User;

public interface UserMapper {

	public void saveUser(User user);

	public List<User> findAll();

}
