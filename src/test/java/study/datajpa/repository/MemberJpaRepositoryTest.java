package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;


@Transactional // 테스트 기본 롤백
@Rollback(false)
@SpringBootTest
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    public void testMemer() {
        Member member = new Member("memberA");
        Member saveMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(saveMember.getId());

        Assertions.assertThat(findMember.getId())  // TODO : static 으로 호출
                .isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername())  // TODO : static 으로 호출
                .isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member); // 영속성 컨텍스트 저장 object => 인스턴스동일성 보장
        // 동일한 트랜젝션내에 동일한 영속성 컨텍스트 공유
    }


    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member member = new Member("memberA", 10);
        Member member2 = new Member("memberA", 5);
        Member member3 = new Member("memberA", 5);

        memberJpaRepository.save(member2);
        memberJpaRepository.save(member);
        memberJpaRepository.save(member3);

        List<Member> memberList = memberJpaRepository.findByUsernameAndAgeGreaterThen("memberA", 5);
        for(Member m : memberList){
            System.out.println("member = "+m);
        }
    }

    @Test
    void findByPage() {
        for(int i = 0;i<100;i++){
            Member member = new Member("memberA", 10);
            memberJpaRepository.save(member);
        }
        int age = 10;
        List<Member> pageList = memberJpaRepository.findByPage(age, 1, 10);
        for(Member m : pageList){
            System.out.println("member = "+m);
        }
        long totalCount = memberJpaRepository.totalCount(age);
        System.out.println("totalCount = "+totalCount);



    }


    @Test
    void bulkAgePlus() {
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 19));
        memberJpaRepository.save(new Member("member3", 20));
        memberJpaRepository.save(new Member("member4", 21));
        memberJpaRepository.save(new Member("member5", 40));

        int resultCount = memberJpaRepository.bulkAgePlus(18);

        Assertions.assertThat(resultCount).isEqualTo(4);


    }


}