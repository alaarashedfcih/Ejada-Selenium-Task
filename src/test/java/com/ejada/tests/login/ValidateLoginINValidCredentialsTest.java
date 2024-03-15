package com.ejada.tests.login;

import com.company.dataproviderobjects.LoginUsersData;
import com.company.pages.LogInPage;
import com.ejada.tests.BaseTest;
import com.company.utilities.DataProviderSource;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Objects;

public class ValidateLoginINValidCredentialsTest extends BaseTest
{
	WebDriver driver;
	LogInPage logInPage;
	SoftAssert softAssert;

	@BeforeMethod(alwaysRun = true)
	@Parameters(value = {"browser"})
	public synchronized void setUp(String browser, Method method, Object[] testData, ITestContext ctx)
	throws MalformedURLException
	{

		if (!Objects.equals(((LoginUsersData) testData[0]).getTestCaseName(), ""))
		{
			ctx.setAttribute(method.getName(),
					"On " + browser + ": " + ((LoginUsersData) testData[0]).getTestCaseName());
		}
		else
		{
			ctx.setAttribute(method.getName(), "On " + browser + ": "
					+ "TC #00: Login with InValid Credentials");
		}
		super.setUp(browser);
		driver = getDriver();
	}

	@Test(alwaysRun = true, dataProvider = "LoginUsersDataFeed", dataProviderClass = DataProviderSource.class)
	public void login_InValidCredentialsTest(LoginUsersData data)
	{
		softAssert = new SoftAssert();
		logInPage = new LogInPage(driver);
		logInPage.setSignInUserName(data.getUserName());
		logInPage.setSignInPassword(data.getPassword());
		logInPage.clickOnLoginBtn();
		softAssert.assertEquals(data.getErrorMessage(),logInPage.getErrorMessage());
		softAssert.assertTrue(logInPage.isLoginBtnDisplayed(), "Login Page is not displayed");
		softAssert.assertAll();
	}

}
