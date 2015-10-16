package br.gov.sibbr.services.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisUtils {
	private static Logger logger = LoggerFactory.getLogger(MyBatisUtils.class);

	private static SqlSessionFactory sqlSessionFactory;

	private MyBatisUtils() {
	}

	static {
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			logger.error("Excpetion: {}", e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
	public static SqlSession getSQLSession() {
		return sqlSessionFactory.openSession();
	}
}
