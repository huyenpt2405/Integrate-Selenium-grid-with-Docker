package commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageUIs.nopcommerce.user.UserBasePageUI;
import utilities.GlobalConstants;

public class BasePage {
	
	private WebDriver driver;
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void openPageUrl(String pageUrl) {
		driver.get(pageUrl);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public void backToPage() {
		driver.navigate().back();
	}

	public void forwardToPage() {
		driver.navigate().forward();
	}

	public void refreshCurrentPage() {
		driver.navigate().refresh();
	}

	public Alert waitForAlertPresence() {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert() {
		waitForAlertPresence().accept();
	}

	public void cancelAlert() {
		waitForAlertPresence().dismiss();
	}

	public String getAlertText() {
		return waitForAlertPresence().getText();
	}

	public void sendKeysToAlert(String textValue) {
		waitForAlertPresence().sendKeys(textValue);
	}

	public void switchToWindowByID(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(parentID);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String title) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runWindow : allWindows) {
			driver.switchTo().window(runWindow);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowWithoutParent(String parentID) {
		Set<String> allWindows = driver.getWindowHandles();

		for (String runwindows : allWindows) {
			if (!runwindows.equals(parentID)) {
				driver.switchTo().window(runwindows);
				driver.close();
			}
		}

		driver.switchTo().window(parentID);
	}

	public WebElement getWebElement(String locator) {
		return driver.findElement(getByLocator(locator));
	}

	public List<WebElement> getWebElements(String locator) {
		return driver.findElements(getByLocator(locator));
	}
	
	public void clickToElement(String locator) {
		getWebElement(locator).click();
	}

	public void clickToElement(String locator, String... dynamicValues) {
		getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).click();
	}

	public void sendKeyToElement(String locator, String textValue) {
		WebElement element = getWebElement(locator);
		element.clear();
		element.sendKeys(textValue);
	}

	public void sendKeyToElement(String locator, String textValue, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicLocator(locator, (String[]) dynamicValues));
		element.clear();
		element.sendKeys(textValue);
	}
	
	public String getElementText(String locator) {
		return getWebElement(locator).getText();
	}

