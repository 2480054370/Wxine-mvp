package com.example.wxine_mvp.data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = -3936141646843762694L;
	private String id;
	private String account;
	private String code;
	private String email;
	private String mobile;
	private String password;
	private String security;
	private String type;
	private String status;
	private Integer rank;
	private String certcode;
	private String certmobile;
	private String certemail;
	private Integer followcount;
	private Integer fanscount;
	private Integer infocount;
	private String name;
	private String logo;
	private String color;
	private String image;
	private String homepage;
	private String intro;
	private String nickname;
	private String sex;
	private Date birthday;
	private String marriage;
	private String bloodtype;
	private String hometown;
	private String edulevel;
	private String religion;
	private String hobby;
	private String mood;
	private String banner;
	private String themecode;
	private Timestamp checktheme;
	private Timestamp ctime;
	private Timestamp logintime;// 登录时间
	private String verifycode;
	private Timestamp validtime;

	private String icp;
	private String certlicense;
	private String comtype;
	private String purchase;
	private String sell;
	private String industry;
	private String bmodel;
	private String regcapital;
	private String founded;
	private String registry;
	private String legrep;
	private String salesarea;
	private String customers;
	private String turnover;
	private String brand;
	private String services;
	private Integer empnum;

	private String district;
	private String address;
	private Double latitude;
	private Double longitude;
	private String postcode;
	private String cmobile;
	private String phone;
	private String fax;
	private String bnhours;



	private String mypermission;// 当前登录用户在群组中的权限
	private String mystatus;// 当前登录用户在群组中的状态

	private String positionx;// 地理位置
	private String positiony;// 地理位置
	private String positionz;// 地理位置
	private String ip;// 登录IP
	private String isme;// 是否当前用户

	private String sessionid;



	public String getMypermission() {
		return mypermission;
	}

	public void setMypermission(String mypermission) {
		this.mypermission = mypermission;
	}

	public String getMystatus() {
		return mystatus;
	}

	public void setMystatus(String mystatus) {
		this.mystatus = mystatus;
	}

	public String getPositionx() {
		return positionx;
	}

	public void setPositionx(String positionx) {
		this.positionx = positionx;
	}

	public String getPositiony() {
		return positiony;
	}

	public void setPositiony(String positiony) {
		this.positiony = positiony;
	}

	public String getPositionz() {
		return positionz;
	}

	public void setPositionz(String positionz) {
		this.positionz = positionz;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIsme() {
		return isme;
	}

	public void setIsme(String isme) {
		this.isme = isme;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	/** default constructor */
	public User() {
	}

	/** for redis constructor */
	public User(String id, String account) {
		this.id = id;
		this.account = account;
	}

	/** minimal constructor */
	public User(String id, String account, String type, String status, String password, String certcode,
				String certmobile, String certemail, Timestamp ctime) {
		this.id = id;
		this.account = account;
		this.type = type;
		this.status = status;
		this.password = password;
		this.certcode = certcode;
		this.certmobile = certmobile;
		this.certemail = certemail;
		this.ctime = ctime;
	}

	public User(String id, String account, String code, String email, String mobile, String password,
				String security, String type, String status, Integer rank, String certcode, String certmobile,
				String certemail, Integer followcount, Integer fanscount, Integer infocount, String name,
				String logo, String color, String image, String homepage, String intro, String nickname,
				String sex, Date birthday, String marriage, String bloodtype, String hometown, String edulevel,
				String religion, String hobby, String mood, String banner, String themecode,
				Timestamp checktheme, Timestamp ctime, String verifycode, Timestamp validtime, String icp,
				String certlicense, String comtype, String purchase, String sell, String industry, String bmodel,
				String regcapital, String founded, String registry, String legrep, String salesarea,
				String customers, String turnover, String brand, String services, Integer empnum,
				String district, String address, Double latitude, Double longitude, String postcode,
				String cmobile, String phone, String fax, String bnhours) {
		this.id = id;
		this.account = account;
		this.code = code;
		this.email = email;
		this.mobile = mobile;
		this.password = password;
		this.security = security;
		this.type = type;
		this.status = status;
		this.rank = rank;
		this.certcode = certcode;
		this.certmobile = certmobile;
		this.certemail = certemail;
		this.followcount = followcount;
		this.fanscount = fanscount;
		this.infocount = infocount;
		this.name = name;
		this.logo = logo;
		this.color = color;
		this.image = image;
		this.homepage = homepage;
		this.intro = intro;
		this.nickname = nickname;
		this.sex = sex;
		this.birthday = birthday;
		this.marriage = marriage;
		this.bloodtype = bloodtype;
		this.hometown = hometown;
		this.edulevel = edulevel;
		this.religion = religion;
		this.hobby = hobby;
		this.mood = mood;
		this.banner = banner;
		this.themecode = themecode;
		this.checktheme = checktheme;
		this.ctime = ctime;
		this.verifycode = verifycode;
		this.validtime = validtime;
		this.icp = icp;
		this.certlicense = certlicense;
		this.comtype = comtype;
		this.purchase = purchase;
		this.sell = sell;
		this.industry = industry;
		this.bmodel = bmodel;
		this.regcapital = regcapital;
		this.founded = founded;
		this.registry = registry;
		this.legrep = legrep;
		this.salesarea = salesarea;
		this.customers = customers;
		this.turnover = turnover;
		this.brand = brand;
		this.services = services;
		this.empnum = empnum;
		this.district = district;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postcode = postcode;
		this.cmobile = cmobile;
		this.phone = phone;
		this.fax = fax;
		this.bnhours = bnhours;

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCertcode() {
		return this.certcode;
	}

	public void setCertcode(String certcode) {
		this.certcode = certcode;
	}

	public String getCertmobile() {
		return this.certmobile;
	}

	public void setCertmobile(String certmobile) {
		this.certmobile = certmobile;
	}

	public String getCertemail() {
		return this.certemail;
	}

	public void setCertemail(String certemail) {
		this.certemail = certemail;
	}

	public Integer getFollowcount() {
		return followcount;
	}

	public void setFollowcount(Integer followcount) {
		this.followcount = followcount;
	}

	public Integer getFanscount() {
		return fanscount;
	}

	public void setFanscount(Integer fanscount) {
		this.fanscount = fanscount;
	}

	public Integer getInfocount() {
		return infocount;
	}

	public void setInfocount(Integer infocount) {
		this.infocount = infocount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getBloodtype() {
		return this.bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public String getHometown() {
		return this.hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getEdulevel() {
		return this.edulevel;
	}

	public void setEdulevel(String edulevel) {
		this.edulevel = edulevel;
	}

	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getMood() {
		return this.mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getThemecode() {
		return themecode;
	}

	public void setThemecode(String themecode) {
		this.themecode = themecode;
	}

	public Timestamp getChecktheme() {
		return checktheme;
	}

	public void setChecktheme(Timestamp checktheme) {
		this.checktheme = checktheme;
	}

	public Timestamp getCtime() {
		return this.ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public Timestamp getLogintime() {
		return logintime;
	}

	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}

	public String getVerifycode() {
		return this.verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public Timestamp getValidtime() {
		return this.validtime;
	}

	public void setValidtime(Timestamp validtime) {
		this.validtime = validtime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIcp() {
		return this.icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	public String getCertlicense() {
		return this.certlicense;
	}

	public void setCertlicense(String certlicense) {
		this.certlicense = certlicense;
	}
	
	public String getComtype() {
		return comtype;
	}

	public void setComtype(String comtype) {
		this.comtype = comtype;
	}

	public String getPurchase() {
		return this.purchase;
	}

	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}

	public String getSell() {
		return this.sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getBmodel() {
		return this.bmodel;
	}

	public void setBmodel(String bmodel) {
		this.bmodel = bmodel;
	}

	public String getRegcapital() {
		return this.regcapital;
	}

	public void setRegcapital(String regcapital) {
		this.regcapital = regcapital;
	}

	public String getFounded() {
		return this.founded;
	}

	public void setFounded(String founded) {
		this.founded = founded;
	}
	
	public String getRegistry() {
		return this.registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getLegrep() {
		return this.legrep;
	}

	public void setLegrep(String legrep) {
		this.legrep = legrep;
	}

	public String getSalesarea() {
		return this.salesarea;
	}

	public void setSalesarea(String salesarea) {
		this.salesarea = salesarea;
	}

	public String getCustomers() {
		return this.customers;
	}

	public void setCustomers(String customers) {
		this.customers = customers;
	}
	
	public String getTurnover() {
		return this.turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getServices() {
		return this.services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public Integer getEmpnum() {
		return this.empnum;
	}

	public void setEmpnum(Integer empnum) {
		this.empnum = empnum;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCmobile() {
		return this.cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getBnhours() {
		return bnhours;
	}

	public void setBnhours(String bnhours) {
		this.bnhours = bnhours;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



}