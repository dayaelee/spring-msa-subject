package com.sparta.msa_exam.auth.repository;

import com.sparta.msa_exam.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsernameAndPassword(String username, String password);
}
