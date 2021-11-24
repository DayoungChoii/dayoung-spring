package dayoung.example.dayoungspring;

import dayoung.example.dayoungspring.repository.MemberRepository;
import dayoung.example.dayoungspring.repository.MemoryMemberRepository;
import dayoung.example.dayoungspring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
