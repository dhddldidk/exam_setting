package kr.or.dgit.exam_setting;

import java.util.Properties;

import kr.or.dgit.exam_setting.djbc.DBCon;
import kr.or.dgit.exam_setting.djbc.LoadProperties;

import java.util.Map.Entry;

public class TestMain {

	public static void main(String[] args) {
		testDBConnection();

	}

	private static void testDBConnection() {
		// DB에 연결해서 연결이 잘 되었느지
		DBCon dbCon = DBCon.getInstance();
		System.out.println(dbCon);

		// 파일들이 잘 출력이 되는지 확인하는거
		LoadProperties lp = new LoadProperties();// resource에 있는 파일을 불러옴
		Properties pro = lp.getProperties();// conf.properties파일이 불려옴

		for (Entry<Object, Object> e : pro.entrySet()) {
			System.out.printf("%s : %s%n", e.getKey(), e.getValue());
		}

		// tables=title,department,employee를 ,기준으로 배열로 저장하겠다 테이블명이 잘 출력이되는지 실험
		String[] values = pro.get("tables").toString().split(",");
		for (String v : values) {
			System.out.println(v);
		}

	}

}
