package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	// @Autowired 는 타입을 찾고 타입이 2개 이상인 경우 이름으로 찾게 된다.
	// 이름으로도 동일한 타입이 중복이 되어 찾이 못하면 오류를 뱉어낸다.
	@Autowired // ac.getBean(MemberRepository.class)
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findByid(memberId);
	}

	// 테스트 용
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}

