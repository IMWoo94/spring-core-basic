package hello.core.scope;

import static org.assertj.core.api.Assertions.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

	@Test
	void prototypeFind() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			PrototypeBean.class);

		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);

		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);

	}

	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			PrototypeBean.class, ClientBean.class);

		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		assertThat(clientBean1.logic()).isEqualTo(1);
		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		assertThat(clientBean2.logic()).isEqualTo(1);
	}

	@Scope("prototype")
	static class PrototypeBean {

		private int count = 0;

		public void addCount() {
			count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}

		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}

	@Scope("singleton")
	//@RequiredArgsConstructor
	static class ClientBean {
		//private final PrototypeBean prototypeBean;

		// ObjectProvider , ObjectFactory => 스프링에서 제공하는 DL ( 의존관계 검색 ) 을 제공하는 간단한 인터페이스
		// Provider Java 표준으로 DL 제공
		// 위에 것을 사용하게 되면 get(), getObject() 메소드를 통해서 관련 Bean을 메소드 호출 시 찾아서 가져온다.
		// prototpy의 경우 해당 호출 시 새로운 객체를 던져 줄 것이다.
		@Autowired
		private Provider<PrototypeBean> prototypeBeanObjectProvider;

		// @Autowired
		// public ClientBean(PrototypeBean prototypeBean) {
		// 	this.prototypeBean = prototypeBean;
		// }

		public int logic() {
			PrototypeBean prototypeBean = prototypeBeanObjectProvider.get();
			System.out.println(prototypeBean);
			prototypeBean.addCount();
			return prototypeBean.getCount();
		}
	}
}
