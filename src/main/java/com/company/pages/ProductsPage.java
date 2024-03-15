package com.company.pages;

import com.company.utilities.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage extends BasePage
{

	public ProductsPage(WebDriver driver)
	{
		super(driver);

		checkCorrectPage("shopping_cart_container", "This is Not the Correct Products Page!");
		PageFactory.initElements(driver, this);
	}

	/*************** Home Page Elements ***************/
	@FindBy(xpath = "//span[@class='title']")
	WebElement pageTitle;

	@FindBy(xpath = "//select[@class='product_sort_container']")
	WebElement sortProductsMenu;

	@FindBy(id = "add-to-cart-sauce-labs-fleece-jacket")
	WebElement fleeceJacketCartBtn;

	@FindBy(id = "add-to-cart-sauce-labs-backpack")
	WebElement labsJacketCartBtn;

	@FindBy(id = "shopping_cart_container")
	WebElement shoppingCartBtn;


	public boolean isProductsPageDisplayed()
	{
		return Utilities.isFieldDisplayed(pageTitle, wait);
	}
	public void sortProductsByHighPrice()
	{
		Utilities.waitAndSelectByValue(sortProductsMenu, "hilo", wait);
	}

	public void clickOnFleeceJacketCartBtn()
	{
		Utilities.waitAndClickOnWebElement(fleeceJacketCartBtn, wait);
	}

	public void clickOnLabsJacketCartBtn()
	{
		Utilities.waitAndClickOnWebElement(labsJacketCartBtn, wait);
	}

	public void addExpensiveTwoProductsToCart()
	{
		clickOnFleeceJacketCartBtn();
		clickOnLabsJacketCartBtn();
	}

	public void clickOnShoppingCartBtn()
	{
		Utilities.waitAndClickOnWebElement(shoppingCartBtn, wait);
	}

	public CartPage navigateToCartPage()
	{
		clickOnShoppingCartBtn();
		return new CartPage(driver);
	}


}
