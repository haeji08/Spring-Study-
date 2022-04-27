package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given (뭔가가 주어졌는데) - "이 데이터를 기반으로 하는구나..!"
        Member member = new Member();
        member.setName("hello");

        //when (이걸 실행했을 때) - "아 이걸 검증하는구나..!"
        Long saveId = memberService.join(member);

        //then (결과가 이게 나와야 함) - 검증부분
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");


        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}