package com.company.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class EncodeToBase64Utils
{

	@SuppressWarnings("unused")
	public String encodeFileToBase64Binary(File file)
	{
		String encodedfile = null;
		try
		{
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");
			fileInputStreamReader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return encodedfile;
	}
}
