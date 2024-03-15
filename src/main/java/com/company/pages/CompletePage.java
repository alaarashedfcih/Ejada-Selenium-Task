package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.company.utilities.Utilities.extractPriceFromString;

public class CompletePage extends BasePage
{

	public CompletePage(WebDriver driver)
	{
		super(driver);

		checkCorrectPage("checkout_complete_container", "This is Not the Correct Checkout Page!");
		PageFactory.initElements(driver, this);
	}

	/*************** Home Page Elements ***************/
	@FindBy(xpath = "//span[@class='title']")
	WebElement pageTitle;

	@FindBy(tagName = "h2")
	WebElement thanksMsg;

	@FindBy(css = ".complete-text")
	WebElement orderDispatchedMsg;


	public boolean isCompletePageDisplayed()
	{
		return Utilities.isFieldDisplayed(pageTitle, wait);
	}

	public String getThanksMsgText(){
		return Utilities.getWebElementText(thanksMsg, wait, "data-placeholder");
	}
	public String getOrderDispatchedMsgText(){
		return Utilities.getWebElementText(orderDispatchedMsg, wait, "data-placeholder");
	}

}
