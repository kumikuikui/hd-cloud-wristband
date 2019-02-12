package com.coins.cloud.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.dao.UserNricDao;
import com.coins.cloud.service.UserNricService;
import com.coins.cloud.vo.UserNricVo;
import com.csvreader.CsvReader;

@Service
public class UserNricServiceImpl implements UserNricService {
	
	@Inject
	private UserNricDao userNricDao;

	@Override
	public int save(InputStream inputStream) {
		// 用来保存数据
		int total=0;
	    ArrayList<String[]> csvFileList = new ArrayList<String[]>();
		CsvReader reader = new CsvReader(inputStream,Charset.forName("UTF-8"));
		try {
			reader.readHeaders();
			while (reader.readRecord()) {
				System.out.println(reader.getRawRecord());
				csvFileList.add(reader.getValues());
			}
			reader.close();
			// 遍历读取的CSV文件
			UserNricVo userNricVo = null;
		    for (int row = 0; row < csvFileList.size(); row++) {
		        String nric = csvFileList.get(row)[0];
				String name = csvFileList.get(row)[1];
				String oldIc = csvFileList.get(row)[2];
				String birthDate = csvFileList.get(row)[3];
				String birthPlace = csvFileList.get(row)[4];
				String gender = csvFileList.get(row)[5];
				String address1 = csvFileList.get(row)[6];
				String address2 = csvFileList.get(row)[7];
				String address3 = csvFileList.get(row)[8];
				String postcode = csvFileList.get(row)[9];
				String city = csvFileList.get(row)[10];
				String state = csvFileList.get(row)[11];
				String race = csvFileList.get(row)[12];
				String religion = csvFileList.get(row)[13];
				String citizenship = csvFileList.get(row)[14];
				String issueDate = csvFileList.get(row)[15];
				String emorigin = csvFileList.get(row)[16];
				String handcode = csvFileList.get(row)[17];
				String mimageUrl = csvFileList.get(row)[18];
				String cameraUrl = csvFileList.get(row)[19];
				String indicatorStatus = csvFileList.get(row)[20];
				userNricVo = UserNricVo.builder().nric(nric).name(name)
						.oldIc(oldIc).birthDate(birthDate).birthPlace(birthPlace)
						.gender(gender).address1(address1).address2(address2)
						.address3(address3).postcode(postcode).city(city)
						.state(state).race(race).religion(religion)
						.citizenship(citizenship).issueDate(issueDate)
						.emorigin(emorigin).handcode(handcode).mimageUrl(mimageUrl)
						.cameraUrl(cameraUrl).indicatorStatus(indicatorStatus).build();
				userNricDao.save(userNricVo);
				total++;
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	@Override
	public List<UserNricBo> getUserNricList(int pageIndex, int pageSize) {
		return userNricDao.getUserNricList(pageIndex, pageSize);
	}

	@Override
	public int getTotal() {
		return userNricDao.getTotal();
	}

	
	/**
	 * 
	 * @Title: importExcle
	 * @param:
	 * @Description: 读取解析excel内容
	 * @return List<Shop>
	 */
	private int importExcle(InputStream inputStream) {
		int total=0;
		try {
			Workbook workbook = new HSSFWorkbook(inputStream);
			//Workbook workbook = WorkbookFactory.create(inputStream);
			UserNricVo userNricVo = null;
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet hssfSheet = workbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					Row hssfRow = hssfSheet.getRow(rowNum);
					Cell nric = hssfRow.getCell(0);
					Cell name = hssfRow.getCell(1);
					Cell oldIc = hssfRow.getCell(2);
					Cell birthDate = hssfRow.getCell(3);
					Cell birthPlace = hssfRow.getCell(4);
					Cell gender = hssfRow.getCell(5);
					Cell address1 = hssfRow.getCell(6);
					Cell address2 = hssfRow.getCell(7);
					Cell address3 = hssfRow.getCell(8);
					Cell postcode = hssfRow.getCell(9);
					Cell city = hssfRow.getCell(10);
					Cell state = hssfRow.getCell(11);
					Cell race = hssfRow.getCell(12);
					Cell religion = hssfRow.getCell(13);
					Cell citizenship = hssfRow.getCell(14);
					Cell issueDate = hssfRow.getCell(15);
					Cell emorigin = hssfRow.getCell(16);
					Cell handcode = hssfRow.getCell(17);
					Cell mimageUrl = hssfRow.getCell(18);
					Cell cameraUrl = hssfRow.getCell(19);
					Cell indicatorStatus = hssfRow.getCell(20);
					userNricVo = UserNricVo.builder().nric(getValue(nric)).name(getValue(name))
							.oldIc(getValue(oldIc)).birthDate(getValue(birthDate)).birthPlace(getValue(birthPlace))
							.gender(getValue(gender)).address1(getValue(address1)).address2(getValue(address2))
							.address3(getValue(address3)).postcode(getValue(postcode)).city(getValue(city))
							.state(getValue(state)).race(getValue(race)).religion(getValue(religion))
							.citizenship(getValue(citizenship)).issueDate(getValue(issueDate))
							.emorigin(getValue(emorigin)).handcode(getValue(handcode)).mimageUrl(getValue(mimageUrl))
							.cameraUrl(getValue(cameraUrl)).indicatorStatus(getValue(indicatorStatus)).build();
					userNricDao.save(userNricVo);
					total++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	private static String getValue(Cell no) {
		if(no==null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (no.getCellType() == no.CELL_TYPE_BOOLEAN) {
			return String.valueOf(no.getBooleanCellValue());
		} else if (no.getCellType() == no.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(no)) {// 日期格式
				Date theDate = no.getDateCellValue();
				return simpleDateFormat.format(theDate);
			} else {
				DecimalFormat df = new DecimalFormat("0");
				String whatYourWant = df.format(no.getNumericCellValue());
				return whatYourWant;// String.valueOf((int)no.getNumericCellValue());
			}
		} else {
			return String.valueOf(no.getStringCellValue());
		}
	}

	@Override
	public UserNricBo getUserNricDetail(int id) {
		return userNricDao.getUserNricDetail(id);
	}

	@Override
	public int updateUserNric(UserNricVo userNricVo) {
		return userNricDao.updateUserNric(userNricVo);
	}

}
