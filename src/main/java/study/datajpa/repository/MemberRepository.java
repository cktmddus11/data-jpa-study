package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
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


    Page<Member> findByUsername( String name, Pageable pageable);

    Slice<Member> findMemberByUsername(String name, Pageable pageable);


    @Query(value="select m from Member m",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findMemberAllCountBy(Pageable pageable);




}



