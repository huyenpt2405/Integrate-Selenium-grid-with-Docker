package pageObjects.nopcommerce.user;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.nopcommerce.user.UserRegisterPageUI;

public class UserRegisterPageObject extends BasePage {
	WebDriver driver;

	public UserRegisterPageObject(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void clickToRegisterButton() {
		waitForElementClickable(UserRegisterPageUI.REGISTER_BUTTON);
		clickToElement(UserRegisterPageUI.REGISTER_BUTTON);
	}

	public void inputToFirstNameTextBox(String string) {
		waitForElementVisible(UserRegisterPageUI.FIRST_NAME_TEXTBOX);
		sendKeyToElement(UserRegisterPageUI.FIRST_NAME_TEXTBOX, string);
	}

	public void inputToLastNameTextBox(String string) {
		waitForElementVisible(UserRegisterPageUI.LAST_NAME_TEXTBOX);
		sendKeyToElement(UserRegisterPageUI.LAST_NAME_TEXTBOX, string);
	}
	
	public void inputToEmailTextBox(String string) {
		waitForElementVisible(UserRegisterPageUI.EMAIL_TEXTBOX);
		sendKeyToElement(UserRegisterPageUI.EMAIL_TEXTBOX, string);
	}

	public void inputToPasswordTextBox(String string) {
		waitForElementVisible(UserRegisterPageUI.PASSWORD_TEXTBOX);
		sendKeyToElement(UserRegisterPageUI.PASSWORD_TEXTBOX, string);
	}

	public void inputToConfirmPasswordTextBox(String string) {
		waitForElementVisible(UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX);
		sendKeyToElement(UserRegisterPageUI.CONFIRM_PASSWORD_TEXTBOX, string);
	}


	public String getErrorMessageAtFirstNameTextBox() {
		waitForElementVisible(UserRegisterPageUI.FIRST_NAME_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.FIRST_NAME_ERROR_MESSAGE);
	}
	
	public String getErrorMessageAtLastNameTextBox() {
		waitForElementVisible(UserRegisterPageUI.LAST_NAME_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.LAST_NAME_ERROR_MESSAGE);
	}
	
	public String getErrorMessageAtPasswordTextBox() {
		waitForElementVisible(UserRegisterPageUI.PASSWORD_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.PASSWORD_ERROR_MESSAGE);
	}

	public String getErrorMessageAtEmailTextBox() {
		waitForElementVisible(UserRegisterPageUI.EMAIL_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.EMAIL_ERROR_MESSAGE);
	}

	public String getErrorMessageAtConfirmPasswordTextBox() {
		waitForElementVisible(UserRegisterPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.CONFIRM_PASSWORD_ERROR_MESSAGE);
	}

	public String getRegisterSuccessMessage() {
		waitForElementVisible(UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
		return getElementText(UserRegisterPageUI.REGISTER_SUCCESS_MESSAGE);
	}

	public String getErrorExistingEmailMessage() {
		waitForElementVisible(UserRegisterPageUI.EXISTING_EMAIL_ERROR_MESSAGE);
		return getElementText(UserRegisterPageUI.EXISTING_EMAIL_ERROR_MESSAGE);
	}
	
	public UserHomePageObject clickToLogoutLink() {
		waitForElementClickable(UserRegisterPageUI.LOGOUT_LINK);
		clickToElement(UserRegisterPageUI.LOGOUT_LINK);
//		return new HomePageObject(driver);
		return PageGeneratorManager.getUserHomePage(driver);
	}
}
