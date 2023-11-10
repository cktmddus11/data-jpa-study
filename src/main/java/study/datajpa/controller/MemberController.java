package study.datajpa.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    // 도메인 컨버터 이용전
    @GetMapping("/v1/members/{id}")
    public String findMember (@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }


    // 도메인 컨버터 이용후
    @GetMapping("/v2/members/{id}")
    public String findMember2 (@PathVariable("id") Member member){ // 도메인 클래스 컨버터가 중간에 동작해 회원엔티티 객체를 반환.
        return member.getUsername(); // 도메인 컨버터도 리파지토리를 사용해 엔티티를 찾음.
    } // 트랜젝션이 없는 범위에서 엔티티를 조회했으므로 엔티티를 변경해도 DB에 반영되지 않음.
    // 조회용으로만사용!!!

}
