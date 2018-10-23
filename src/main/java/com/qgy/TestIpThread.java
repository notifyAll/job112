package com.qgy;

import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestIpThread implements Runnable {
    //一行数据
    Row row;
//超时时间
    int timeout=6000;

    PoiIP poiIP;
    public TestIpThread(Row row,PoiIP poiIP) {
        this.row = row;
        this.poiIP=poiIP;
    }

    public TestIpThread(Row row,PoiIP poiIP, int timeout) {
        this(row,poiIP);
        this.timeout = timeout;
    }

    @Override
    public void run() {
        String ip=row.getCell(1).getStringCellValue();
        try {
            InetAddress byName = Inet4Address.getByName(ip);
            boolean reachable = byName.isReachable(timeout);

            if (reachable){
                row.createCell(2).setCellValue("是");
            }else {
                row.createCell(2).setCellValue("否");
            }

            // 给予计数 用以结束程序

            poiIP.addIpcount();
//            System.out.println(poiIP.getIpcount());
//            synchronized (PoiIP.class){
//                poiIP.notify();
//            PoiIP.class.notifyAll();
//            }

        } catch (UnknownHostException e) {
            row.createCell(2).setCellValue("否");
            e.printStackTrace();
        } catch (IOException e) {
            row.createCell(2).setCellValue("否");
            e.printStackTrace();
        }
    }
}
