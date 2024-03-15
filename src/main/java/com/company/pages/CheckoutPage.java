package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage
{

	public CheckoutPage(WebDriver driver)
	{
		super(driver);

		checkCorrectPage("continue", "This is Not the Correct Checkout Page!");
		PageFactory.initElements(driver, this);
	}

	/*************** Home Page Elements ***************/
	@FindBy(xpath = "//span[@class='title']")
	WebElement pageTitle;

	@FindBy(id = "first-name")
	WebElement inputFirstName;

	@FindBy(id = "last-name")
	WebElement inputLastName;

	@FindBy(id = "postal-code")
	WebElement inputPostalCode;
	@FindBy(id = "continue")
	WebElement continueBtn;


	public boolean isCheckOutPageDisplayed()
	{
		return Utilities.isFieldDisplayed(pageTitle, wait);
	}



	public void setInputFirstName(String value)
	{
		Utilities.waitClearAndSetInputField(inputFirstName, value, wait);
	}
	public void setInputLastName(String value)
	{
		Utilities.waitClearAndSetInputField(inputLastName, value, wait);
	}
	public void setInputPostalCode(String value)
	{
		Utilities.waitClearAndSetInputField(inputPostalCode, value, wait);
	}


	public void clickOnContinueBtn()
	{
		Utilities.waitAndClickOnWebElement(continueBtn, wait);
	}

	public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
		setInputFirstName(firstName);
		setInputLastName(lastName);
		setInputPostalCode(postalCode);
	}
	public OverviewPage navigateToOverviewPage()
	{
		clickOnContinueBtn();
		return new OverviewPage(driver);
	}




}
