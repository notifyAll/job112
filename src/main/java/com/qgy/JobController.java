package com.qgy;

/**
 * 程序入口
 */
public class JobController {
    public static void main(String[] args) {
//        for (int i = 0; i < args.length; i++) {
//            System.out.println(args[i]);
//
//        }
        //执行网络连通率 java -jar job112.jar ip
//        new PoiIP().pip();
//        new Poi112().p112_2();
        if (args!=null&&args.length>=1&&"ip".equals(args[0])){
            new PoiIP().pip();
            return; //跳出程序
        }
        //112程序 java -jar job112.jar 112
        if (args!=null&&args.length>=1&&"112".equals(args[0])){
            new Poi112().p112_2();
            return; //跳出程序
        }
//

    }
}
