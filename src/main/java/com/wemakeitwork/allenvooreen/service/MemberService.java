package com.wemakeitwork.allenvooreen.service;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService implements MemberServiceInterface {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMembername(String membername) {
        return memberRepository.findByMemberName(membername);
    }
}