package com.vfconverterapp.service;

import org.springframework.web.multipart.MultipartFile;

import com.vfconverterapp.exceptions.FileFormatNotSupportedException;

/**
 * DESC: This is an interface contains the abstract methods for services which is used in vfconverter-api
 * 
 * @author SivakumarK
 *
 */
public interface IConverterService {
	
	String fileUploader(MultipartFile file, String path) throws FileFormatNotSupportedException;

	String formatConverter(String inputFileName, String inputFilenameWithoutExtension, String format, String Path);
}
