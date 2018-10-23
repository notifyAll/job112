package com.qgy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkBookFactory {
    /**
     * 返回表格对象
     *
     * @param file
     * @return
     */
    public static Workbook getWorkbook(File file) throws Exception {
        Workbook workbook = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println(file.getName() + "该文件读取失败" + e.getMessage());
            e.printStackTrace();
        }
        //判断其为 xlsx 还是 xls
        try {
            if (file.getName().matches(".*(xls)$")) {
                workbook = new HSSFWorkbook(new POIFSFileSystem(fileInputStream));
                System.out.println("读取文件：" + file.getName());
            } else if (file.getName().matches(".*(xlsx)$")) {
                workbook = new XSSFWorkbook(fileInputStream);
                System.out.println("读取文件：" + file.getName());
            }
        } catch (IOException e) {
            System.out.println(file.getName() + "该文件读取失败" + e.getMessage());
            throw new Exception(e.getMessage());
//            e.printStackTrace();
        }
        return workbook;
    }
}
