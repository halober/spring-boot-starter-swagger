package com.reger.swagger.properties;

public class Swagger2GroupProperties {

	/**
	 * api组的名字，会在swagger-ui的api下拉列表中显示
	 */
	private String groupName;
	/**
	 * api组的标题，会在swagger-ui的标题处描述
	 */
	private String title;
	/**
	 * api组的描述，会在swagger-ui的描述中显示
	 */
	private String description;
	/**
	 * 匹配到本组的api接口，匹配uri，可以用正则表达式匹配
	 */
	private String pathRegex;
	/**
	 * 匹配到的url在swagger中测试请求时加的url前缀
	 */
	private String pathMapping;
	/**
	 * api的版本号
	 */
	private String version;
	/**
	 * api服务条款的地址
	 */
	private String termsOfServiceUrl;
	/**
	 * 授权的协议
	 */
	private String license;
	/**
	 * 授权的协议的url
	 */
	private String licenseUrl;

	/**
	 * api联系信息
	 */
	private ContactI contact = new ContactI();

	public String getPathRegex() {
		return pathRegex;
	}

	public void setPathRegex(String pathRegex) {
		this.pathRegex = pathRegex;
	}

	public String getPathMapping() {
		return pathMapping;
	}

	public void setPathMapping(String pathMapping) {
		this.pathMapping = pathMapping;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}

	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	public ContactI getContact() {
		return contact;
	}

	public void setContact(ContactI contact) {
		this.contact = contact;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
