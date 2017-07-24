package com.IotCloud.util;
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
   
public class PoiTest {  
    public static void main(String[] args) {  
        //readXml("D:/test.xlsx");  
        //System.out.println("-------------");  
        //readXml("d:\\评分标准.xls");  
    	//System.out.println((2 & 3) == 0);
    	double pi = 31331322113.1415927;// 圆周率
        
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
    public static String readXml(String fileName){  
        boolean isE2007 = false;    //判断是否是excel2007格式  
        if(fileName.endsWith(".xlsx")) { 
            isE2007 = true;  
        }else if(fileName.endsWith(".xls")) {
        	isE2007 = false;
        }else {
        	return "后缀名必须为.xls或者.xlsx";
        }
        Workbook wb  = null; 
        try {  
            InputStream input = new FileInputStream(fileName);  //建立输入流  
             
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007)  
                wb = new XSSFWorkbook(input);  
            else  
                wb = new HSSFWorkbook(input);  
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            if(!rows.hasNext()) {
            	//空文档
            	return "excel 空文档";
            }else {
            	//跳过第一行
            	rows.next();
            }
            
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                while (cells.hasNext()) {  
                    Cell cell = cells.next();  
                    System.out.println("Cell #" + cell.getColumnIndex());  
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                    case HSSFCell.CELL_TYPE_NUMERIC:  
                        System.out.println(cell.getNumericCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING:  
                        System.out.println(cell.getStringCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN:  
                        System.out.println(cell.getBooleanCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA:  
                        System.out.println(cell.getCellFormula());  
                        break; 
                    case HSSFCell.CELL_TYPE_BLANK:
                    	break;
                    default:  
                        System.out.println("unsuported sell type");  
                    break;  
                    }  
                }  
            }  
            return "成功";
        } catch (IOException ex) {  
            ex.printStackTrace(); 
            return "IO错误";
        }  finally {
        	
        }
    }  
}  