package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// ComponentScan의 경우 default로는 현재 설정 파일의 패키지와 하위 패키지의 대상들을 Base로 찾아서 스캔을 한다.
// basePackages, basePackageClasses 로 스캔할 위치를 정할 수 있다.
@ComponentScan(
	excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

	// // Bean 등록 시 자동과 수동이 충돌나는 경우 수동이 우선권을 가집니다.
	// @Bean(name = "memoryMemberRepository")
	// public MemberRepository memberRepository() {
	// 	return new MemoryMemberRepository();
	// }

}
