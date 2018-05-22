package com.learningcenter.miaosha.access;

import com.learningcenter.miaosha.model.MiaoShaUser;

public class UserContext {
	
	private static ThreadLocal<MiaoShaUser> userHolder = new ThreadLocal<MiaoShaUser>();
	
	public static void setUser(MiaoShaUser user) {
		userHolder.set(user);
	}
	
	public static MiaoShaUser getUser() {
		return userHolder.get();
	}

}
