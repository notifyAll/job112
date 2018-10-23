package com.qgy;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;

public class FileTest implements Serializable {
    @Test
    public  void  text(){
        try {
            FileOutputStream f2=new FileOutputStream("people.out");
            FileInputStream f=new FileInputStream("people.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void  text1(){
        File path=new File(ConfigEnum.PATH_NAME.getMessage());
        try {
            Workbook workbook = WorkBookFactory.getWorkbook(new File(path, "1.12门店销售流水完整性对比表至18年10月14日--加盟.xls"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
