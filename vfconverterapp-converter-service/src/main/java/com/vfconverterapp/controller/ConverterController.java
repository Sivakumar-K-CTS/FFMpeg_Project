package com.vfconverterapp.controller;

import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		String result = service.formatConverter(inputFileName,inputFilenameWithoutExtension,inputFormat,outputPath);
		String response = result + " ,video has been uploaded the working directory!!!";
		logger.info("Video format is successfully completed");
		logger.info("Converted video will be present in the below path:\nPath: "+outputPath);
		return new ResponseEntity<>(response  ,HttpStatus.OK);
	}
}
