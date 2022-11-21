package com.vfconverterapp.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vfconverterapp.controller.ConverterController;
import com.vfconverterapp.exceptions.FileFormatNotSupportedException;
import com.vfconverterapp.exceptions.WrongUserInputFormatException;

/**
 * DESC: Implementation class for the interface IConverterService
 * 
 * @author SivakumarK
 *
 */
@Service
public class ConverterServiceImpl implements IConverterService{
	
	private Logger logger = LoggerFactory.getLogger(ConverterServiceImpl.class);
	Process process = null;

	/**
	 * DESC: Will try to validate the extension of the file and upload it to a directory to make it ready for the format conversion of the input video.
	 * 
	 * RETURN: Input file name with extension.
	 */
	@Override
	public String fileUploader(MultipartFile file, String path)
			throws FileFormatNotSupportedException{
		logger.info("Inside the fileUploader() in ConverterServiceImpl class");
		String inputFormat = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		if (((inputFormat.equalsIgnoreCase("mp4") || inputFormat.equalsIgnoreCase("mov")
				|| inputFormat.equalsIgnoreCase("avi") || inputFormat.equalsIgnoreCase("mkv")
				|| inputFormat.equalsIgnoreCase("WMV")))) {
			
				
				try (InputStream inputStream = file.getInputStream()) {
					logger.info("Before cleaning the working directory");
					FileUtils.cleanDirectory(new File(path));
					logger.info("Cleaning of the working directory is completed");
					Path filePath = Paths.get(path).resolve(fileName);
					logger.info("Before copying the input file to the working directory");
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
					logger.info("The input file is copied successfully to the working directory");
				} catch (IOException e) {
					e.printStackTrace();
				}

			
		} else {
			logger.error("Input file format is not supported by this application");
			throw new FileFormatNotSupportedException();
		}
		return fileName;
	}

	/**
	 *	DESC: Will convert the video file to the format provided by the user.
	 *
	 * RETURN: A string "Success".
	 */
	@Override
	public String formatConverter(String inputFileName, String inputFilenameWithoutExtension, String format,
			String path) throws WrongUserInputFormatException {

		logger.info("Inside the formatConverter() in ConverterServiceImpl class");
		if (((format.equals("mp4") || format.equals("mov")
				|| format.equalsIgnoreCase("avi") || format.equals("mkv")))) {
			
			logger.info("Before the starting of video format conversion");
			try {
				process = Runtime.getRuntime().exec(
						"cmd /c ffmpeg -i " + inputFileName + " " + inputFilenameWithoutExtension + "_Converted." + format,
						null, new File(path));
				//int processTimer = process.waitFor();
				
				logger.info("The video format converted successfully");
				
			} catch (IOException e) {
				logger.error("Process object did not execute the ffmpeg command");
				e.printStackTrace();
			} /*
				 * catch (InterruptedException e) { e.printStackTrace(); }
				 */
			
		    

		}else {
			logger.error("User requested format of the output is invalid");
			throw new WrongUserInputFormatException();
		}
		
		
		return "Success, The video has been convrted and stored in "+path;
	}


}
