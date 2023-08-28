package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

@Component
// lombok 어노테이션을 사용해서 final이 붙은 필드의 constructor를 자동으로 만들어 준다.
// @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	// 필드 주입의 경우 스프링 DI를 사용하지 않으면 바꿀수가 없다.
	// 순수 자바로 테스트할 방법이 없음.
	//@Autowired
	private final MemberRepository memberRepository;

	// private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	//@Autowired
	private final DiscountPolicy discountPolicy;

	// 생성자가 딱 1개만 존재한다면 Autowired를 선언하지 않아도 자동으로 지정되어 처리 된다.
	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
		System.out.println("OrderServiceImpl constructor");
		System.out.println(discountPolicy);
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}

	// 주입객체가 없는 경우 required = false 를 선언하면 빈 생성이 없는 상태로 진행 된다.
	// @Autowired(required = false)
	// public void setMemberRepository(MemberRepository memberRepository) {
	// 	System.out.println("setMemberRepository");
	// 	this.memberRepository = memberRepository;
	// }
	//
	//
	// @Autowired
	// public void setDiscountPolicy(DiscountPolicy discountPolicy) {
	// 	System.out.println("setDiscountPolicy");
	// 	this.discountPolicy = discountPolicy;
	// }

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findByid(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);

		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

	// 메소드 주입의 경우 생성자나 setter 주입으로 거의 처리 됨으로 잘 사용하지 않는다.
	// @Autowired
	// public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
	// 	this.memberRepository = memberRepository;
	// 	this.discountPolicy = discountPolicy;
	// }

	// 테스트 용
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
