package com.utudo.hwwd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utudo.hwwd.controllers.MainController;
import com.utudo.hwwd.helpers.HwDatas;
import com.utudo.hwwd.helpers.HwMatch;
import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.Annonce;
import com.utudo.hwwd.models.Needs;
import com.utudo.hwwd.models.User;
import com.utudo.hwwd.models.Ville;
import com.utudo.hwwd.models.extModel.Village;
import com.utudo.hwwd.service.VilleService;
import com.utudo.hwwd.service.impl.AnnonceServiceImpl;
import com.utudo.hwwd.service.impl.NeedsServiceImpl;
import com.utudo.hwwd.service.impl.UserServiceImpl;
import com.utudo.hwwd.service.impl.VilleServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class HwwdApplicationTests extends HwwdMainTest{


	@Resource
	AnnonceServiceImpl annonceService;

	@Resource
	NeedsServiceImpl needsService;

	@Resource
	UserServiceImpl userService;

	@Resource
	VilleServiceImpl villeService;



//	@Test
	void contextLoads() {

	}

	private ArrayList<String> sdates = new ArrayList<>();
	void setDate(){
//		List<Date> list = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020,10,10);
		Date minDate = calendar.getTime();  //最小时间
		calendar.set(2021,2,1);
		Date maxDate = calendar.getTime();//最大时间
		//计算两个时间点相隔多少天
		int totalDays = (int) ((maxDate.getTime() - minDate.getTime()) / (1000 * 60 * 60 * 24));
		calendar.setTime(minDate);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		for(int i = 0;i<=totalDays;i++){
			if(i!=0){
				//天数加1
				calendar.add(Calendar.DAY_OF_MONTH,1);
			}
//			.add(calendar.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.error("时间哦："+sdf.format(calendar.getTime()));
			sdates.add(sdf.format(calendar.getTime()));
		}


	}


//	@Test
//	@Transactional
//	@Rollback(value = false)
//	@Order(1)
	void PrepareData(){
		setDate();
		Random rand = new Random();

		for (int i = 0; i < 2000; i++) {
			float budget = (float) ((rand.nextInt(40) + 1) * 100);
			int location = rand.nextInt(19) + 1;

			int rtype = rand.nextInt(2);

			int uid = rand.nextInt(12);
			int trans = rand.nextInt(4);
			int status = rand.nextInt(HwDatas.dataNeedsStatus.size());
			Needs needs = new Needs();
			needs.setBudget(budget);
			needs.setRtype(rtype);
			needs.setLocation(location);
			needs.setUid(uid);
			needs.setTrans(trans);
			needs.setStatus(status);
			HashMap<Integer,Integer> base = new HashMap<Integer,Integer>();
			int baseRand = rand.nextInt(6);
			switch (baseRand){
				case 0:
					base.put(0,0);
					break;
				case 1:
					base.put(0,0);
					base.put(1,1);
					break;
				case 2:
					base.put(0,0);
					base.put(2,2);
					break;
				case 3:
					base.put(1,1);
					base.put(2,2);
					break;
				case 4:
					base.put(1,1);
					break;
				case 5:
					base.put(2,2);
					break;
				default:
					base.put(0,0);
					base.put(1,1);
					base.put(2,2);
			}

			needs.setBase(base);
//
//			HashMap<Integer,Integer> envir = new HashMap<Integer,Integer>();
//			int envirRand = rand.nextInt(HwDatas.dataFunctions.size());

			needs.setSdate(sdates.get(rand.nextInt(sdates.size())));
			logger.error("存入系统："+needs.toString());
			needsService.save(needs);

		}


	}
//
//	@Test
//	@Order(2)
	void testMatch(){
		Optional<Annonce> annonce	= annonceService.findAnnonceById(1);
		if (annonce.isPresent()){
			Annonce ann = annonce.get();
			HwMatch hwMatch = new HwMatch(ann);
			hwMatch.setNeedsService(needsService);
			hwMatch.matchData();
//			logger.error("匹配的数据："+hwMatch.getNeedsWeek().toString());
		}
	}
//
//	@Test
//	@Rollback(value = false)
//	@Order(1)
	void createUser(){
		for (int i = 0; i < 12; i++) {
			User user = new User();
			user.setAddress("19 bonnard balle");
			user.setBirthday("1998-02-09");
			user.setEmail("z5138812"+i+"4@icloud.com");
			user.setPassword("123456"+i);
			user.setTelephone("065675925"+i);
			user.setNom("li"+i);
			user.setPrenom("sim"+i);
			user.setIdentity(0);
			user.setSalary(0);
			userService.save(user);
		}

	}


//	@Test
//	@Rollback(value = false)
//	@Order(1)
	void createVille() throws JsonProcessingException {
		String pathname = "/Library/WebServer/Documents/france.txt";
		String data = HwTools.readJsonFile(pathname);
		ObjectMapper objectMapper = new ObjectMapper();

		List<Village> villages = objectMapper.readValue(data, new TypeReference<List<Village>>(){});
		villages.forEach(n->{
			logger.error(n.toString());
			Ville ville = new Ville();
			ville.setCode(Integer.parseInt(n.getCodep().substring(0,2)));
			ville.setPostcode(n.getCodep());
			ville.setName(n.getName());
			ville.setLabel(n.getLibelle());
			String str = n.getGps();
			if (str!=null&&!str.equals("")){
				String[] latlng;
				String delimeter = ", ";
				latlng = str.split(delimeter);
				ville.setLat(latlng[0]);
				ville.setLng(latlng[1]);
			}else{
				logger.error("就是这里出错了");
			}
			villeService.save(ville);

		});
	}





}
