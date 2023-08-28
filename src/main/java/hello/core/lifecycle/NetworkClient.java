package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
		connect();
		call("초기화 연결 메시지");
	}

	//서비스 시작시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}

	public void call(String message) {
		System.out.println("call: " + url + " message = " + message);
	}

	//서비스 종료시 호출
	public void disConnect() {
		System.out.println("close: " + url);
	}

	// InitializingBean, DisposableBean 두 개의 인터페이스는 스프링에서 제공하는 전용 입니다.
	// 또한 외부 라이브러리에 대해서 수정 작업을 할 수 없다면 해당 방식으로 지정할 수 없다.
	// 스프링 초장기에 사용되며, 이거와 대비되는 것으로는 @Bean 어노테이션의 initMethod, detroyMethod를 사용하는 방식이 자주 사용된다.
	@Override
	// 의존관계 주입이 끝나면 호출 해주겟다라는 의미
	public void afterPropertiesSet() throws Exception {
		System.out.println("NetworkClient.afterPropertiesSet InitializingBean init method");
		connect();
		call("초기화 연결 메시지");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("NetworkClient.destroy DisposableBean destory method");
		disConnect();
	}

	// @Bean의 initMethod에 사용 될 초기화 콜백 메소드
	public void init() {
		System.out.println("NetworkClient.init method @Bean initMethod 지정");
		connect();
		call("초기화 연결 메시지");
	}

	// @Bean의 destroyMethod에 사용 될 소멸 콜백 메소드
	// destoryMethod는 조금 특이하게 추측이라는 기능을 사용해서 별도 지정하지 않아도
	// close, shutdown 같은 단어를 소멸 콜백으로 인지한다.
	public void close() {
		System.out.println("NetworkClient.close method @Bean destroyMethod 지정");
		disConnect();
	}

	// @PostConstruct, @PreDestroy
	@PostConstruct
	public void postConstructInit() {
		System.out.println("NetworkClient.postConstructInit @PostConstruct method");
		connect();
		call("초기화 연결 메시지");
	}

	@PreDestroy
	public void preDestroyclose() {
		System.out.println("NetworkClient.preDestroyclose method @PreDestroy method");
		disConnect();
	}
}
