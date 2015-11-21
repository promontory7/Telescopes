
package com.xuhai.easeui.domain;

import com.easemob.chat.EMContact;

public class EaseUser extends EMContact {
    
    /**
     * 昵称首字母
     */
	protected String initialLetter;

	protected String avatar;

	private String userid;
	private String rename;
	private String idcard;
	private String email;
	private String signature;
	private String effect;
	private String level;
	private String pearl;
	private String country;
	private String province;
	private String city;
	private String area;
	private String addr;
	private String school;
	private String school_state;
	private int authentication;
	private String birthday;
	private String mobile;
	private int gender;
	private String token;
	private String last_login_ip;
	private String register_type;
	private String created_at;

	private String role_id;
	private String role_name;
	private String role_description;
	public EaseUser(){

	}

	public EaseUser(String username){
	    this.username = username;
	}

	public String getInitialLetter() {
		return initialLetter;
	}

	public void setInitialLetter(String initialLetter) {
		this.initialLetter = initialLetter;
	}


	public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

	public String getUserId() {
		return userid;
	}

	public void setUserId(String id) {
		this.userid = id;
	}

	public String getRename() {
		return rename;
	}

	public void setRename(String rename) {
		this.rename = rename;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPearl() {
		return pearl;
	}

	public void setPearl(String pearl) {
		this.pearl = pearl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchool_state() {
		return school_state;
	}

	public void setSchool_state(String school_state) {
		this.school_state = school_state;
	}

	public int getAuthentication() {
		return authentication;
	}

	public void setAuthentication(int authentication) {
		this.authentication = authentication;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLast_login_ip() {
		return last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public String getRegister_type() {
		return register_type;
	}

	public void setRegister_type(String register_type) {
		this.register_type = register_type;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getRole_description() {
		return role_description;
	}

	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}




	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof EaseUser)) {
			return false;
		}
		return getUsername().equals(((EaseUser) o).getUsername());
	}

	@Override
	public String toString() {
	 return "id="+this.userid+"  name="+this.username+ "   nickname="+this.nick+"   rename="+this.rename
			 +"   idcard="+this.idcard+"   email="+this.email+"   signature="+this.signature
			 +"   effect+"+this.effect+"   level="+this.level+"   pearl="+this.pearl+"   country="+this.country
			 +"   province="+this.province+"   city="+this.city+"   area="+this.area+"   addr="+this.addr
			 +"   school="+this.school+"   schoolstate="+this.school_state+"   authentication="+this.authentication
			 +"   avater="+this.avatar+"   birthday="+this.birthday+"   mobile="+this.mobile+"   gender="+this.gender
			 +"   token ="+this.token+"   last_login_ip="+this.last_login_ip ;
	}
}
