package com.noisy.rrssProject.repository;

import com.noisy.rrssProject.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerByUsername(String username);
    Optional<Customer> findCustomerByEmail(String email);

    Customer findByEmailIgnoreCase(String emailId);

    Boolean existsByEmail(String email);

}
