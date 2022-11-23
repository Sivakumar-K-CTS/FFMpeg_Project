package com.vfconverterapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vfconverterapp.service.IConverterService;

/**
 * 
 * DESC: This is a controller class contained all the mapped methods which is called using the web request with the matching string mapped to the particular method.   
 * 
 * @author SivakumarK
 *
 */

@RestController
@RequestMapping("/vfconverter-api")
public class ConverterController {
	
	private Logger logger = LoggerFactory.getLogger(ConverterController.class);
	
 
	@Autowired
	IConverterService service;
	
	String inputFileName;
	String inputFormat;
	
	/**
	 * Value is retrieved from the application.yml file present in the external git repository 
	 */
	@Value("${outPath}")	
	private String outputPath;
	
	/**
	 * DESC: Used to call the videoUploader method in the ConverterServiceImpl which is the implementation class of IConverterService.
	 * 
	 * @param uploadedFile
	 * @param format
	 * @return Uploaded input file name with the extension as part of the response entity with header and status code 
	 */
	@PostMapping("/uploader")
	public ResponseEntity<String> videoUploader(@RequestParam("file") MultipartFile uploadedFile,
			@RequestParam("format") String format){
		logger.info("Inside videoUploader() of ConverterController class");
		logger.info("input multipart file: "+uploadedFile);
		logger.info("Requested format for conversion: "+format);
		logger.info("Calling fileUploader() of IConverterService interface");
		inputFileName = service.fileUploader(uploadedFile, outputPath);
		logger.info("Input file uploaded successfully to the directory - "+outputPath);
		logger.info("inputFileName - variable - value : "+inputFileName);
		inputFormat = format;
		HttpHeaders header = new HttpHeaders();
		header.add("Desc", "Upload the input video to the woring directory");
		return new ResponseEntity<>(inputFileName,header,HttpStatus.OK);
	}
	
	
	/**
	 * DESC: Used to call the formatConverter method in the ConverterServiceImpl which is the implementation class of IConverterService.
	 * 
	 * @return String which is returned by the formatConverter method.
	 */
	@PostMapping("/converter")
	public ResponseEntity<String> videoFormatConverter(){
		
		logger.info("Inside videoFormatConverter() in ConverterController");
		logger.info("Global variable inputFileName: "+inputFileName);
		logger.info("Global variable inputFormat: "+inputFormat);

		String inputFilenameWithoutExtension = FilenameUtils.removeExtension(inputFileName);
		logger.info("inputFilenameWithoutExtension - variable - value : "+inputFilenameWithoutExtension);
		logger.info("Calling formatConverter(inputFileName,inputFilenameWithoutExtension,inputFormat,outputPath) of IConverterService interface");
		String response = service.formatConverter(inputFileName,inputFilenameWithoutExtension,inputFormat,outputPath);
		logger.info("Video format is successfully completed");
		logger.info("Converted video will be present in the below path:\nPath: "+outputPath);
		return new ResponseEntity<>(response  ,HttpStatus.OK);
	}


		@RequestMapping("/download")
		public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response) throws IOException {

			String name = FilenameUtils.removeExtension(inputFileName);
			File file = new File(outputPath + "/"+name+"_Converted."+inputFormat);
			if (file.exists()) {
				
				//get the mimetype
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				System.out.println(file.getName());
				if (mimeType == null) {
					//unknown mimetype so set the mimetype to application/octet-stream
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);

				/**
				 * In a regular HTTP response, the Content-Disposition response header is a
				 * header indicating if the content is expected to be displayed inline in the
				 * browser, that is, as a Web page or as part of a Web page, or as an
				 * attachment, that is downloaded and saved locally.
				 * 
				 */

				/**
				 * Here we have mentioned it to show inline
				 */
				response.setHeader("Content-Disposition", String.format("attachement; filename=\"" + file.getName() + "\""));

				 //Here we have mentioned it to show as attachment
				 //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				FileCopyUtils.copy(inputStream, response.getOutputStream());

			}
		}
	
}
