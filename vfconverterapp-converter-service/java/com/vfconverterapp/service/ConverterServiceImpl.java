package com.vfconverterapp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
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
public class ConverterServiceImpl implements IConverterService {

	private Logger logger = LoggerFactory.getLogger(ConverterServiceImpl.class);

	/**
	 * DESC: Will try to validate the extension of the file and upload it to a
	 * directory to make it ready for the format conversion of the input video.
	 * 
	 * RETURN: Input file name with extension.
	 */
	@Override
	public String fileUploader(MultipartFile file, String path) throws FileFormatNotSupportedException {
		logger.info("Inside the fileUploader() in ConverterServiceImpl class");
		String inputFormat = FilenameUtils.getExtension(file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		if (((inputFormat.equalsIgnoreCase("mp4") || inputFormat.equalsIgnoreCase("mov")
				|| inputFormat.equalsIgnoreCase("avi") || inputFormat.equalsIgnoreCase("mkv")
				|| inputFormat.equalsIgnoreCase("WMV")))) {

			try (InputStream inputStream = file.getInputStream()) {
				System.out.println(path);
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
	 * DESC: Will convert the video file to the format provided by the user.
	 *
	 * RETURN: A string "Success".
	 */
	@Override
	public String formatConverter(String inputFileName, String inputFilenameWithoutExtension, String format,
			String path) throws WrongUserInputFormatException {
		
		logger.info("Inside the formatConverter() in ConverterServiceImpl class");
		if (((format.equals("mp4") || format.equals("mov") || format.equalsIgnoreCase("avi")
				|| format.equals("mkv")))) {
			//Process process = nullc;
			logger.info("Before the starting of video format conversion");
			try {
				Process process = Runtime.getRuntime().exec("cmd /c ffmpeg -i " + inputFileName + " "
						+ inputFilenameWithoutExtension + "_Converted." + format, null, new File(path));
				System.out.println(process.toString());
				Thread.sleep(30000);
				
//				Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe" );
//				System.out.println(process.toString());
//				process.destroy();
				System.out.println(process.toString());
//				String cmd = "taskkill /F /PID " + process.pid();
//				Runtime.getRuntime().exec(cmd);
				System.gc();
				System.out.println(process.toString());
				
				/*if(process.isAlive()) {
					logger.info("Process:--------------------"+process.toString());
					process.waitFor();
				}else {
					process.destroy();
				}*/
				
				//Thread.sleep(10000);
				/*long id = process.pid();
				Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(id);
				optionalProcessHandle.ifPresent(processHandle -> processHandle.destroy());*/
				/*Runtime.getRuntime().exec("taskkill /F /IM " + process + ".exe");
				process.destroy();*/
				
				/*process.waitFor(1, TimeUnit.MINUTES);*/
				//process.destroy();
				
			
				
				
				File file = new File(path+"\\"+inputFilenameWithoutExtension + "_Converted." + format);
				System.out.println(file.canRead());
				System.out.println("fileIsNotLocke:-------"+file.renameTo(file));
				
				FileReader reader = new FileReader(file);
				  long pid = process.pid();
				  System.out.println("pid:-------"+pid);
				  if(process.isAlive()) {
					  process.destroy();
				  }
				  //Runtime.getRuntime().exec("kill " + pid);
				  reader.close();
				
				
				/*FileWriter fw = new FileWriter(file);
				fw.close();*/
				System.out.println(file.canRead());
				
				logger.info("The video format converted successfully");

			} catch (IOException | InterruptedException e) {
				logger.error("Process object did not execute the ffmpeg command");
				e.printStackTrace();
			} /*
				 * catch (InterruptedException e) { e.printStackTrace(); }
				 * 
				 * if(!process.isAlive()) { process.destroy(); }
				 */
			

		} else {
			logger.error("User requested format of the output is invalid");
			throw new WrongUserInputFormatException();
		}

		return "Success video has been successfully converted and stored in " + path;
	}

	
}
