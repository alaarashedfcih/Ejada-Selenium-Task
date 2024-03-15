package com.ejada.tests.shopping;

import com.company.dataproviderobjects.LoginUsersData;
import com.company.pages.*;
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

public class ValidateShoppingTest extends BaseTest
{
	WebDriver driver;
	LogInPage logInPage;
	ProductsPage productsPage;
	CartPage cartPage;
	CheckoutPage checkoutPage;
	OverviewPage overviewPage;
	CompletePage completePage;
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
					+ "TC #00: Shopping");
		}
		super.setUp(browser);
		driver = getDriver();
	}

	@Test(alwaysRun = true, dataProvider = "LoginUsersDataFeed", dataProviderClass = DataProviderSource.class)
	public void shopping_ValidCheckoutOrderTest(LoginUsersData data)
	{
		softAssert = new SoftAssert();
		logInPage = new LogInPage(driver);
		softAssert.assertTrue(logInPage.isLoginBtnDisplayed(), "Log in Page is not displayed");
		productsPage = logInPage.login(data.getUserName(),data.getPassword());
		softAssert.assertTrue(productsPage.isProductsPageDisplayed(),"Products Page is not displayed");
		productsPage.sortProductsByHighPrice();
		productsPage.addExpensiveTwoProductsToCart();
		cartPage = productsPage.navigateToCartPage();
		softAssert.assertTrue(cartPage.isCartPageDisplayed(),"Cart Page is not displayed");
		cartPage.isYourProductsAddedToCart();
		checkoutPage = cartPage.navigateToCheckoutPage();
		softAssert.assertTrue(checkoutPage.isCheckOutPageDisplayed(),"Checkout Page is not displayed");
		checkoutPage.fillCheckoutForm(data.getFirstName(),data.getLastName(),data.getPostalCode());
		overviewPage = checkoutPage.navigateToOverviewPage();
		softAssert.assertTrue(overviewPage.isOverviewPageDisplayed(),"Overview Page is not displayed");
		overviewPage.scroll();
		softAssert.assertTrue(overviewPage.verifyTotalAmount(),"Total price is not correct");
		softAssert.assertTrue(overviewPage.verifyOverviewPageUrl(),"Overview page URL is not correct");
		completePage = overviewPage.navigateToCompletePage();
		softAssert.assertTrue(completePage.isCompletePageDisplayed(),"Complete Page is not displayed");
		softAssert.assertEquals(completePage.getThanksMsgText(),data.getThanksMsg(),"Thank You Msg is not displayed");
		softAssert.assertEquals(completePage.getOrderDispatchedMsgText(),data.getOrderDispatchedMsg(),"Order Dispatched is not displayed");
		softAssert.assertAll();
	}

}
