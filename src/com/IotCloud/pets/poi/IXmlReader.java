package com.IotCloud.pets.poi;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;

public interface IXmlReader<T> {
	public PoiRecord<T> readXmlRecord(String adminId, int rowNum, Iterator<Cell> cells, DecimalFormat df);
	
	public PoiListRecord<T> loadFromXml(String adminId, InputStream input) throws IOException;
}
