package hello.core.discount;

import hello.core.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {
	@Override
	public int discont(Member member, int price) {
		return (int)(price * 0.1);
	}
}