	public String getElementText(String locator, String... dynamicValues) {
		return getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).getText();
	}

	public void selectItemInDefaultDropdown(String locator, String textItem) {
		Select select = new Select(getWebElement(locator));
		select.selectByVisibleText(textItem);
	}
	
	public void selectItemInDefaultDropdown(String locator, String textItem, String...dynamicValues) {
		Select select = new Select(getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)));
		select.selectByValue(textItem);
	}

	public String getSelectedItemDefaultDropdown(String locator) {
		Select select = new Select(getWebElement(locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public String getSelectedItemDefaultDropdown(String locator, String... dynamicValues) {
		Select select = new Select(getWebElement(getDynamicLocator(locator, dynamicValues)));
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(String locator) {
		Select select = new Select(getWebElement(locator));
		return select.isMultiple();
	}
	
	public boolean isDropdownMultiple(String locator, String... dynamicValues) {
		Select select = new Select(getWebElement(getDynamicLocator(locator, dynamicValues)));
		return select.isMultiple();
	}

	public void selectItemInCustomDropdown(String parentLocator, String childLocator,
			String expectedItem) {
		getWebElement(parentLocator).click();
		sleepInSecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		List<WebElement> allItems = explicitWait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childLocator)));

		for (WebElement item : allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				JavascriptExecutor jsExcutor = (JavascriptExecutor) driver;
				jsExcutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);

				item.click();
				sleepInSecond(1);
				break;
			}
		}
	}

	public String getElementAttribute(String locator, String attributeName) {
		return getWebElement(locator).getAttribute(attributeName);
	}
	
	public String getElementAttribute(String locator, String attributeName, String... dynamicValues) {
		return getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).getAttribute(attributeName);
	}

	public String getElementCssValue(String locator, String propertyName) {
		return getWebElement(locator).getCssValue(propertyName);
	}
	
	public String getElementCssValue(String locator, String propertyName, String... dynamicValues) {
		return getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).getCssValue(propertyName);
	}

	public String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex().toUpperCase();
	}

	public int getElementSize(String locator) {
		return getWebElements(locator).size();
	}
	
	public int getElementSize(String locator, String... dynamicValues) {
		return getWebElements(getDynamicLocator(locator, (String[]) dynamicValues)).size();
	}

	public void checkToDefaultRadioCheckbox(String locator) {
		WebElement element = getWebElement(locator);

		if (!element.isSelected()) {
			element.click();
		}
	}

	public void checkToDefaultRadioCheckbox(String locator, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicLocator(locator, dynamicValues));
		
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToDefaultCheckbox(String locator) {
		WebElement element = getWebElement(locator);

		if (element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckbox(String locator, String... dynamicValues) {
		WebElement element = getWebElement(getDynamicLocator(locator, dynamicValues));
		
		if (element.isSelected()) {
			element.click();
		}
	}

	
	public boolean isElementDisplayed(String locator) {
		return getWebElement(locator).isDisplayed();
	}
	
	public boolean isElementDisplayed(String locator, String... dynamicValues) {
		return getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).isDisplayed();
	}
	
	public void overrideImplicitTimeout(long timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
	}
	
	public boolean isElementUndisplayed(String locator) {
		overrideImplicitTimeout(shortTimeout);
		List<WebElement> elements = getWebElements(locator);
		overrideImplicitTimeout(longTimeout);
		
		if (elements.size() == 0) {
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementUndisplayed(String locator, String... dynamicValues) {
		overrideImplicitTimeout(shortTimeout);
		List<WebElement> elements = getWebElements(getDynamicLocator(locator, dynamicValues));
		overrideImplicitTimeout(longTimeout);
		
		if (elements.size() == 0) {
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isElementEnabled(String locator) {
		return getWebElement(locator).isEnabled();
	}
	
	public boolean isElementEnabled(String locator, String... dynamicValues) {
		return getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)).isEnabled();
	}

	public boolean isElementSelected(String locator) {
		return getWebElement(locator).isSelected();
	}
	
	public boolean isElementSelected(String locator, String...dynamicValues) {
		return getWebElement(getDynamicLocator(locator, dynamicValues)).isSelected();
	}

	public void switchToFrameIframe(String locator) {
		driver.switchTo().frame(getWebElement(locator));
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public void hoverMouseToElement(String locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(locator)).perform();
	}
	
	public void pressKeyToElement(String locator, Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(locator), key).perform();
	}
	
	public void pressKeyToElement(String locator, Keys key, String...dynamicValues) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(getDynamicLocator(locator, (String[]) dynamicValues)), key).perform();
	}

	public void scrollToBottomPage() {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void highlightElement(String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);
	}

	public void clickToElementByJS(String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(locator));
	}
	
	public WebElement getShadowDOM(String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot;", getWebElement(locator));
		return element;
	}

	public void scrollToElement(String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(locator));
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(locator));
	}


	public String getElementValidationMessage(String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
				getWebElement(locator));
	}

	public void waitForElementVisible(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locator)));
	}
	
	public void waitForElementVisible(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicLocator(locator, (String[]) dynamicValues))));
	}

	public void waitForAllElementsVisible(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locator)));
	}
	
	public void waitForAllElementsVisible(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicLocator(locator, (String[]) dynamicValues))));
	}

	public void waitForElementInvisible(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
	}
	
	public void waitForElementInvisible(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicLocator(locator, (String[]) dynamicValues))));
	}
	
	/*
	 * Wait for element undisplay and override implicit timeout
	 */
	public void waitForElementUndisplayed(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(shortTimeout));
		overrideImplicitTimeout(shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
		overrideImplicitTimeout(longTimeout);
	}
	
	public void waitForElementUndisplayed(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(shortTimeout));
		overrideImplicitTimeout(shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicLocator(locator, (String[]) dynamicValues))));
		overrideImplicitTimeout(longTimeout);
	}

	public void waitForAllElementsInvisible(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getWebElements(locator)));
	}
	
	public void waitForAllElementsInvisible(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getWebElements(getDynamicLocator(locator, (String[]) dynamicValues))));
	}
	
	public void waitForElementClickable(String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locator)));
	}
	
	public void waitForElementClickable(String locator, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(longTimeout));
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicLocator(locator, (String[]) dynamicValues))));
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private By getByLocator(String locator) {
		By by = null;
		String locatorLowerCase = locator.toLowerCase();
		
		if (locatorLowerCase.startsWith("id=")) {
			by = By.id(locator.substring(3));
		} else if (locatorLowerCase.startsWith("class=")) {
			by = By.className(locator.substring(6));
		} else if (locatorLowerCase.startsWith("name")) {
			by = By.name(locator.substring(5));
		} else if (locatorLowerCase.startsWith("css=")) {
			by = By.cssSelector(locator.substring(4));
		} else if (locatorLowerCase.startsWith("xpath=")) {
			by = By.xpath(locator.substring(6));
		} else {
			throw new RuntimeException("Locator type is not supported");
		}
		
		return by;
	}
	
	private String getDynamicLocator(String locator, String... params) {
		locator = String.format(locator, (Object[]) params);
		return locator;
	}
	
	
	public Set<Cookie> getAllCooies() {
		return driver.manage().getCookies();
	}

	public void setCookies(Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		
		sleepInSecond(2);
	}
	
	public void inputToTextboxByID(String value, String texboxID) {
		waitForElementVisible(UserBasePageUI.DYNAMIC_TEXTBOX_BY_ID, texboxID);
		sendKeyToElement(UserBasePageUI.DYNAMIC_TEXTBOX_BY_ID, value, texboxID);
	}
	
	public void clickToButtonByText(String buttonText) {
		waitForElementClickable(UserBasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
		clickToElement(UserBasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
	}
	
	private ArrayList<String> getStringByLocator(String locator) {
		waitForAllElementsVisible(locator);
		List<WebElement> elements = getWebElements(locator);
		
		ArrayList<String> stringValues = new ArrayList<String>();
		for (WebElement element : elements) {
			stringValues.add(element.getText());
		}
		return stringValues;
	}

	public boolean isDataStringSortedAscending(String locator) {
		ArrayList<String> arrayList = getStringByLocator(locator);
		ArrayList<String> sortedList = new ArrayList<String>(); 

		for (String name : arrayList) {
			sortedList.add(name);
		}
		
		Collections.sort(sortedList);

		return sortedList.equals(arrayList);
	}
	
	public boolean isDataStringSortedDescending(String locator) {
		ArrayList<String> arrayList = getStringByLocator(locator);
		ArrayList<String> sortedList = new ArrayList<String>(); 

		for (String name : arrayList) {
			sortedList.add(name);
		}
		
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDataFloatSortedAscending(String locator) {
		ArrayList<Float> arrayList = getFloatValueByLocator(locator);
		ArrayList<Float> sortedList = new ArrayList<Float>(); 
		for (float number : arrayList) {
			sortedList.add(number);
		}
		
		Collections.sort(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDataFloatSortedDescending(String locator) {
		ArrayList<Float> arrayList = getFloatValueByLocator(locator);
		ArrayList<Float> sortedList = new ArrayList<Float>(); 
		for (float number : arrayList) {
			sortedList.add(number);
		}
		
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	private ArrayList<Float> getFloatValueByLocator(String locator) {
		waitForAllElementsVisible(locator);
		List<WebElement> elements = getWebElements(locator);
		
		ArrayList<Float> floatValues = new ArrayList<Float>();
		for (WebElement element : elements) {
			String priceText = element.getText().replace("$", "").trim();
			floatValues.add(Float.parseFloat(priceText));
		}
		return floatValues;
	}
	
	
	private ArrayList<Date> getDateTimeValueByLocator(String locator) {
		waitForAllElementsVisible(locator);
		List<WebElement> elements = getWebElements(locator);
		
		ArrayList<Date> dateValues = new ArrayList<Date>();
		for (WebElement element : elements) {
			dateValues.add(convertStringToDate(element.getText()));
		}
		return dateValues;
	}
	
	public boolean isDateSortedAscending(String locator) {
		ArrayList<Date> arrayList = getDateTimeValueByLocator(locator);
		ArrayList<Date> sortedList = new ArrayList<Date>(); 
		for (Date date : arrayList) {
			sortedList.add(date);
		}
		
		Collections.sort(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDateSortedDescending(String locator) {
		ArrayList<Date> arrayList = getDateTimeValueByLocator(locator);
		ArrayList<Date> sortedList = new ArrayList<Date>(); 
		for (Date date : arrayList) {
			sortedList.add(date);
		}
		
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public Date convertStringToDate(String dateInString) {
		dateInString = dateInString.replace(",", "");
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
}
