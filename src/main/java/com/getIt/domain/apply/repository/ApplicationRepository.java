package com.getit.domain.apply.repository;

import com.getit.domain.apply.entity.Application;
import com.getit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMemberOrderByIdDesc(Member member);
}
