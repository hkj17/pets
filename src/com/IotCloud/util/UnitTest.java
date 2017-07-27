package com.IotCloud.util;

import java.text.DecimalFormat;

   
public class UnitTest {  
    public static void main(String[] args) {
    	double pi = 3.1415927;// 圆周率
        
        // 取一位整数
        System.out.println(new DecimalFormat("0").format(pi)); // 3
        // 取一位整数和两位小数
        System.out.println(new DecimalFormat("0.00").format(pi)); // 3.14        
        // 取两位整数和三位小数，整数不足部分以0填补。
        System.out.println(new DecimalFormat("00.000").format(pi)); // 03.142
        // 取所有整数部分
        System.out.println(new DecimalFormat("#").format(pi)); // 3
        // 以百分比方式计数，并取两位小数
        System.out.println(new DecimalFormat("#.##%").format(pi)); // 314.16%
    }  
}  