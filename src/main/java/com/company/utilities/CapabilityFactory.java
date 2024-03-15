package com.company.utilities;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class CapabilityFactory {
	public Capabilities capabilities;

	public Capabilities getCapabilities(String browser) {
		if (browser.equalsIgnoreCase(Constants.BROWSER_FIREFOX))
			capabilities = new FirefoxOptions();
		else if (browser.equalsIgnoreCase(Constants.BROWSER_CHROME))
			capabilities = new ChromeOptions();
		// Add more conditions for other browsers if needed
		return capabilities;
	}
}

