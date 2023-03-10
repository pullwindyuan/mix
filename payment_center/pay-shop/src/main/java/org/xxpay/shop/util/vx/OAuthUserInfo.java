package org.xxpay.shop.util.vx;

/**
 参数		描述
 openid		用户的唯一标识
 nickname	用户昵称
 sex			用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
 province	用户个人资料填写的省份
 city		普通用户个人资料填写的城市
 country		国家，如中国为CN
 headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
 privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
 unionid		只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
 */
public class OAuthUserInfo extends AccessToken {
	
	//oauth2.0
	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String openid;
	private String headimgurl;
	private String privilege;
	private String unionid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

