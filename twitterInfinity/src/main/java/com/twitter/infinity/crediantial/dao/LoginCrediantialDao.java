package com.twitter.infinity.crediantial.dao;

import org.springframework.stereotype.Repository;

import com.twitter.infinity.vo.SignupVo;

@Repository
public interface LoginCrediantialDao {
  public boolean signup(SignupVo signupVo);	
  public boolean validateUser(SignupVo signupVo);
  public boolean resetUserPassword(SignupVo signupVo);
}
