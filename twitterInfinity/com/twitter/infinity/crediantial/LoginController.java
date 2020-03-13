package com.twitter.infinity.crediantial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.infinity.crediantial.dao.LoginCrediantialDao;
import com.twitter.infinity.vo.SignupVo;

@Controller
//@RequestMapping("/login")
public class LoginController {

@Autowired
LoginCrediantialDao loginCrediantialDao;
	
  @RequestMapping(value="/signup",method=RequestMethod.POST)
  @ResponseBody	
  public Boolean signup(@RequestBody SignupVo signupVo )
  {
	return loginCrediantialDao.signup(signupVo);
  }

  @GetMapping(value="/validateUser")
  @ResponseBody	
  public Boolean validateUser(@RequestBody SignupVo signupVo )
  {
	 return loginCrediantialDao.validateUser(signupVo);
  }
  
  @RequestMapping(value="/test",method=RequestMethod.GET)
  @ResponseBody	
  public String signup1()
  {
	return "hello";
  }
  
  
	@RequestMapping("/hi")
	public @ResponseBody String  test()
	{
		return "Hello world!!!!";
	}	

  

}
