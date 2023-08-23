package hello.core.member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryMemberRepository implements MemberRepository {

	// HashMap 은 동시성 이슈가 발생할 수 있음.
	// private static Map<Long, Member> store = new HashMap<>();
	// ConcurrentHashMap 의 경우 버킷 단위로 Lock을 걸며 동시성에 안전하다.
	private static Map<Long, Member> store = new ConcurrentHashMap<>();

	@Override
	public void save(Member member) {
		store.put(member.getId(), member);
	}

	@Override
	public Member findByid(Long memberId) {
		return store.get(memberId);
	}
}
