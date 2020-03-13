package com.twitter.infinity.crediantial.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.twitter.infinity.crediantial.dao.LoginCrediantialDao;
import com.twitter.infinity.vo.SignupVo;

@Repository
public class LoginCrediantailDaoImpl implements LoginCrediantialDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean signup(SignupVo signupVo) {
		boolean flag=false;
		try {
			String query="insert ignore into tbl_login_detail(userName,password) values(?,?)";
			System.out.println(query);
			Object[] obj=new Object[]{signupVo.getUserName(),signupVo.getPassword()};
			jdbcTemplate.update(query, obj);
			flag=true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean validateUser(SignupVo signupVo) {
		boolean flag=false;
		try {
			String query="select count(*) from tbl_login_detail where userName=? and password=?";
			System.out.println(query);
			Object[] obj=new Object[]{signupVo.getUserName(),signupVo.getPassword()};
			int count=jdbcTemplate.queryForObject(query, obj, Integer.class);
			if(count>0)
			{
				return true;
			}
			else 
			{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean resetUserPassword(SignupVo signupVo) {
		boolean flag=false;
		try {
			String query="update tbl_login_detail set password=? where userName=?";
			System.out.println(query);
			Object[] obj=new Object[]{signupVo.getPassword(),signupVo.getUserName()};
			jdbcTemplate.update(query, obj);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
