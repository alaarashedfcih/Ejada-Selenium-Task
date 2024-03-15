package com.ejada.tests;

import com.company.listeners.TestListener;
import com.company.pages.LogInPage;
import com.company.utilities.DriverHandler;
import org.testng.annotations.AfterMethod;

import java.net.MalformedURLException;

public class BaseTest extends TestListener
{
	DriverHandler driverHandler;
	LogInPage logInPage;

	public void setUp(String browser) throws MalformedURLException
	{
		driverHandler = DriverHandler.getInstance();
		driverHandler.createDriver(browser);
		driverHandler.getDriver().manage().window().maximize();
		driverHandler.gotoApplicationHomePage();
		logInPage = new LogInPage(driverHandler.getDriver());
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown()
	{
		driverHandler.getDriver().quit();
		driverHandler.setDriver(null);
	}
}
