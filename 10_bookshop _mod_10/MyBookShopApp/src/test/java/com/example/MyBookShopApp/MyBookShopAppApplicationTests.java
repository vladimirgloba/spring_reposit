package com.example.MyBookShopApp;

import org.assertj.core.api.AssertionsForClassTypes;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MyBookShopAppApplicationTests {

	@Value("${auth.secret}")
	String authSecret;

	private final MyBookShopAppApplication application;

	@Autowired
	public MyBookShopAppApplicationTests(MyBookShopAppApplication application) {
		this.application = application;
	}

	@Test
	void contextLoads() {
		assertNotNull(application);
	}

	@Test
	void verifyAuthSecret(){
		Boolean flag=authSecret.length()>0?true:false;
		AssertionsForClassTypes.assertThat(flag).isTrue();
	}


}
