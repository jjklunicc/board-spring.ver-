package com.goodee.mvcboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@PostMapping("/login")
	public String login(HttpSession session,
						@RequestParam(name="memberId") String memberId,
						@RequestParam(name="memberPw") String memberPw) {
			// service(memberId, memberPw) -> mapper -> 로그인 성공 유무 반환
			
			// 로그인 성공시
			
			session.setAttribute("", "");
			
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
}
