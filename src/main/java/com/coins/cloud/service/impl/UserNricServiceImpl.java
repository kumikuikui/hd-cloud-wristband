package com.coins.cloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.coins.cloud.bo.UserNricBo;
import com.coins.cloud.dao.UserNricDao;
import com.coins.cloud.service.UserNricService;
import com.coins.cloud.vo.UserNricVo;
import com.csvreader.CsvReader;
import com.hlb.cloud.util.CSVUtils;

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
		        BigDecimal bd = new BigDecimal(csvFileList.get(row)[0]);
		        String nric = String.valueOf(Long.parseLong(bd.toPlainString()));
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
				int existResu = userNricDao.existByNricNum(nric);
				if(existResu == 0){//不存在，才新增
					userNricDao.save(userNricVo);
					total++;
				}
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

	@Override
	public UserNricBo getUserNricDetail(int id) {
		return userNricDao.getUserNricDetail(id);
	}

	@Override
	public int updateUserNric(UserNricVo userNricVo) {
		return userNricDao.updateUserNric(userNricVo);
	}

	@Override
	public File reportUserNric(String csvFilePath, String fileName) {
		List<UserNricBo> userNricList = userNricDao.getAllUserNric();
		try {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("nric", "NRIC");
			map.put("name", "Name");
			map.put("oldIc", "Old IC");
			map.put("birthDate", "Date Of Birth");
			map.put("birthPlace", "Place Of Birth");
			map.put("gender", "Gender");
			map.put("address1", "Address 1");
			map.put("address2", "Address 2");
			map.put("address3", "Address 3");
			map.put("postcode", "Postcode");
			map.put("city", "City");
			map.put("state", "State");
			map.put("race", "Race");
			map.put("religion", "Religion");
			map.put("citizenship", "Citizenship");
			map.put("issueDate", "Date Of Issuance");
			map.put("emorigin", "E.M. Origin");
			map.put("handcode", "Hand Code");
			map.put("mimageUrl", "MyKad Image path");
			map.put("cameraUrl", "Camera Path");
			map.put("indicatorStatus", "Indicator Status");
			File file = CSVUtils.createCSVFile(userNricList, map, csvFilePath, fileName);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
