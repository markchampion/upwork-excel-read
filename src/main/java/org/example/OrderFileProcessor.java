package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFileProcessor {

    private final String sourceFilePath;
    private final String newDataFilePath;

    private List<CellStyle> cellHeaderStyle = new ArrayList<>();
    private Map<Integer, CellStyle> defaultCellStyle = new HashMap<>();

    public OrderFileProcessor(String sourceFilePath, String newDataFilePath) {
        this.sourceFilePath = sourceFilePath;
        this.newDataFilePath = newDataFilePath;
    }

    public void updateNewOrders(List<Order> newOrders) throws IOException {
        FileInputStream file = new FileInputStream(sourceFilePath);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        List<Order> orders = new ArrayList<>();
        int i = 0;
        for (Row row : sheet) {
            if (checkIfRowIsEmpty(row)) break;
            Order order = new Order();
            if (i > 0) {
                int j = 0;
                for (Cell cell : row) {
                    defaultCellStyle.put(j, cell.getCellStyle());
                    OrderMapperUtils.orderCellMapper(order, j, cell);
                    j++;
                }
                orders.add(order);
            } else {
                for (Cell cell : row) {
                    cellHeaderStyle.add(cell.getCellStyle());
                }
            }
            i++;
        }
        sheet.shiftRows(i, i + 20, newOrders.size());

        OrderSummary orderSummary = new OrderSummary();
        for (Order order : newOrders) {
            Row row = sheet.createRow(i);
            CellStyle headerStyle = workbook.createCellStyle();
            for (int j = 0; j < 11; j++) {
                sheet.autoSizeColumn(0);
                Cell cell = row.createCell(j);
                OrderMapperUtils.orderCellMapToFile(order, j, cell);
                if (defaultCellStyle.containsKey(j))
                    headerStyle.cloneStyleFrom(defaultCellStyle.get(j));
                cell.setCellStyle(headerStyle);
            }

            orderSummary.totalAdditionalChanges += order.getAdditionalChanges();
            orderSummary.totalSubTotal += order.getSubTotal();
            orderSummary.totalTax += order.getTax();
            orderSummary.total += order.getTotal();
            orderSummary.totalEarned += order.getTotalEarned();
            if (order.getOrderType().equalsIgnoreCase("Delivery")) {
                orderSummary.subTotalDeliver += order.getSubTotal();
                orderSummary.lessPercentDeliver += (order.getCommission() / 100.0D * order.getSubTotal());
            }
            if (order.getOrderType().equalsIgnoreCase("Takeout") && order.getOrderType().equalsIgnoreCase("CREDIT_CARD")) {
                orderSummary.lessPercentCredit += (order.getCommission() / 100.0D * order.getSubTotal());
                orderSummary.subTotalTakeOutCredit += order.getSubTotal();
            }
            if (order.getOrderType().equalsIgnoreCase("Takeout") && order.getOrderType().equalsIgnoreCase("CASH")) {
                orderSummary.lessPercentCash += (order.getCommission() / 100.0D * order.getSubTotal());
                orderSummary.lessCashSales += order.getSubTotal();
                orderSummary.lessCashTax += order.getTax();
                orderSummary.subTotalTakeOutCredit += order.getSubTotal();
            }
            i++;
        }
        i += 2;
        int[] totalCellIndex = new int[]{6, 7, 8, 9, 10};
        Row row = sheet.getRow(i);
        for (int cellIndex : totalCellIndex) {
            Cell cell = row.getCell(cellIndex);
            updateFormula(cell, orders.size() + 1, newOrders.size() + orders.size() + 1);
        }
        i += 3;

        int[] totalRowIndex = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        for (int rowIndex : totalRowIndex) {
            Row sum = sheet.getRow(i + rowIndex);
            Cell cell = sum.getCell(10);
            updateFormula(cell, orders.size() + 1, newOrders.size() + orders.size() + 1);
        }
        FileOutputStream fos = new FileOutputStream(sourceFilePath);
        workbook.write(fos);
        workbook.close();
    }

    private void updateFormula(Cell cell, int oldSize, int newSize) {
        if (cell != null && cell.getCellType() != CellType.BLANK && Strings.isNotBlank(cell.toString())) {
            String totalAddFormula = cell.getCellFormula();
            totalAddFormula = totalAddFormula.replaceAll("([A-Z])" + oldSize, "$1" + newSize + "");
            cell.setCellFormula(totalAddFormula);
        }
    }

    public List<Order> loadOrderFromSource() throws IOException {
        List<Order> orders = new ArrayList<>();
        try {
            CSVParser parser = new CSVParser(new FileReader(newDataFilePath), CSVFormat.DEFAULT);
            List<CSVRecord> list = parser.getRecords();
            int i = 0;
            for (CSVRecord record : list) {
                i++;
                if (i == 1) continue;
                int j = 0;
                Order order = new Order();
                for (String str : record) {
                    OrderMapperUtils.orderCellMapper(order, j, str);
                    j++;
                }
                orders.add(order);
            }
            parser.close();
        } catch (Exception ex) {
            System.out.println("Error when loadOrderFromSource: " + ex.getMessage());
            throw ex;
        }
        return orders;
    }

    private boolean checkIfRowIsEmpty(Row row) {
        boolean isEmptyRow = true;
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && Strings.isNotBlank(cell.toString())) {
                isEmptyRow = false;
            }
        }
        return isEmptyRow;
    }
}
