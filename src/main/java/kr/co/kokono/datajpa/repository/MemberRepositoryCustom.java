package kr.co.kokono.datajpa.repository;

import kr.co.kokono.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
