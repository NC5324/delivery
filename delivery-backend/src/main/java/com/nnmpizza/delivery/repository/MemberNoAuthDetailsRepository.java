package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.MemberNoAuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberNoAuthDetailsRepository extends JpaRepository<MemberNoAuthDetails, Long> {

    Optional<MemberNoAuthDetails> findById(Long id);

}
