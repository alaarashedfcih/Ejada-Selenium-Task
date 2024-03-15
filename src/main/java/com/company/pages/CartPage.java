package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage
{

	public CartPage(WebDriver driver)
	{
		super(driver);

		checkCorrectPage("cart_contents_container", "This is Not the Correct Cart Page!");
		PageFactory.initElements(driver, this);
	}

	/*************** Home Page Elements ***************/
	@FindBy(xpath = "//span[@class='title']")
	WebElement pageTitle;

	@FindBy(id = "checkout")
	WebElement checkoutBtn;

	@FindBy(id = "item_5_title_link")
	WebElement fleeceJacketCartItem;

	@FindBy(id = "item_5_title_link")
	WebElement labsJacketCartItem;

	@FindBy(id = "shopping_cart_container")
	WebElement shoppingCartBtn;


	public boolean isCartPageDisplayed()
	{
		return Utilities.isFieldDisplayed(pageTitle, wait);
	}

	public void isFleeceJacketCartItemDisplayed()
	{
		Utilities.isFieldDisplayed(fleeceJacketCartItem, wait);
	}

	public void isLabsJacketCartItemDisplayed()
	{
		Utilities.isFieldDisplayed(labsJacketCartItem, wait);
	}

	public void isYourProductsAddedToCart()
	{
		isFleeceJacketCartItemDisplayed();
		isLabsJacketCartItemDisplayed();
	}

	public void clickOnCheckoutBtn()
	{
		Utilities.waitAndClickOnWebElement(checkoutBtn, wait);
	}

	public CheckoutPage navigateToCheckoutPage()
	{
		clickOnCheckoutBtn();
		return new CheckoutPage(driver);
	}




}
