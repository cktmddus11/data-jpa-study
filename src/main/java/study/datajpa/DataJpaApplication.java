package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;


@EnableJpaAuditing //(modifyOnCreate = true) 기본값으로 true, 엔티티가 수정될때마다 수정시간이 업데이트됨.
@SpringBootApplication
public class DataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataJpaApplication.class, args);
    }


    // 등록자 , 수정자를 처리해주는 AuditorAware 스프링 빈 등록
    // 실무에서는 세션정보나, 스프링 시큐리티 로그인 정보에서 ID를 받음.
    @Bean
    public AuditorAware<String> auditorProvider() {
		/*return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of(UUID.randomUUID().toString());
			}
		};*/
        return () -> Optional.of(UUID.randomUUID().toString());
    } // 한개의 추상메서드를 가진 인터페이스 AuditorAware 여서 람다식으로 구현가능.
}
