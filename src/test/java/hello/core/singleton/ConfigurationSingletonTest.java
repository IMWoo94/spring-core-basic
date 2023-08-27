package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;

public class ConfigurationSingletonTest {
	@Test
	void configurationTest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
		MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();

		System.out.println("memberService -> memberRepository" + memberRepository1);
		System.out.println("orderService -> memberRepository" + memberRepository2);
		System.out.println("memberRepository" + memberRepository);

		assertThat(memberRepository).isSameAs(memberRepository1);
		assertThat(memberRepository).isSameAs(memberRepository2);
		assertThat(memberRepository1).isSameAs(memberRepository2);

	}

	@Test
	void configurationDeep() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		//AppConfig도 스프링 빈으로 등록된다.
		AppConfig bean = ac.getBean(AppConfig.class);
		System.out.println("bean = " + bean.getClass());
		//출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$2103b6ba

		// @Configuration 어노테이션 없이 @bean 사용하게 되면, 위 에서 memberRepository는 싱글톤을 보장할 수 없다.
		// @Configutation + GGLIB 원리 확인 필요
	}
}
