package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static com.company.utilities.Constants.OVERVIEW_PAGE_URL;
import static com.company.utilities.Utilities.extractPriceFromString;
import static com.company.utilities.Utilities.roundToTwoDecimalPlaces;

public class OverviewPage extends BasePage
{

	public OverviewPage(WebDriver driver)
	{
		super(driver);

		checkCorrectPage("checkout_summary_container", "This is Not the Correct Checkout Page!");
		PageFactory.initElements(driver, this);
	}

	/*************** Home Page Elements ***************/
	@FindBy(xpath = "//span[@class='title']")
	WebElement pageTitle;

	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	WebElement itemsPrice;

	@FindBy(xpath = "//div[@class='summary_tax_label']")
	WebElement taxPrice;

	@FindBy(xpath = "//div[@class='summary_info_label summary_total_label']")
	WebElement totalPrice;
	@FindBy(id = "finish")
	WebElement finishBtn;

	public boolean isOverviewPageDisplayed()
	{
		return Utilities.isFieldDisplayed(pageTitle, wait);
	}

	public float getItemsPrice(){
		String price =  Utilities.getWebElementText(itemsPrice, wait, "data-placeholder");
		return extractPriceFromString(price);
	}

	public float getTaxPrice(){
		String price =   Utilities.getWebElementText(taxPrice, wait, "data-placeholder");
		return extractPriceFromString(price);
	}

	public float getTotalPrice(){
		String price =   Utilities.getWebElementText(totalPrice, wait, "data-placeholder");
		return extractPriceFromString(price);
	}
	public void scroll()
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500);");
	}
	public boolean verifyTotalAmount(){

		// Get the prices
		float itemsPrice = getItemsPrice();
		float totalPrice = getTotalPrice();
		float taxPrice = getTaxPrice();
		float confirmPrice  = totalPrice - taxPrice;
		double totalPriceRounded = roundToTwoDecimalPlaces(confirmPrice);
		double itemsPriceRounded = roundToTwoDecimalPlaces(itemsPrice);
		return itemsPriceRounded == totalPriceRounded;
	}
	public void clickOnFinishBtn()
	{
		Utilities.waitAndClickOnWebElement(finishBtn, wait);
	}

	public boolean verifyOverviewPageUrl() {
		// Get the current URL
		String currentUrl = driver.getCurrentUrl();
		return currentUrl.equals(OVERVIEW_PAGE_URL);
	}

	public CompletePage navigateToCompletePage()
	{
		clickOnFinishBtn();
		return new CompletePage(driver);
	}




}
