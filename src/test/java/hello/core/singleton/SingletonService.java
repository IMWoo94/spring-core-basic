package hello.core.singleton;

public class SingletonService {
	// 1. static 영역에 객체를 딱 1개만 생성
	private static final SingletonService instance = new SingletonService();

	// 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
	public static SingletonService getInstance() {
		return instance;
	}

	// 3. 생성자를 private 으로 선언해서 외부에서 new 키워드로 생성하지 못하게 한다.
	private SingletonService() {
	}

	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}
}

class SingletonSyncronizedService {
	// 1. static 영역에 객체를 딱 1개만 생성
	private static SingletonSyncronizedService instance;

	// 2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
	// 이 때 동시성으로 접근하여 synchronized를 제공하여 동시성을 막아준다.
	public static synchronized SingletonSyncronizedService getInstance() {
		if (instance != null) {
			return instance;
		}
		return new SingletonSyncronizedService();
	}

	// 3. 생성자를 private 으로 선언해서 외부에서 new 키워드로 생성하지 못하게 한다.
	private SingletonSyncronizedService() {
	}

}

class Singleton {

	// 외부에서 Singleton 객체 생성하지 못하도록 private 선언
	private Singleton() {
	}

	// static 메소드로 Singleton 객체를 생성하지 않고 호출하여  Singleton.InnerClass의 안의 Singleton 객체 리턴
	public static Singleton getInstance() {
		return LazyHolder.INSTANCE;
	}

	// InnerClass static 으로 선언 및 private으로 Singleton 클래스 안에서만 호출 가능하도록 진행
	private static class LazyHolder {
		private static final Singleton INSTANCE = new Singleton();

	}

}