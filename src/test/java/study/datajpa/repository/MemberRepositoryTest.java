package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Rollback(false)
@Transactional
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMemer() {
        System.out.println("memberRepository : " + memberRepository);

        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        Optional<Member> memberOptional = memberRepository.findById(saveMember.getId());
        Member findMember = memberOptional.get();

        Assertions.assertThat(findMember.getId())  // TODO : static 으로 호출
                .isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername())  // TODO : static 으로 호출
                .isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member); // 영속성 컨텍스트 저장 object => 인스턴스동일성 보장
        // 동일한 트랜젝션내에 동일한 영속성 컨텍스트 공유
    }


    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);
        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);
        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);
        //카운트 검증
        long count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(2);
        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        Assertions.assertThat(deletedCount).isEqualTo(0);
    }
}