package com.company.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Objects;

@Slf4j
public class DriverHandler {
	private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

	public void createDriver(String browser) {
		WebDriver driver;
		switch (browser.toLowerCase()) {
			case Constants.BROWSER_CHROME -> {
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.merge(new CapabilityFactory().getCapabilities(Constants.BROWSER_CHROME));
				driver = new ChromeDriver(chromeOptions);
			}
			case Constants.BROWSER_FIREFOX -> {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.merge(new CapabilityFactory().getCapabilities(Constants.BROWSER_FIREFOX));
				driver = new FirefoxDriver(firefoxOptions);
			}
			default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
		setDriver(driver);
	}

	public static DriverHandler getInstance() {
		return new DriverHandler();
	}

	public void gotoApplicationHomePage() {
		Objects.requireNonNull(driverThread.get()).get(Constants.APPLICATION_HOST);
	}

	public WebDriver getDriver() {
		return driverThread.get();
	}

	public void setDriver(WebDriver driver) {
		driverThread.set(driver);
	}
}
