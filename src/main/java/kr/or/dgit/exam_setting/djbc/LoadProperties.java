package kr.or.dgit.exam_setting.djbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
	private Properties properties;

	public LoadProperties() {
		properties = new Properties();
		// conf.properties라는 파일을 읽어옴
		configAsProperties();
	}

	private void configAsProperties() {
		ClassLoader context = Thread.currentThread().getContextClassLoader();

		try (InputStream inputstream = context.getResourceAsStream("conf.properties");) {

			properties.load(inputstream);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}
}
