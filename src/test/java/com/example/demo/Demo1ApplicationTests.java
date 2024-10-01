package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Demo1ApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Test
	void test(){

		String sql="select * from User";//SQL查询语句
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		System.out.println(Arrays.toString(list.toArray()));
	}
	@Test
	void contextLoads() {
	}

}
