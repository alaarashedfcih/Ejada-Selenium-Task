package com.company.listeners;

import com.company.utilities.Constants;
import com.company.utilities.ExtentManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Properties;

@Slf4j
public class SuiteListener implements ISuiteListener
{
	private String testReportFilePath;

	@Override
	public void onStart(ISuite suite)
	{
		log.info("Extent Reports Version 5 is starting!");
		ISuiteListener.super.onStart(suite);
	}

	@Override
	public void onFinish(ISuite suite)
	{
		log.info(("Extent Reports Version 5  is ending!"));
		ISuiteListener.super.onFinish(suite);
		TestListener.extent.flush();

	}
}
