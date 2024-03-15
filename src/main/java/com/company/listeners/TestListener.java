package com.company.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.company.utilities.Constants;
import com.company.utilities.DriverHandler;
import com.company.utilities.EncodeToBase64Utils;
import com.company.utilities.ExtentManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TestListener extends DriverHandler implements ITestListener
{

	// Extent Report Declarations
	public static ExtentReports extent = ExtentManager.createInstance();
	private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private static String screenshotPath;
	private static String screenshotTitle;
	private static String screenshotEncoded;
	private final Logger logger = LoggerFactory.getLogger(TestListener.class);
	private WebDriver driver;

	@Override
	public synchronized void onStart(ITestContext context)
	{
		logger.info("Test Suite " + context.getName() + " started!");
	}

	@Override
	public synchronized void onFinish(ITestContext context)
	{
		logger.info("Test Suite " + context.getName() + " ended!");
	}

	@Override
	public synchronized void onTestStart(ITestResult result)
	{
		logger.info(result.getTestContext().getAttribute(result.getMethod().getMethodName()).toString() + " started!");

		ExtentTest extentTest =
				extent.createTest(result.getTestContext().getAttribute(result.getMethod().getMethodName()).toString(),
								result.getMethod().getDescription())
						.assignCategory(result.getMethod().getXmlTest().getParameter("browser"));

		test.set(extentTest);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result)
	{
		logger.info(result.getTestContext().getAttribute(result.getMethod().getMethodName()).toString() + " Passed!");

		// TODO Need to refactor this and add it as Utilities
		if (Constants.PASS_SCREENSHOTS_FLAG.equalsIgnoreCase("TRUE"))
		{
			Class<?> testClass = result.getTestClass().getRealClass();
			try
			{
				// this field name must be present and equals in any testcase
				Field field = testClass.getDeclaredField("driver");

				field.setAccessible(true);

				driver = (WebDriver) field.get(result.getInstance());
				Screenshot screenshot =
						new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);

				if (screenshot != null)
				{
					File src;

					EncodeToBase64Utils encodeScreenshot = new EncodeToBase64Utils();

					screenshotTitle =
							result.getMethod().getMethodName() + "_" + new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-ms").format(
									new Date());

					screenshotPath =
							ExtentManager.getScreenshotFileLocation(ExtentManager.getCurrentPlatform(), screenshotTitle);

					ImageIO.write(screenshot.getImage(), "PNG", src = new File(screenshotPath));

					screenshotEncoded = encodeScreenshot.encodeFileToBase64Binary(src);

					test.get().pass("Test Passed",
							MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotEncoded).build());
				}
				else
				{
					if (result.getThrowable() != null)
					{
						result.getThrowable().printStackTrace();
						test.get().pass("Test Passed");
					}
				}

			}
			catch (NullPointerException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoClassDefFoundError e)
			{
				e.printStackTrace();
				if (result.getThrowable() != null && test.get() != null)
				{
					test.get().pass("Test Passed");
				}
			}
		}
		else
		{
			test.get().pass("Test Passed");
		}

	}

	@Override
	public synchronized void onTestFailure(ITestResult result)
	{
		logger.info(result.getTestContext().getAttribute(result.getMethod().getMethodName()).toString() + " Failed!");

		Class<?> testClass = result.getTestClass().getRealClass();
		try
		{
			// this field name must be present and equals in any testcase
			Field field = testClass.getDeclaredField("driver");

			field.setAccessible(true);

			driver = (WebDriver) field.get(result.getInstance());
			Screenshot screenshot =
					new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);

			if (screenshot != null)
			{
				File src;

				EncodeToBase64Utils encodeScreenshot = new EncodeToBase64Utils();

				screenshotTitle =
						result.getMethod().getMethodName() + "_" + new SimpleDateFormat("dd-MM-yyyy hh-mm-ss-ms").format(
								new Date());

				screenshotPath =
						ExtentManager.getScreenshotFileLocation(ExtentManager.getCurrentPlatform(), screenshotTitle);

				ImageIO.write(screenshot.getImage(), "PNG", src = new File(screenshotPath));

				screenshotEncoded = encodeScreenshot.encodeFileToBase64Binary(src);

				test.get().fail(result.getThrowable(),
						MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotEncoded).build());
			}
			else
			{
				if (result.getThrowable() != null)
				{
					result.getThrowable().printStackTrace();
					test.get().fail(result.getThrowable());
				}
			}

		}
		catch (NullPointerException | IOException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoClassDefFoundError e)
		{
			e.printStackTrace();
			if (result.getThrowable() != null && test.get() != null)
			{
				test.get().fail(result.getThrowable());
			}
		}
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result)
	{
		logger.info(result.getTestContext().getAttribute(result.getMethod().getMethodName()).toString() + " Skipped!");
		if (result.getThrowable() != null)
		{
			result.getThrowable().printStackTrace();
		}
		test.get().skip(result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
		logger.info("onTestFailedButWithinSuccessPercentage for " + result.getTestContext()
				.getAttribute(result.getMethod().getMethodName()).toString());
	}

}
