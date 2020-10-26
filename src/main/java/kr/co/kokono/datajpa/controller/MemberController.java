package kr.co.kokono.datajpa.controller;

import kr.co.kokono.datajpa.dto.MemberDto;
import kr.co.kokono.datajpa.entity.Member;
import kr.co.kokono.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getName();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member) {
        return member.getName();
    }

    //http://localhost:8181/members?page=0&size=10&sort=id,desc
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page.map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("userNako"+i, i));
        }
    }
}
