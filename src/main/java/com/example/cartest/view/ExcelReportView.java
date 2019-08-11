package com.example.cartest.view;

import com.example.cartest.domain.Car;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelReportView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=\"cars.xls\"");
        List<Car> carList = (List<Car>) model.get("carList");
        Sheet sheet = workbook.createSheet("Cars");
        Row header = sheet.createRow(0);
        int cel = 0;
        header.createCell(cel++).setCellValue("Brand");
        header.createCell(cel++).setCellValue("Name");
        header.createCell(cel++).setCellValue("Year");
        header.createCell(cel++).setCellValue("Month");
        header.createCell(cel++).setCellValue("Gear Box");

        int rowNum = 1;
        for(Car car : carList){
            Row row = sheet.createRow(rowNum++);
            cel = 0;
            row.createCell(cel++).setCellValue(car.getBrand());
            row.createCell(cel++).setCellValue(car.getName());
            row.createCell(cel++).setCellValue(car.getYear());
            row.createCell(cel++).setCellValue(car.getMonth());
            row.createCell(cel++).setCellValue(car.getGearBox().label);
        }
    }
}