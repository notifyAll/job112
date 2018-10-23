package com.qgy;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PoiIP {

    private int ipcount=0;

    Object object=new Object();

    /**
     * ip 网络连通主程序 主要功能ping 网络是否正常
     */
    public  void pip(){
        System.out.println(TimeUtil.getStringTime(ConfigEnum.DATE_LOCAL_PATTERN.getMessage())+"——ip连通程序开始");
        File file =new File(ConfigEnum.PATH_NAME.getMessage());

        //获取网络不通的文件
        File wlbt=new File(file,"网络不通.xlsx");

        Workbook workbook=null;
        try {
             workbook = WorkBookFactory.getWorkbook(wlbt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sheet sheet=workbook.getSheetAt(0);
        ipcount=sheet.getLastRowNum()+1;

        //开启多线程执行
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            TestIpThread testIpThread =new TestIpThread(row,this);
            new Thread(testIpThread).start();
        }

        //判断合适可以写入文档 并结束程序
        System.out.println("主线程等待中");
        while (ipcount>0) {
//            try {
////                synchronized (PoiIP.class){
//                    PoiIP.class.wait();
////                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("main "+ipcount);
            if (ipcount>0){
                continue;
            }else {
                break;
            }
        }
        System.out.println("主线程唤醒");
        try {
            System.out.println(TimeUtil.getStringTime(ConfigEnum.DATE_LOCAL_PATTERN.getMessage())+"保存结果");
            workbook.write(new FileOutputStream(wlbt));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int getIpcount() {
        return ipcount;
    }

    public void addIpcount() {
        synchronized (object){
            this.ipcount--;
        }
    }
}
