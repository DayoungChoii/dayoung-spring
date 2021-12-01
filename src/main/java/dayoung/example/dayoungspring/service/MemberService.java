package dayoung.example.dayoungspring.service;

import dayoung.example.dayoungspring.domain.Member;
import dayoung.example.dayoungspring.repository.MemberRepository;
import dayoung.example.dayoungspring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){

        long start = System.currentTimeMillis();

        try{
            validateDuplicateMember(member); //중복회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("jon = " + timeMs + "ms");

        }


        //같은 이름이 있는 중복 회원X
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
        
        //권장하는 optional의 모양새

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });


    }

    /**
     * 전체 회원조회
     */
    public List<Member> findMembers(){
        long start = System.currentTimeMillis();

        try{
            return memberRepository.findAll();
        } finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }

    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
