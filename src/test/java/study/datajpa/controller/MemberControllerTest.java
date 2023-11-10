package study.datajpa.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@Rollback(false)
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findMember2() throws Exception {
        //
        Member member = new Member("member1", 3);
        memberRepository.save(member);

        // Given
        long memberId = member.getId(); // 테스트에 사용할 멤버의 ID



        // When and Then
        mockMvc.perform(get("/v2/members/{id}", memberId))
                .andExpect(status().isOk())
                .andExpect(content().string("member1")); // 예상되는 반환값인 username을 여기에 넣어주세요


    }
}