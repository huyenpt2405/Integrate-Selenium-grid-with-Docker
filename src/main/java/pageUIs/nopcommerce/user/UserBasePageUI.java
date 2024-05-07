package pageUIs.nopcommerce.user;

public class UserBasePageUI {
	public static final String CUSTOMER_INFO_LINK = "xpath=//div[contains(@class, 'account-navigation')]//a[text()='Customer info']";
	public static final String ADDRESS_LINK = "xpath=//div[contains(@class, 'account-navigation')]//a[text()='Addresses']";
	public static final String REWARD_POINT_LINK = "xpath=//div[contains(@class, 'account-navigation')]//a[text()='Reward points']";
	public static final String MY_PRODUCT_REVIEW_LINK = "xpath=//div[contains(@class, 'account-navigation')]//a[text()='My product reviews']";
	
	public static final String DYNAMIC_PAGE_AT_MY_ACCOUNT_AREA = "xpath=//div[contains(@class, 'account-navigation')]//a[text()='%s']";
	public static final String DYNAMIC_TEXTBOX_BY_ID = "xpath=//input[@id='%s']";
	public static final String DYNAMIC_BUTTON_BY_TEXT = "xpath=//button[text()='%s']";
}
