package com.qgy;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class Poi112 {
    private int count = 0; // 总门店数
    private int pc_count = 0; // 非本区域门店
    private int cy_count = 0; // 差异门店数量

    /**
     * 112主程序 1.0
     */
    public void p112_1() {
        System.out.println(TimeUtil.getStringTime(ConfigEnum.DATE_LOCAL_PATTERN.getMessage()));

        File path = new File(ConfigEnum.PATH_NAME.getMessage());
        if (!path.exists()) path.mkdir();  // 创建目录

//        加盟
        File jm_source = new File(path, "112 店结流水差异查询_" + TimeUtil.getStringTime(ConfigEnum.DATE_112JM_PATTERN.getMessage()) + "_999997.xlsx");
//        直营
        File zy_source = new File(path, "1.12门店销售流水完整性对比dgrdRptDisp.xlsx");

        File jg = new File(path, "112查询结果.xlsx");

        if (!jg.exists()) { //文件不存在 则创建
            try {
                jg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        FileInputStream zy_source_i = null; // 读取源文件

        FileOutputStream jg_o = null; // 写入目标文件

        Workbook jg_i_e = null;
        try {
            jg_i_e = WorkBookFactory.getWorkbook(jg); //读取目标资源
        } catch (Exception e) {
            jg_i_e = new XSSFWorkbook();
        }

        Workbook zy_source_i_e = null; //获取直营资源
        Workbook jm_source_i_e = null; //加盟资源
        try {
            zy_source_i_e = WorkBookFactory.getWorkbook(zy_source);
            jm_source_i_e = WorkBookFactory.getWorkbook(jm_source);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Sheet jg112 = jg_i_e.createSheet("jg112" + TimeUtil.getStringTime(ConfigEnum.DATE_PATTERN.getMessage()));
        Sheet zy_source_i_eSheetAt = zy_source_i_e.getSheetAt(0);
        Sheet jm_source_i_eSheetAt = jm_source_i_e.getSheetAt(0);

//直营表格初处理 去除首尾
//        zy_source_i_eSheetAt.removeRow(zy_source_i_eSheetAt.getRow(0)); //删除第一行 removeRow 删除数据 原先的行位置还在 下方的数据不会上移
//        System.out.println(zy_source_i_eSheetAt.getLastRowNum());
        zy_source_i_eSheetAt.shiftRows(1, zy_source_i_eSheetAt.getLastRowNum(), -1); // -1 向上挪一行 会将前面的覆盖掉  正数向下
//        System.out.println(zy_source_i_eSheetAt.getLastRowNum());
        zy_source_i_eSheetAt.removeRow(zy_source_i_eSheetAt.getRow(zy_source_i_eSheetAt.getLastRowNum())); //删除最后一行
//        System.out.println(zy_source_i_eSheetAt.getLastRowNum());
//        加盟表格初处理
        jm_source_i_eSheetAt.shiftRows(2, jm_source_i_eSheetAt.getLastRowNum(), -2);
        jm_source_i_eSheetAt.removeRow(jm_source_i_eSheetAt.getRow(jm_source_i_eSheetAt.getLastRowNum()));

        //筛选负责区域 及提取差异到目标表
        shaiXuan(zy_source_i_eSheetAt, jg112,ConfigEnum.ZY_THEME.getMessage());
        zongJie(jg112);

        shaiXuan(jm_source_i_eSheetAt, jg112,ConfigEnum.JM_THEME.getMessage());
        zongJie(jg112);

        try {
            //写到输出文件中
            jg_i_e.write(new FileOutputStream(jg));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                关闭流
                jg_i_e.close();
                jm_source_i_e.close();
                zy_source_i_e.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("导出结果完成");
    }
    /**
     * 112主程序 2.0
     */
    public void p112_2() {
        System.out.println(TimeUtil.getStringTime(ConfigEnum.DATE_LOCAL_PATTERN.getMessage())+"112程序开始");

        File path = new File(ConfigEnum.PATH_NAME.getMessage());
        if (!path.exists()) path.mkdir();  // 创建目录

        /**1 获取文件*/
//        加盟
        File jm_source = new File(path, "112 店结流水差异查询_" + TimeUtil.getStringTime(ConfigEnum.DATE_112JM_PATTERN.getMessage()) + "_999997.xlsx");
        File jm_target = new File(path, "1.12门店销售流水完整性对比表至" + TimeUtil.getStringTime(ConfigEnum.DATE_112JM_TARGET_PATTERN.getMessage(), -4) + "--加盟.xlsx");
        //        直营
        File zy_source = new File(path, "1.12门店销售流水完整性对比dgrdRptDisp.xlsx");
        File zy_target = new File(path, "1.12门店销售流水完整性对比表.xlsx");


        Workbook zy_source_i_e = null; //获取直营资源
        Workbook zy_target_i_e = null; //获取直营输出目标
        Workbook jm_source_i_e = null; //获取加盟资源
        Workbook jm_target_i_e = null; //获取加盟输出目标
        try {
            zy_source_i_e = WorkBookFactory.getWorkbook(zy_source);
            zy_target_i_e = WorkBookFactory.getWorkbook(zy_target);
            jm_source_i_e = WorkBookFactory.getWorkbook(jm_source);
            jm_target_i_e = WorkBookFactory.getWorkbook(jm_target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**2 读取表格文件*/
        // 输入文件
        Sheet zy_source_i_eSheetAt = zy_source_i_e.getSheetAt(0);
        Sheet jm_source_i_eSheetAt = jm_source_i_e.getSheetAt(0);
        // 输出文件
        Sheet zy_target_i_eSheetAt = zy_target_i_e.getSheetAt(0);
        Sheet zy_target_i_eSheetAt1 = zy_target_i_e.getSheetAt(1); //差异记录

        Sheet jm_target_i_eSheetAt = jm_target_i_e.getSheetAt(0);
        Sheet jm_target_i_eSheetAt1 = jm_target_i_e.getSheetAt(1); //差异记录
        /**3 资源表格文件格式初始化*/
//直营表格初处理 去除首尾
        zy_source_i_eSheetAt.shiftRows(1, zy_source_i_eSheetAt.getLastRowNum(), -1); // -1 向上挪一行 会将前面的覆盖掉  正数向下
        zy_source_i_eSheetAt.removeRow(zy_source_i_eSheetAt.getRow(zy_source_i_eSheetAt.getLastRowNum())); //删除最后一行

//        加盟表格初处理
        jm_source_i_eSheetAt.shiftRows(2, jm_source_i_eSheetAt.getLastRowNum(), -2);
        jm_source_i_eSheetAt.removeRow(jm_source_i_eSheetAt.getRow(jm_source_i_eSheetAt.getLastRowNum()));

        //筛选负责区域 及提取差异到目标表
        shaiXuan(zy_source_i_eSheetAt, zy_target_i_eSheetAt1,ConfigEnum.ZY_THEME.getMessage());
        zongJie(zy_target_i_eSheetAt); //总结统计数据

        shaiXuan(jm_source_i_eSheetAt, jm_target_i_eSheetAt1,ConfigEnum.JM_THEME.getMessage());
        zongJie(jm_target_i_eSheetAt);

        try {
            //写到输出文件中
            zy_target_i_e.write(new FileOutputStream(zy_target));
            jm_target_i_e.write(new FileOutputStream(jm_target));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                关闭流
                jm_source_i_e.close();
                zy_source_i_e.close();
                jm_target_i_e.close();
                zy_target_i_e.close();

                //改变加盟文档的名字
                File file =new File(path,"1.12门店销售流水完整性对比表至" + TimeUtil.getStringTime(ConfigEnum.DATE_112JM_TARGET_PATTERN.getMessage(), -3) + "--加盟.xlsx");
//                jm_target.
                jm_target.renameTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(TimeUtil.getStringTime(ConfigEnum.DATE_LOCAL_PATTERN.getMessage())+"导出结果完成");
    }

    /**
     * 将一行数据 复制到目标表
     * getLastCellNum 是从1开始的 计算的 所以会指针越界
     */
    private void copyRow(Sheet jg112, Row row) {
//        System.out.println("jg112-"+jg112.getLastRowNum());
        Row row1 = jg112.createRow(jg112.getLastRowNum() + 1);
//        System.out.println(row.getLastCellNum());
        for (int i = 0; i < row.getLastCellNum(); i++) {
            row1.createCell(i).setCellValue(row.getCell(i).toString());
        }
    }

    /**
     * 筛选数据
     *  theme   jm   zy
     */
    public void shaiXuan(Sheet source, Sheet target,String theme) {
        count=0;// 总门店数
        pc_count = 0; // 非本区域门店
        cy_count = 0; // 差异门店数量


        int ssje=ConfigEnum.ZY_THEME.getMessage().equals(theme)?6:5;
        int jybs=ConfigEnum.ZY_THEME.getMessage().equals(theme)?9:8;
        for (int i = 0; i <= source.getLastRowNum(); i++) {
            Row row = source.getRow(i);
            String id = row.getCell(0).toString();

            //没有差异的门店
            if (id.matches("^[2AG897].*")) {
                pc_count++;
                continue;
            }
            //有差异的门店

            if (row.getCell(ssje) == null || row.getCell(jybs) == null) continue;
            if (row.getCell(ssje).getNumericCellValue() != 0 || row.getCell(jybs).getNumericCellValue() != 0) {
                cy_count++;
                // 将差异门店记录到 jg
                copyRow(target, row);
                continue;
            }
        }

        count=source.getLastRowNum() +1;


    }

    /**
     * 总结统计数据   第一行 112时间   差异门店数   负责门店数
     */
    public  void  zongJie(Sheet target){
        Row row = target.createRow(target.getLastRowNum() + 1);
        row.createCell(0).setCellValue(TimeUtil.getStringTime(ConfigEnum.DATE_112TAGET_PATTERN.getMessage(),-3));
        row.createCell(1).setCellValue(cy_count); //差异门店
        row.createCell(2).setCellValue(count - pc_count); //负责门店
        System.out.println("总行数：" + count + "非负责区域：" + pc_count + "  负责门店：" + (count - pc_count) + "差异门店：" + cy_count);
//        记录总结
    }

}
