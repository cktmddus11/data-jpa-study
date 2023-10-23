package study.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
// @EnableJpaRepositories(basePackages = "study.datajpa.repository") // 스프링 부트가 알아서 컴포넌트 스캔 처리함.
class DataJpaApplicationTests {

	@Test
	void contextLoads() {
	}

}
