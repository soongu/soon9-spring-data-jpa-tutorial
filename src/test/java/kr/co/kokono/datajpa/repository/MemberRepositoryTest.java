package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = new Member("kura");
        //when
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }

    @Test
    public void basicCRUD() throws Exception {
        //given
        Member member1 = new Member("nako");
        Member member2 = new Member("kura");
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        List<Member> members = memberRepository.findAll();
        //then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        assertThat(members.size()).isEqualTo(2);
        assertThat(memberRepository.count()).isEqualTo(2);

        memberRepository.delete(member1);
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    public void findNameAndAgeGreaterThanTest() throws Exception {
        //given
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMember = memberRepository.findByNameAndAgeGreaterThan("bbb", 15);
        //then
        assertThat(findMember.get(0).getName()).isEqualTo("bbb");
        assertThat(findMember.get(0).getAge()).isEqualTo(20);
    }

}