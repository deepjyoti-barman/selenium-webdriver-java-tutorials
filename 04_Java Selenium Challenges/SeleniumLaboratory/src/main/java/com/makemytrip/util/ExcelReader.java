package com.makemytrip.util;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExcelReader {

    public static List<Object[]> getDataFromExcel(String filePath, String sheetName) {
        List<Object[]> excelData = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = sheet.getRow(0).getLastCellNum();

            for (int i = 1; i < noOfRows; i++) {
                Object[] rowData = new Object[noOfCols];

                for (int j = 0; j < noOfCols; j++) {
                    rowData[j] = sheet.getRow(i).getCell(j).toString();
                }

                excelData.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excelData;
    }

    public static Map<String, String> getDataFromExcelByColValue(String filePath, String sheetName, String shipmentId) {

        Map<String, String> excelData = new LinkedHashMap<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            List<String> colHeaderNames = new ArrayList<>();

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCols = sheet.getRow(0).getLastCellNum();

            // Get all the column header names ignoring first column
            if (noOfRows >= 1) {
                colHeaderNames = IntStream.range(1, noOfCols)
                        .mapToObj(i -> sheet.getRow(0).getCell(i).toString())
                        .collect(Collectors.toList());
            }

            // Verify if the given shipment record is available by shipmentId
            // If present get all shipment details
            for (int i = 1; i < noOfRows; i++) {
                int rowIndex = i;
                String firstColData = sheet.getRow(i).getCell(0).toString();

                if (firstColData.equalsIgnoreCase(shipmentId)) {
                    List<String> shipmentDetails  = IntStream.range(1, noOfCols)
                            .mapToObj(j -> sheet.getRow(rowIndex).getCell(j).toString())
                            .collect(Collectors.toList());

                    // Prepare the map containing shipment details
                    for (int k = 0; k < colHeaderNames.size(); k++)
                        excelData.put(colHeaderNames.get(k), shipmentDetails.get(k));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return excelData;
    }
}
