package kr.or.dgit.exam_setting.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import kr.or.dgit.exam_setting.dao.ExecuteSql;

public class InitService implements DaoService {
	private static final InitService instance = new InitService();

	public static InitService getInstance() {
		return instance;
	}

	private InitService() {
	}

	@Override
	public void service() {
		// create_sql.txt 파일을 읽어옴
		File f = new File(System.getProperty("user.dir") + "\\resources\\create_sql.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(f));) {
			StringBuilder sb = new StringBuilder(); // String이라는 글자를 한곳에 계속 추가시킴
			String line = null;
			while ((line = br.readLine()) != null) { // 글이 들어가 있다면
				if (!line.isEmpty() && !line.startsWith("--")) { // 그게 빈공간이거나 문장의 처음에 --주석표시가 있는게 아니면
					sb.append(line);// 문장에 넣음
				}

				// 여기서 테이블을 만드는 문장이 실행이됨
				if (line.endsWith(";")) { // 문장이 ;끝나면
					ExecuteSql.getInstance().execSQL(sb.toString()); // 그 문장을 execSQL함수를 실행함
					sb.setLength(0);// 먼저 불러온 문장을 덮어쓰지 말고 다음줄로 보내줌
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
