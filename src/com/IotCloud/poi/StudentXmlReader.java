package com.IotCloud.poi;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.IotCloud.model.Student;
import com.IotCloud.util.CommonUtil;

public class StudentXmlReader implements IXmlReader<Student> {

	private static Logger logger = Logger.getLogger(StudentXmlReader.class);

	@SuppressWarnings("deprecation")
	@Override
	public PoiRecord<Student> readXmlRecord(String adminId, int rowNum, Iterator<Cell> cells, DecimalFormat df) {
		Student student = new Student();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			int cellNo = cell.getColumnIndex();
			student.setStudentId(CommonUtil.generateRandomUUID());
			student.setAdminId(adminId);

			if (cellNo == 0) {// 学生姓名
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					student.setStudentName(cell.getStringCellValue());
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，考生姓名不是字符串";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else if (cellNo == 1) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setStudentNo(Integer.parseInt(df.format(cell.getNumericCellValue())));
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，考生学号不是数字";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else if (cellNo == 2) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					student.setTesterNo(Long.parseLong(df.format(cell.getNumericCellValue())));
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，考生考号不是数字";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else if (cellNo == 3) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					if ("男".equals(cell.getStringCellValue())) {
						student.setGender(1);
					} else if ("女".equals(cell.getStringCellValue())) {
						student.setGender(2);
					} else {
						String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别必须是男或者女";
						logger.error(message);
						return new PoiRecord<Student>(null, message);
					}
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别不是字符串";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else if (cellNo == 4) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					student.setSchoolName(cell.getStringCellValue());
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，学校名称不是字符串";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else if (cellNo == 5) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					student.setClassName(cell.getStringCellValue());
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，班级名称不是字符串";
					logger.error(message);
					return new PoiRecord<Student>(null, message);
				}
			} else {
				break;
			}
		}
		return new PoiRecord<Student>(student, "添加成功");
	}

	@Override
	public PoiListRecord<Student> loadFromXml(String adminId, InputStream input) throws IOException {
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();
		} else {
			wb.close();
			logger.warn("添加失败，excel文档为空");
			return new PoiListRecord<Student>(null, "添加失败，excel文档为空");
		}

		List<Student> studentList = new ArrayList<Student>();
		DecimalFormat df = new DecimalFormat("0");
		int rowNum = 1;
		while (rows.hasNext()) {
			Row row = rows.next();
			rowNum++;
			Iterator<Cell> cells = row.cellIterator();
			PoiRecord<Student> record = readXmlRecord(adminId, rowNum, cells, df);
			if (record.getRecord() == null) {
				wb.close();
				return new PoiListRecord<Student>(null, record.getMessage());
			} else {
				studentList.add(record.getRecord());
			}
		}
		wb.close();
		return new PoiListRecord<Student>(studentList, "添加成功");
	}

}
