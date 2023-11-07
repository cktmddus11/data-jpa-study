package study.datajpa.repository;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

import java.util.List;


// 사용자 정의 인터페이스 구현 클래스
// 규칙: 리포지토리 인터페이스 이름 + Impl
// 스프링 데이터 JPA가 인식해서 스프링 빈으로 등록
// 변경할 수 있지만 그냥쓰자..


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member", Member.class)
                .getResultList();
    }
}
