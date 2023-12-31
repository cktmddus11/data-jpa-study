package study.datajpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    long countByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findByUsernameContaining(String username);

    List<Member> findTestMemberByUsername(String username);

    // 스프링 데이터 JPA로 NamedQuery 사용
   /* @Query(name = "Member.findByUsername")
    // @Query 생략하고 메서드이름만으로도 가능
    List<Member> findByUsername(@Param("username") String username);*/

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select username from Member m")
    List<String> findMemberUserName();


    @Query("select m from Member m where m.address.city = :city")
    List<Member> findMemberByAddressCity(@Param("city") String city);


    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) " +
            "from Member m " +
            "join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.address.city = ?1 and m.age = ?2")
    List<Member> findMemberByAddressCity2(@Param("city") String city, @Param("age") int age);


    @Query("select m from Member m where m.username in :names")
    List<Member> findMembeByUsernameList(@Param("names") List<String> userNameList);


    Page<Member> findByUsername(String name, Pageable pageable);

    Slice<Member> findMemberByUsername(String name, Pageable pageable);


    @Query(value = "select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);


    @Query(value = "select m from Member m " +
            "left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberNotLeftJoin(Pageable pageable);


    // 스프링 데이터 JPA사용한 벌크성 수정쿼리
    @Modifying(clearAutomatically = true) //수정 삭제 쿼리는 어노테이션 필수
    @Query("update Member m set m.age = m.age + 1 " +
            "where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    // EntityGraph

    // 공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    // JPQL + EntityGraph
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();


    // NamedQuery 이용
    @EntityGraph(attributePaths = {"team"})
    List<Member> findByUsername(String name);

    @EntityGraph("Member.all")
    @Query("select m from Member m")
    List<Member> findMemberEntityGrap2();


    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);


    @QueryHints(value = {
            @QueryHint(name = "org.hibernate.readOnly", value = "true")
             }
            , forCounting = true)
    Page<Member> findMemberUseHintByUsername(String name, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // 비관적락 => 성능저하에 주의
                                            // 쓰기락. 해당 메소드가 실행되는 동안 엔티티에 쓰기락 걸림.
    List<Member> findMemberUseLockByUsername(String name); // 다수의 트랜젝션 동시에 수정하는 경우 데이터 무결성 방지


}



