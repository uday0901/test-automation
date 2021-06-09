package com.automation.utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileUtilities {

	/**
	 * FileUtilities class provides methods to perform file operations on any kind
	 * of file
	 */
	File file = null;
	Files files = null;
	Paths paths = null;
	FileInputStream inputStream = null;
	FileOutputStream outputStream = null;
	List<CSVRecord> csvRecords = new LinkedList<>();
	Reader reader = null;
	CSVParser csvParser = null;
	private Workbook workbook = null;
	Sheet sheet = null;
	int noOfRows = 0;

	/**
	 * This methods provides FileInputStream object of any file that we are going to
	 * read
	 *
	 * @param filePath - file location
	 * @param fileName - name of the file including extension
	 * @return - returns FileInutStreamObject for a specified file
	 */
	public FileInputStream getFileInputStream(String filePath, String fileName) {

		try {
			file = new File(filePath + fileName);
			inputStream = new FileInputStream(file);
		} catch (Exception e) {

		}

		return inputStream;
	}

	/**
	 * getNumberOfRowsFromExcel method is used to determine .xls and .xlsx files.
	 * based file type, workbook object is instantiated accordingly
	 *
	 * @param filePath  - excel file's location
	 * @param fileName  - name of the excel file including extension
	 * @param sheetName - name of the sheet from which we want to read the data
	 * @return - it returns the number rows that the excel sheet contains
	 */

	public int getNumberOfRowsFromExcel(String filePath, String fileName, String sheetName) {
		sheet = null;

		String fileExtension = fileName.substring(fileName.indexOf(".xls"), fileName.length());

		try {
			if (fileExtension.equalsIgnoreCase(".xlsx")) {
				workbook = new XSSFWorkbook(getFileInputStream(filePath, fileName));
			} else if (fileExtension.equalsIgnoreCase(".xls")) {
				workbook = new HSSFWorkbook(getFileInputStream(filePath, fileName));
			}

			sheet = workbook.getSheet(sheetName);
			noOfRows = sheet.getPhysicalNumberOfRows();

		} catch (Exception e) {

		}
		return noOfRows;

	}

	/**
	 * This method is used to get the CSV file data into scanner object
	 *
	 * @param filePath - file location
	 * @param fileName - name of the csv file including extension
	 * @throws IOException
	 */
	public List<CSVRecord> readCSVData(String filePath, String fileName) throws IOException {
		reader = Files.newBufferedReader(Paths.get(filePath + fileName));
		csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Environment", "URL", "UserName", "Password")
				.withIgnoreHeaderCase().withTrim());
		for (CSVRecord csvRecord : csvParser) {
			csvRecords.add(csvRecord);
		}
		return csvRecords;
	}

	public JSONArray readJsonFile(String filePath, String fileName) {
		JSONArray testData = null;
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(filePath+"/"+fileName)) {
			//Read JSON file
			Object obj = jsonParser.parse(reader);

			testData = (JSONArray) obj;
			System.out.println(testData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return testData;
	}

}
