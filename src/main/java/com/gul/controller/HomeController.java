package com.gul.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gul.entity.User;
import com.gul.repo.UserRepository;

@Controller
public class HomeController {

	@Autowired
	UserRepository userRepo;

	@GetMapping("/")
	public ModelAndView login(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = checkCookie(request);
		if (user == null) {
			mav.addObject("user", new User());
			mav.setViewName("login");
		} else {
			user = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
			if (user != null) {
				session.setAttribute("username", user.getEmail());
				mav.addObject("msg","using Cookies");
				mav.setViewName("success");
			} else {
				mav.addObject("user", user);
				mav.setViewName("login");
				mav.addObject("msg", "User Name or Password from Cookie");
			}
		}
		System.out.println("Login Called:");
		return mav;
	}

	@PostMapping("authenticate")
	public ModelAndView authenticate(@ModelAttribute("user") User user, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		user = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		String rm = request.getParameter("remember");
//		Cookies 
		System.out.println(rm);
		if (user != null) {
			if (rm != null) {
				Cookie ckUsername = new Cookie("username", user.getEmail());
				ckUsername.setMaxAge(30);
				response.addCookie(ckUsername);
				Cookie ckPassword = new Cookie("password", user.getPassword());
				ckUsername.setMaxAge(30);
				response.addCookie(ckPassword);
			}
			mav.setViewName("success");
		} else {
			mav.setViewName("login");
			mav.addObject("msg", "User Name or Password is Invalid");
		}
		System.out.println(user);
		return mav;
	}

	public User checkCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		User account = null;
		String username = "", password = "";
		for (Cookie ck : cookies) {
			if (ck.getName().equalsIgnoreCase("username"))
				username = ck.getValue();
			if (ck.getName().equalsIgnoreCase("password"))
				password = ck.getValue();
		}
		if (!username.isEmpty() && !password.isEmpty())
			account = new User(username, password);
		return account;
	}
}
