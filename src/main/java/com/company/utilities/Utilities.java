package com.company.utilities;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Utilities
{

	public static void waitAndClickOnWebElement(WebElement webElement, WebDriverWait wait)
	{
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
		webElement.click();
	}

	public static void waitUntilSelectHasOptions(WebElement webElement, WebDriverWait wait)
	{
		final Select dropdown = new Select(webElement);
		wait.until(new ExpectedCondition<Boolean>()
		{
			@Nullable
			@Override //implementation of apply method??
			public Boolean apply(@Nullable WebDriver driver)
			{
				return (dropdown.getOptions().size() > 1);
			}
		});
	}

	public static void waitClearAndSetInputField(WebElement element, String value, WebDriverWait wait)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(value);
	}

	public static void waitSelectAllAndDeleteInputField(WebElement element, WebDriverWait wait)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}

	public static void waitAndSelectByValue(WebElement element, String value, WebDriverWait wait)
	{
		waitUntilSelectHasOptions(element, wait);
		Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}

	public static boolean isAttributePresent(WebElement element, WebDriverWait wait, String attribute)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		Boolean result = false;
		try
		{
			String value = element.getAttribute(attribute);
			if (value != null)
			{
				result = true;
			}
		}
		catch (Exception e)
		{
			log.debug("A Handled Exception is Thrown\n" + e);
		}

		return result;
	}

	public static String getWebElementText(WebElement element, WebDriverWait wait, String Attribute)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		String textValue = element.getAttribute(Attribute);
		if (textValue != null && textValue.length() > 0)
		{
			return element.getAttribute(Attribute);
		}
		else
		{
			return element.getText();
		}
	}

	public static boolean isFieldDisplayed(WebElement element, WebDriverWait wait)
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
			return element.isDisplayed();
		}
		catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e)
		{
			log.debug("A Handled Exception is Thrown\n" + e);
			return false;
		}
	}

	public static float extractPriceFromString(String input) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+");
		Matcher matcher = pattern.matcher(input);
		float total = 0.0f;

		while (matcher.find()) {
			total += Float.parseFloat(matcher.group());
		}

		return total;
	}

	public static double roundToTwoDecimalPlaces(double number) {
		String formattedNumber = String.format("%.2f", number);
		return Double.parseDouble(formattedNumber);
	}

}
