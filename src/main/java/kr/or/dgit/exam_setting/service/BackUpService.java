package kr.or.dgit.exam_setting.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import kr.or.dgit.exam_setting.dao.ExecuteSql;
import kr.or.dgit.exam_setting.djbc.LoadProperties;

public class BackUpService implements DaoService {
	private static final BackUpService instance = new BackUpService();

	public static BackUpService getInstance() {
		return instance;
	}

	private BackUpService() {
	}

	@Override
	public void service() {
		LoadProperties loadProperties = new LoadProperties();
		Properties properties = loadProperties.getProperties();

		ExecuteSql.getInstance().execSQL("use " + properties.getProperty("dbname"));

		checkBackupDir();// 백업 파일의 존재유무 확인

		String[] tables = properties.get("tables").toString().split(",");

		for (String tblName : tables) {// 한개씩 불러와짐
			String sql = String.format("select * from %s", tblName);
			// System.out.println(sql);
			exportData(sql, tblName);
		}
	}

	private void exportData(String sql, String tblName) {
		try {
			ResultSet rs = ExecuteSql.getInstance().execQuerySQL(sql);
			// 컬럼이 갯수가 다르므로 갯수를 먼저 알아내야함
			int columnCnt = rs.getMetaData().getColumnCount();

			// 하나의 문자열에 담음
			StringBuilder sb = new StringBuilder();
			// System.out.println("column Cnt "+ columnCnt);

			// 컬럼의 갯수만큼 반복문 돌리기
			while (rs.next()) {
				for (int i = 1; i <= columnCnt; i++) {
					sb.append(rs.getObject(i) + ",");// 컬럼은 쉼표로 구분
				}
				// 문장의 마지막에 있는 ,삭제하기
				sb.replace(sb.length() - 1, sb.length(), "");
				// 한줄 띄우기
				sb.append("\r\n");
			}

			writeBackupFile(sb.toString(), tblName);// 파일을 만들어라!!

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void writeBackupFile(String result, String tblName) {
		// 경로-BackupFiles
		String resPath = System.getProperty("user.dir") + "\\BackupFiles\\" + tblName + ".txt";

		// 여기서 문제인 \를 /로 바꾸줌
		resPath = resPath.replace("\\", "/");

		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(resPath), "euc-kr");) {
			osw.write(result); // result를 쓰면 됨
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkBackupDir() {
		File backupDir = new File(System.getProperty("user.dir") + "\\BackupFiles");
		// 백업파일의 존재유무 확인
		if (backupDir.exists()) {
			// System.out.println("BackupFiles 존재");
			// 파일이 존재할 경우
			for (File file : backupDir.listFiles()) {
				file.delete();// 파일이 존재할 경우 삭제함
				System.out.printf("%s Delete Success! %n", file.getName());
			}
		} else {
			// System.out.println("BackupFiles 존재하지 않음");
			// 존재하지 않을 경우 파일을 만들어줌
			backupDir.mkdir();
			System.out.printf("%s Create Success! %n", backupDir.getName());
		}

	}

	@Override
	public void procedure() {
		// TODO Auto-generated method stub
		
	}
}
