package pageObjects.nopcommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.nopcommerce.user.UserLoginPageUI;

public class UserLoginPageObject extends BasePage {
	private WebDriver driver;

	public UserLoginPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public UserHomePageObject clickToLoginButton() {
		waitForElementClickable(UserLoginPageUI.LOGIN_BUTTON);
		clickToElement(UserLoginPageUI.LOGIN_BUTTON);
//		return new HomePageObject(driver);
		return PageGeneratorManager.getUserHomePage(driver);
	}

	public String getErrorMessageAtEmailTextBox() {
		waitForElementVisible(UserLoginPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(UserLoginPageUI.EMAIL_ERROR_MESSAGE);
	}

	public void inputToEmailTextBox(String invalidEmail) {
		waitForElementVisible(UserLoginPageUI.EMAIL_TEXTBOX);
		sendKeyToElement(UserLoginPageUI.EMAIL_TEXTBOX, invalidEmail);
	}

	public String getLoginErrorMessage() {
		waitForElementVisible(UserLoginPageUI.LOGIN_ERROR_MESSAGE);
		return getElementText(UserLoginPageUI.LOGIN_ERROR_MESSAGE);
	}

	public void inputToPasswordTextBox(String incorrectPassword) {
		waitForElementVisible(UserLoginPageUI.PASSWORD_TEXTBOX);
		sendKeyToElement(UserLoginPageUI.PASSWORD_TEXTBOX, incorrectPassword);
	}
	
	public void loginAsUser(String userEmail, String userPassword) {
		inputToEmailTextBox(userEmail);
		inputToPasswordTextBox(userPassword);
		clickToLoginButton();
	}
}
