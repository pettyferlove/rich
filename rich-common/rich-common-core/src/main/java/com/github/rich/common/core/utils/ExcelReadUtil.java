package com.github.rich.common.core.utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel读取工具
 * @author petty
 * @date 2017/6/27
 */
public class ExcelReadUtil{
	
	/**
	 * 根据Excel版本读取Excel
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook getWorkbook(InputStream in) throws IOException, InvalidFormatException {
		if (!in.markSupported()) {
			in = new PushbackInputStream(in, 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			return new HSSFWorkbook(in);
		}
		if (POIXMLDocument.hasOOXMLHeader(in)) {
			return new XSSFWorkbook(OPCPackage.open(in));
		}
		
		throw new IllegalArgumentException("你的excel版本目前poi解析不了！");
	}
	
	/**
	 * 获取数据
	 * @param in 输入流
	 * @return
	 */
	public static List<Map<String, Object>> getData(InputStream in , int numRowRead,Boolean oneSheet,int firstRow) {
		return ExcelReadUtil.getExcelData(in, numRowRead, oneSheet, firstRow);
	}
	
	/**
	 * 获取数据
	 * @param in 输入流
	 * @param numRowRead 开始读取行数
	 * @return
	 */
	public static List<Map<String, Object>> getExcelData(InputStream in,int numRowRead,Boolean oneSheet,int firstRow) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		
		try {
			//得到工作簿
			Workbook workbook = ExcelReadUtil.getWorkbook(in);
			//数据
			Map<String, Object> data = null;
			//表格
			Sheet sheet = null;
			//行
			Row row = null;
			//列数
			int numColnum = 0;
			//标志位数组
			String[] marks = null;
			int workbooklength = 1;
			if(!oneSheet) {
				workbooklength = workbook.getNumberOfSheets();
			}
			//循环读取表格
			for (int numSheet = 0; numSheet < workbooklength ; numSheet++) {
				sheet = (Sheet) workbook.getSheetAt(numSheet);
				if(sheet == null){
					continue;
				}
				
				//读取标志行信息，默认第一行为标志行
				int firstRownum = sheet.getFirstRowNum() + firstRow;
				row = sheet.getRow(firstRownum);
				numColnum = row.getPhysicalNumberOfCells();//2   获取不为空的列个数
				marks = new String[numColnum];//单位, AAA
				for (int i = 0; i < marks.length; i++) {
					marks[i] = row.getCell(i).getStringCellValue();
				}
				
				//循环读取行，由第二行开始                                  //获得数据的总行数    sheet.getLastRowNum
				for (int rowNum = numRowRead-1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					row = sheet.getRow(rowNum);
					if (row != null) {
						data = new HashMap<String, Object>(16);
						
						//循环读取列
						for (int i = 0; i < numColnum; i++) {
							//data.put(marks[i], row.getCell(i));//单位, AAA     row.getCell(i)  row行的第i列
							if(row.getCell(i)!=null){
								data.put(marks[i], ExcelReadUtil.toString(row.getCell(i)));
							}else{
								data.put(marks[i], "");
							}
							
						}
						
						dataList.add(data);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	
	/**
	 * 获取数据
	 * @param in 输入流
	 * @param numRowRead 开始读取行数
	 * @return
	 */
	public static List<List<String>> getExcel(InputStream in,int numRowRead) {
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			Workbook workbook = ExcelReadUtil.getWorkbook(in);//得到工作簿
			
			Sheet sheet = null; //表格
			
			//循环读取表格
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				sheet = (Sheet) workbook.getSheetAt(numSheet);
				if(sheet == null){
					continue;
				}
				//循环读取行                 //获得数据的总行数    sheet.getLastRowNum
				for (int rowNum = numRowRead-1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					int minColIx =row.getFirstCellNum();
					int maxColIx =row.getLastCellNum();
					List<String> rowlist = new ArrayList<String>();
					//循环读取列
					for (int colIx = minColIx; colIx < maxColIx; colIx++) {
						Cell cell=row.getCell(colIx);//row.getCell(ColIx)  row行的第i列
						if(cell == null){
							continue;
						}
						rowlist.add(ExcelReadUtil.toString(cell));
					}
					list.add(rowlist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 获取数据
	 * @param in 输入流
	 * @param numRowRead 开始读取行数
	 * @return
	 */
	public static List<List<String>> getExcel2(InputStream in,int numRowRead) {
		List<List<String>> list = new ArrayList<List<String>>();
		try {
			Workbook workbook = ExcelReadUtil.getWorkbook(in);//得到工作簿
			
			Sheet sheet = null; //表格
			
			//循环读取表格
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				sheet = (Sheet) workbook.getSheetAt(numSheet);
				if(sheet == null){
					continue;
				}
				//循环读取行                 //获得数据的总行数    sheet.getLastRowNum
				for (int rowNum = numRowRead-1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					int numColnum = row.getLastCellNum();// 获取不为空的列个数
					List<String> rowlist = new ArrayList<String>();
					//循环读取列
					for (int i = 0; i < numColnum; i++) {
						Cell cell=row.getCell(i);//row.getCell(ColIx)  row行的第i列
						if(cell == null){
							continue;
						}
						rowlist.add(ExcelReadUtil.toString(cell));
					}
					list.add(rowlist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/** 
	 * 对Excel的各个单元格的格式进行判断并转换 
	 */
	public static String toString(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
				return fmt.format(cell.getDateCellValue());
			}else{
				/*cell.setCellType(Cell.CELL_TYPE_STRING);
				return cell.getStringCellValue();*/
				//解决数字精度问题
				String cellValue = String.valueOf(cell.getNumericCellValue());
                DecimalFormat df = new DecimalFormat("#.#########");
                cellValue=df.format(Double.valueOf(cellValue));
                return cellValue;
			}
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
		case Cell.CELL_TYPE_FORMULA://公式转化
            //return cell.getNumericCellValue();
            try {
            	return  String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
				return String.valueOf(cell.getRichStringCellValue());
			}
		default:
			return cell.getStringCellValue();
		}
	}
	

	/**
	 * 测试
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println(rootPath);
		//InputStream in = new FileInputStream(rootPath+"/template/test/测试.xls");
		InputStream in = new FileInputStream("C:/Users/md/Desktop/供销社/excel/负责人考核信息.xlsx");
		
		List<Map<String, Object>> dataList = ExcelReadUtil.getData(in,3,true,0);
		
		for (Map<String, Object> map : dataList) {
			System.out.print(map.get("公司名称")+"  ");
			System.out.print(map.get("绩效考核时间")+"  ");
			System.out.print(map.get("任期激励")+"  ");
		}
	}
	
}