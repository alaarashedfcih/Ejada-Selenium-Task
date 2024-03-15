package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogInPage extends BasePage
{

	public LogInPage(WebDriver driver)
	{
		super(driver);
		checkCorrectPage("login-button", "This is Not the Correct LogIn Page!");
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user-name")
	WebElement signInUserName;

	@FindBy(id = "password")
	WebElement signInPassword;

	@FindBy(id = "login-button")
	WebElement loginBtn;

	@FindBy(tagName = "h3")
	private WebElement errorMsg;

	public void setSignInUserName(String value)
	{
		Utilities.waitClearAndSetInputField(signInUserName, value, wait);
	}

	public void setSignInPassword(String value)
	{
		Utilities.waitClearAndSetInputField(signInPassword, value, wait);
	}

	public void clearSignInUserName()
	{
		Utilities.waitSelectAllAndDeleteInputField(signInUserName, wait);
	}

	public void clearSignInPassword()
	{
		Utilities.waitSelectAllAndDeleteInputField(signInPassword, wait);
	}

	public void clickOnLoginBtn()
	{
		Utilities.waitAndClickOnWebElement(loginBtn, wait);
	}

	public String getSignInUserNameFieldLabel()
	{
		return Utilities.getWebElementText(signInUserName, wait, "data-placeholder");
	}

	public Boolean isSubmitBtnDisabled()
	{
		return Utilities.isAttributePresent(loginBtn, wait, "disabled");
	}

	public boolean isLoginBtnDisplayed()
	{
		return Utilities.isFieldDisplayed(loginBtn, wait);
	}

	public String getSignInPasswordFieldLabel()
	{
		return Utilities.getWebElementText(signInPassword, wait, "data-placeholder");
	}

	public String getSubmitBtnLabel()
	{
		return Utilities.getWebElementText(loginBtn, wait, "mat-button-wrapper");
	}

	public ProductsPage login(String userId, String password)
	{
		setSignInUserName(userId);
		setSignInPassword(password);
		clickOnLoginBtn();
		return new ProductsPage(driver);
	}

	public String getErrorMessage(){
		return Utilities.getWebElementText(errorMsg,wait,"data-placeholder");
	}
}
