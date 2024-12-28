package com.noisy.rrssProject.repository;

import com.noisy.rrssProject.model.entity.Customer;
import com.noisy.rrssProject.model.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findAllByCustomer(Customer customer);
}
