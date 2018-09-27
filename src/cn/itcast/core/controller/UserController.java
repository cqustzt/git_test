package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.pojo.User;
import cn.itcast.core.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/insertUI.action")
	public String insertUI(){
    	return "insert";
    }
	@RequestMapping("/saveUser.action")
    public String save(User user){
		userService.save(user);
    	return "success";
    }
	@RequestMapping("list")
	public String listAll(Model model){
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return "list";
	}
}
