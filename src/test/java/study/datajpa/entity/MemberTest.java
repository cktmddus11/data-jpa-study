package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.engine.spi.EntityEntry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@Transactional // 테스트 기본 롤백
@Rollback(false)
@SpringBootTest
class MemberTest { // 연관관계 테스트
    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4); // persist : 영속성 컨텍스트 저장, insert쿼리가 날라가진않음

        
        // 초기화
        em.flush(); // insert 쿼리
        em.clear(); // 영속성 컨텍스트 비움.

        // 확인
        List<Member> members = em.createQuery("select m from Member m ", Member.class)
                .getResultList();

        for(Member member : members){
            System.out.println("member = "+member);
            System.out.println("-> member.team = "+ member.getTeam());
        }


    }
}