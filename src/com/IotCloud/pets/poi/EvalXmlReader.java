package com.IotCloud.pets.poi;

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

import com.IotCloud.pets.model.Evaluation;
import com.IotCloud.pets.util.CommonUtil;

public class EvalXmlReader implements IXmlReader<Evaluation> {

	private static Logger logger = Logger.getLogger(EvalXmlReader.class);

	@SuppressWarnings("deprecation")
	@Override
	public PoiRecord<Evaluation> readXmlRecord(String adminId, int rowNum, Iterator<Cell> cells, DecimalFormat df) {
		Evaluation eval = new Evaluation();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			int cellNo = cell.getColumnIndex();
			eval.setEvalId(CommonUtil.generateRandomUUID());
			if (cellNo == 0) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					String itemName = cell.getStringCellValue();
					// override testId field in Evaluation
					eval.setTestId(itemName);
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，项目名称不是字符串";
					logger.error(message);
					return new PoiRecord<Evaluation>(null, message);
				}
			} else if (cellNo == 1) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					if ("男".equals(cell.getStringCellValue())) {
						eval.setType(1);
					} else if ("女".equals(cell.getStringCellValue())) {
						eval.setType(2);
					} else {
						String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别必须是男或者女";
						logger.error(message);
						return new PoiRecord<Evaluation>(null, message);
					}
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，性别不是字符串";
					logger.error(message);
					return new PoiRecord<Evaluation>(null, message);
				}
			} else if (cellNo == 2) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					eval.setLowerBound(Double.parseDouble(df.format(cell.getNumericCellValue())));
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，最低成绩不是数字";
					logger.error(message);
					return new PoiRecord<Evaluation>(null, message);
				}
			} else if (cellNo == 3) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					eval.setUpperBound(Double.parseDouble(df.format(cell.getNumericCellValue())));
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，最高成绩不是数字";
					logger.error(message);
					return new PoiRecord<Evaluation>(null, message);
				}
			} else if (cellNo == 4) {
				if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					eval.setPoint(Double.parseDouble(df.format(cell.getNumericCellValue())));
				} else {
					String message = "第" + rowNum + "行, 第" + (cellNo + 1) + "列出错， 添加失败，分值不是数字";
					logger.error(message);
					return new PoiRecord<Evaluation>(null, message);
				}
			} else {
				break;
			}

		}
		return new PoiRecord<Evaluation>(eval, "添加成功");
	}

	@Override
	public PoiListRecord<Evaluation> loadFromXml(String adminId, InputStream input) throws IOException {
		Workbook wb = new HSSFWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();
		} else {
			wb.close();
			logger.warn("添加失败，excel文档为空");
			return new PoiListRecord<Evaluation>(null, "添加失败，excel文档为空");
		}

		List<Evaluation> evalList = new ArrayList<Evaluation>();
		DecimalFormat df = new DecimalFormat("0.00");
		int rowNum = 1;
		while (rows.hasNext()) {
			Row row = rows.next();
			rowNum++;
			Iterator<Cell> cells = row.cellIterator();
			PoiRecord<Evaluation> record = readXmlRecord(adminId, rowNum, cells, df);
			if (record.getRecord() == null) {
				wb.close();
				return new PoiListRecord<Evaluation>(null, record.getMessage());
			} else {
				evalList.add(record.getRecord());
			}
		}
		wb.close();
		return new PoiListRecord<Evaluation>(evalList, "添加成功");
	}

}
