package com.devpro.selenium.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataProvider {
    private static final Logger logger = LoggerFactory.getLogger(ExcelDataProvider.class);
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    
    public static Object[][] getTestData(String fileName, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        String filePath = TEST_DATA_PATH + fileName;
        
        // Kiểm tra file tồn tại
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("Test data file not found: {}", filePath);
            throw new RuntimeException("Test data file not found: " + filePath);
        }
        
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            // In ra tên các sheet có trong file
            logger.info("Available sheets in file {}:", fileName);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                logger.info("Sheet {}: {}", i, workbook.getSheetName(i));
            }
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                logger.error("Sheet '{}' not found in file: {}", sheetName, fileName);
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + fileName);
            }
            
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row not found in sheet: {}", sheetName);
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }
            
            // Đọc header
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    headers.add(cell.getStringCellValue());
                }
            }
            
            // Đọc dữ liệu
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) continue;
                
                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = currentRow.getCell(j);
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = String.valueOf((int) cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                value = "";
                        }
                    }
                    rowData.put(headers.get(j), value);
                }
                testData.add(rowData);
            }
            
            logger.info("Successfully read {} test data rows from sheet '{}'", testData.size(), sheetName);
            
        } catch (IOException e) {
            logger.error("Error reading test data file: {}", filePath, e);
            throw new RuntimeException("Error reading test data file: " + filePath, e);
        }
        
        if (testData.isEmpty()) {
            logger.error("No test data found in sheet '{}'", sheetName);
            throw new RuntimeException("No test data found in sheet: " + sheetName);
        }
        
        // Convert List<Map<String, String>> to Object[][] với từng giá trị String
        Object[][] data = new Object[testData.size()][headers.size()];
        for (int i = 0; i < testData.size(); i++) {
            Map<String, String> row = testData.get(i);
            for (int j = 0; j < headers.size(); j++) {
                data[i][j] = row.get(headers.get(j));
            }
        }
        return data;
    }

    public static Object[][] getOrderTestData(String fileName, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        String filePath = TEST_DATA_PATH + fileName;
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Object[] rowData = new Object[colCount];
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = new java.text.DecimalFormat("#").format(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                value = "";
                        }
                    }
                    rowData[j] = value;
                }
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toArray(new Object[0][]);
    }
} 