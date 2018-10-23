package com.qgy;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PoiTest {

    /**
     * .xls 和xlsx 读取方式不同
     */
    @Test
    public void poiRead(){
        File path=new File("C:\\112_10_17");
//        加盟
        File file=new File(path,"112 店结流水差异查询_20181017_999997.xlsx");
        File file1=new File(path,"1.12门店销售流水完整性对比表至18年10月14日--加盟.xls");

//        直营
        File file2=new File(path,"1.12门店销售流水完整性对比dgrdRptDisp.xls");
        File file3=new File(path,"1.12门店销售流水完整性对比表.xlsx");


        if (!file.exists()||!file1.exists()||!file2.exists()||!file3.exists()){
            System.out.println("文件不存在");
        }

        InputStream inputStream=null;

        Workbook workbook=null;

        try {
            inputStream = new FileInputStream(file);
            workbook=WorkbookFactory.create(inputStream);

            //工作表对象 获取改表格的第一张表
            Sheet sheet = workbook.getSheetAt(0);

            //总行数
            int rowLength = sheet.getLastRowNum()+1;

            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);

//            System.out.println(cell.getRow());

            Row row1 = sheet.createRow(row.getLastCellNum() + 1);
            row1.createCell(0).setCellValue("hello");
            row1.createCell(1).setCellValue(33);
            row1.createCell(2).setCellValue(new Date());
            for (int i = 2; i < sheet.getLastRowNum()+1; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < 3; j++) {
                    System.out.print(row.getCell(j)+"-");
                }
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }


}
