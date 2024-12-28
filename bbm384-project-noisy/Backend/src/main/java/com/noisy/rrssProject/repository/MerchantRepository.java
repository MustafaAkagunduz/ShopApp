package com.noisy.rrssProject.repository;


import com.noisy.rrssProject.model.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByEmailIgnoreCase(String emailId);
    Optional<Merchant> findByNameIgnoreCase(String name);
    Boolean existsByEmail(String email);
}
