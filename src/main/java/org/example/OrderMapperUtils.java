package org.example;

import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Cell;

public class OrderMapperUtils {

    public static void orderCellMapper(Order order, int cellNum, Cell cell) {
        switch (cellNum) {
            case 0:
                order.setOid(cell.getStringCellValue());
                break;
            case 1:
                order.setOrderType(cell.getStringCellValue());
                break;
            case 2:
                order.setDate(cell.getStringCellValue());
                break;
            case 3:
                order.setPaymentMethod(cell.getStringCellValue());
                break;
            case 4:
                order.setMealPeriod(cell.getStringCellValue());
                break;
            case 5:
                order.setCommission(cell.getNumericCellValue());
                break;
            case 6:
                order.setAdditionalChanges(cell.getNumericCellValue());
                break;
            case 7:
                order.setSubTotal(cell.getNumericCellValue());
                break;
            case 8:
                order.setTax(cell.getNumericCellValue());
                break;
            case 9:
                order.setTotal(cell.getNumericCellValue());
                break;
            case 10:
                order.setTotalEarned(cell.getNumericCellValue());
                break;
        }
    }

    public static void orderCellMapToFile(Order order, int cellNum, Cell cell) {
        switch (cellNum) {
            case 0:
                cell.setCellValue(order.getOid());
                break;
            case 1:
                cell.setCellValue(order.getOrderType());
                break;
            case 2:
                cell.setCellValue(order.getDate());
                break;
            case 3:
                cell.setCellValue(order.getPaymentMethod());
                break;
            case 4:
                cell.setCellValue(order.getMealPeriod());
                break;
            case 5:
                cell.setCellValue(order.getCommission());
                break;
            case 6:
                cell.setCellValue(order.getAdditionalChanges());
                break;
            case 7:
                cell.setCellValue(order.getSubTotal());
                break;
            case 8:
                cell.setCellValue(order.getTax());
                break;
            case 9:
                cell.setCellValue(order.getTotal());
                break;
            case 10:
                break;
        }
    }

    public static void orderCellMapper(Order order, int cellNum, String cell) {
        if (cell != null && !cell.isEmpty()) {
            switch (cellNum) {
                case 0:
                    order.setOid(cell);
                    break;
                case 1:
                    order.setOrderType(cell);
                    break;
                case 2:
                    order.setDate(cell);
                    break;
                case 3:
                    order.setPaymentMethod(cell);
                    break;
                case 4:
                    order.setMealPeriod(cell);
                    break;
                case 5:
                    order.setCommission(Double.parseDouble(cell));
                    break;
                case 6:
                    order.setAdditionalChanges(Double.parseDouble(cell));
                    break;
                case 7:
                    order.setSubTotal(Double.parseDouble(cell));
                    break;
                case 8:
                    order.setTax(Double.parseDouble(cell));
                    break;
                case 9:
                    order.setTotal(Double.parseDouble(cell));
                    break;
                case 10:
                    order.setTotalEarned(Double.parseDouble(cell));
                    break;
            }
        }
    }
}
