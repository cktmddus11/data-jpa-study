package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Address;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Rollback(false)
@Transactional
@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

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

    @Test
    void findByUsernameAndAgeGreaterThen() {
        Member member = new Member("memberA", 10);
        Member member2 = new Member("memberA", 5);
        Member member3 = new Member("memberA", 5);

        memberRepository.save(member2);
        memberRepository.save(member);
        memberRepository.save(member3);


        List<Member> memberList = memberRepository.findByUsernameAndAgeGreaterThan("memberA", 2);
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void countByUsernameAndAgeGreaterThen() {
        Member member = new Member("memberA", 10);
        Member member2 = new Member("memberA", 5);
        Member member3 = new Member("memberA", 5);

        memberRepository.save(member2);
        memberRepository.save(member);
        memberRepository.save(member3);

        long memberCount = memberRepository.countByUsernameAndAgeGreaterThan("memberA", 2);
        System.out.println("memberCount = " + memberCount);
    }

    @Test
    void findByUsernameContaining() {
        Member member = new Member("memberA", 10);
        Member member2 = new Member("memberA", 5);
        Member member3 = new Member("memberA", 5);

        memberRepository.save(member2);
        memberRepository.save(member);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findByUsernameContaining("memberA");
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void findTestMemberByUsername() {
        Member member = new Member("memberA", 10);
        Member member2 = new Member("memberB", 5);
        Member member3 = new Member("memberC", 5);

        memberRepository.save(member2);
        memberRepository.save(member);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findTestMemberByUsername("memberA");
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }

    }

    @Test
    void findUser() {
        Member member = new Member("memberA", 3);
        Member member2 = new Member("memberB", 5);


        memberRepository.save(member);
        memberRepository.save(member2);

        List<Member> memberList = memberRepository.findUser("memberA", 3);
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }

    }

    @Test
    void findMemberUserName() {

        Member member = new Member("memberA", 3);
        Member member2 = new Member("memberB", 5);

        memberRepository.save(member);
        memberRepository.save(member2);

        List<String> userNameList = memberRepository.findMemberUserName();
        for (String username : userNameList) {
            System.out.println("username = " + username);
        }
    }

    @Test
    void findMemberByAddressCity() {
        Address memberAddress = new Address("주소1", "서울", "길", "우편번호");
        Address member2Address = new Address("주소1", "부산", "길", "우편번호");
        Address member3Address = new Address("주소1", "서울", "길", "우편번호");


        Member member = new Member("memberA", 3, memberAddress);
        Member member2 = new Member("memberB", 5, member2Address);
        Member member3 = new Member("memberC", 5, member3Address);


        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);


        List<Member> memberList = memberRepository.findMemberByAddressCity("서울");
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void findMemberDto() {
        Team team = new Team("팀1");
        Team team2 = new Team("팀2");

        teamRepository.save(team);
        teamRepository.save(team2);

        Member member = new Member("memberA", 3, team);
        Member member2 = new Member("memberB", 5, team2);

        memberRepository.save(member);
        memberRepository.save(member2);

        List<MemberDto> memberList = memberRepository.findMemberDto();
        for (MemberDto m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void findMemberByAddressCity2() {
        Address memberAddress = new Address("주소1", "서울", "길", "우편번호");
        Address member2Address = new Address("주소1", "부산", "길", "우편번호");
        Address member3Address = new Address("주소1", "서울", "길", "우편번호");


        Member member = new Member("memberA", 3, memberAddress);
        Member member2 = new Member("memberB", 5, member2Address);
        Member member3 = new Member("memberC", 5, member3Address);


        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> memberList = memberRepository.findMemberByAddressCity2("서울", 3);
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void findMembeByUsernameList() {
        Member member = new Member("memberA", 3);
        Member member2 = new Member("memberB", 5);
        Member member3 = new Member("memberC", 5);

        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);


        List<String> usernameList = Arrays.asList("memberA", "memberC");
        List<Member> memberList = memberRepository.findMembeByUsernameList(usernameList);
        for (Member m : memberList) {
            System.out.println("member = " + m);
        }
    }

    @Test
    void findByUsername() {

        Member member1 = new Member("memberA", 10);
        Member member2 = new Member("memberA", 10);
        Member member3 = new Member("memberA", 10);
        Member member4 = new Member("memberA", 10);
        Member member5 = new Member("memberA", 10);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        // PageRequest of(int pageNumber, int pageSize, Sort sort)
        PageRequest pageRequest =   // 페이지는 0번부터 시작
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> page = memberRepository.findByUsername("memberA", pageRequest);

        List<Member> content = page.getContent(); //조회된 데이터
        Assertions.assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        Assertions.assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
        Assertions.assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        Assertions.assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
        Assertions.assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?


        for (Member m : content) {
            System.out.println("member = " + m);
        }
    }


    @Test
    void findByUsername2() {

        Member member1 = new Member("memberA", 10);
        Member member2 = new Member("memberA", 10);
        Member member3 = new Member("memberA", 10);
        Member member4 = new Member("memberA", 10);
        Member member5 = new Member("memberA", 10);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        // PageRequest of(int pageNumber, int pageSize, Sort sort)
        PageRequest pageRequest =   // 페이지는 0번부터 시작
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> slice = memberRepository.findMemberByUsername("memberA", pageRequest);

        List<Member> content = slice.getContent(); //조회된 데이터
        Assertions.assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
      //  Assertions.assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
        Assertions.assertThat(slice.getNumber()).isEqualTo(0); //페이지 번호
       // Assertions.assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
        Assertions.assertThat(slice.isFirst()).isTrue(); //첫번째 항목인가?
        Assertions.assertThat(slice.hasNext()).isTrue(); //다음 페이지가 있는가?

        Pageable nextPageable = slice.nextPageable();
        Assertions.assertThat(slice.getNumber()+1).isEqualTo(nextPageable.getPageNumber());
    }

    @Test
    void findMemberAllCountBy() {


        Member member1 = new Member("memberA", 10);
        Member member2 = new Member("memberA", 10);
        Member member3 = new Member("memberA", 10);
        Member member4 = new Member("memberA", 10);
        Member member5 = new Member("memberA", 10);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);


        // PageRequest of(int pageNumber, int pageSize, Sort sort)
        PageRequest pageRequest =   // 페이지는 0번부터 시작
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Page<Member> page = memberRepository.findMemberAllCountBy(pageRequest);


        List<Member> content = page.getContent(); //조회된 데이터
        for (Member m : content) {
            System.out.println("member = " + m);
        }
        System.out.println(" content = "+ page.getTotalElements());

    }

    @Test
    void findMemberConvertToDto() {
        makeTeamAndMember();

        // PageRequest of(int pageNumber, int pageSize, Sort sort)
        PageRequest pageRequest =   // 페이지는 0번부터 시작
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        // Page<Member> page = memberRepository.findByUsername("memberA", pageRequest);
        Page<Member> page = memberRepository.findMemberNotLeftJoin(
                pageRequest);

        Page<MemberDto> dtoPage = page.map(m -> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));
        List<MemberDto> content = dtoPage.getContent(); //조회된 데이터
        for (MemberDto m : content) {
            System.out.println("member = " + m);
        }
        System.out.println(" content = "+ page.getTotalElements());
    }

    private void makeTeamAndMember() {
        Team team = new Team("팀1");
        Team team2 = new Team("팀2");
        Team team3 = new Team("팀3");
        Team team4 = new Team("팀4");
        Team team5 = new Team("팀5");


        teamRepository.save(team);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamRepository.save(team4);
        teamRepository.save(team5);


        Member member1 = new Member("memberA", 10, team);
        Member member2 = new Member("memberA", 10, team2);
        Member member3 = new Member("memberA", 1, team3);
        Member member4 = new Member("memberA", 1, team4);
        Member member5 = new Member("memberA", 10, team5);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);


    }
}