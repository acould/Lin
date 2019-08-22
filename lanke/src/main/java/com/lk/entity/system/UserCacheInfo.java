package com.lk.entity.system;


import java.io.Serializable;
import java.util.List;

public class UserCacheInfo implements Serializable {
	private static final long serialVersionUID = 3161223461218374632L;
	
	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户归属信息
	 */
	private List<User> userRelations;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	static public UserCacheInfo cast2UserCache(User userInfo,
			List<User> userRelations) {
		if (userInfo == null) {
			return null;
		}

		UserCacheInfo userCacheInfo = new UserCacheInfo();
		userCacheInfo.setUserId(userInfo.getUSER_ID());
		userCacheInfo.setUserName(userInfo.getNAME());
		userCacheInfo.setUserRelations(userRelations);
		return userCacheInfo;
	}

	@Override
	public String toString() {
		return "UserCacheInfo [userId=" + userId + ", userName=" + userName
				+ ", userRelations=" + userRelations + "]";
	}

	public List<User> getUserRelations() {
		return userRelations;
	}

	public void setUserRelations(List<User> userRelations) {
		this.userRelations = userRelations;
	}
}
