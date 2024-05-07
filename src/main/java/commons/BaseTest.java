package commons;
import java.net.URL;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;

import utilities.Browser;
import utilities.GlobalConstants;

public class BaseTest {
	protected String projectPath = GlobalConstants.PROJECT_PATH;
	protected ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
    public String remote_url = "http://localhost:4444/wd/hub";
	
	protected BaseTest() {
	}
	
	protected WebDriver openMultiBrowser(String browserName) throws Exception {
		Browser browser = Browser.valueOf(browserName.toUpperCase());
		if (browser == Browser.FIREFOX) {
			FirefoxOptions options = new FirefoxOptions();
	        options.addArguments("--start-maximized");
	        driver.set(new RemoteWebDriver(new URL(remote_url), options));
		} else 
		if (browser == Browser.CHROME) {
			ChromeOptions options = new ChromeOptions();
	        options.addArguments("--start-maximized");
	        driver.set(new RemoteWebDriver(new URL(remote_url), options));
		} else {
			throw new RuntimeException("Cannot find browser");
		}
		
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.get().get(GlobalConstants.USER_PORTAL_URL);
		return driver.get();
	}
	
	public WebDriver getWebdriver() {
		return this.driver.get();
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		if (driver != null) {
			driver.get().quit();
		}
	}
	
	public int generateFakeNumber() {
		Random random = new Random();
		return random.nextInt(99999);
	}
}
