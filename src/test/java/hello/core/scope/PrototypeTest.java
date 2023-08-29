package hello.core.scope;

import static org.assertj.core.api.Assertions.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

	@Test
	void prototpyeBeanFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

		System.out.println("find prototypeBean1");
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

		System.out.println("find prototypeBean2");
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

		System.out.println("prototypeBean1 = " + prototypeBean1);
		System.out.println("prototypeBean2 = " + prototypeBean2);

		assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

		// prototype 의 빈의 경우 수동으로 소멸 초기화 콜백을 호출해야한다.
		prototypeBean2.destory();

		ac.close();
	}

	@Scope("prototype")
	static class PrototypeBean {

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init");
		}

		@PreDestroy
		public void destory() {
			System.out.println("PrototypeBean.destory");
		}
	}
}
