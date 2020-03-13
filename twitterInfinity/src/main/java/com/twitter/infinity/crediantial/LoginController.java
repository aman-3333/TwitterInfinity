package com.twitter.infinity.crediantial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.infinity.crediantial.dao.LoginCrediantialDao;
import com.twitter.infinity.vo.SignupVo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/login")
public class LoginController {

@Autowired
LoginCrediantialDao loginCrediantialDao;
	
  @PostMapping(value="/signup")
  @ResponseBody	
  public Boolean signup(@RequestBody SignupVo signupVo )
  {
	return loginCrediantialDao.signup(signupVo);
  }

  @PostMapping(value="/validateUser")
  @ResponseBody	
  public Boolean validateUser(@RequestBody SignupVo signupVo )
  {
	 return loginCrediantialDao.validateUser(signupVo);
  }
  
  @PostMapping(value="/resetUserPassword")
  @ResponseBody	
  public Boolean resetUserPassword(@RequestBody SignupVo signupVo )
  {
	 return loginCrediantialDao.resetUserPassword(signupVo);
  }
   
 
}
