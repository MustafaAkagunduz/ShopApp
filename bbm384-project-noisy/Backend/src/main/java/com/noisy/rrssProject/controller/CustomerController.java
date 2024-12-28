package com.noisy.rrssProject.controller;

import com.noisy.rrssProject.model.dto.request.CustomerUpdate;
import com.noisy.rrssProject.model.dto.response.CustomerProductListResponse;
import com.noisy.rrssProject.model.dto.response.CustomerResponse;
import com.noisy.rrssProject.model.dto.request.CustomerRequest;
import com.noisy.rrssProject.model.entity.Customer;
import com.noisy.rrssProject.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            CustomerResponse customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getCustomerProductListById(@RequestParam("customerId") Long customerId) {
        try {
            CustomerProductListResponse customer = customerService.getCustomerProductsById(customerId);
            return ResponseEntity.ok(customer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping()
    public ResponseEntity<?> createCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        try {
            Customer createdCustomer = customerService.createCustomer(customerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/assignReward")
    public ResponseEntity<?> assignRewardToCustomer(@RequestParam("rewardId") Long rewardId,
                                                    @RequestParam("customerId") Long customerId ){
        return ResponseEntity.ok(customerService.assignRewardToCustomer(rewardId,customerId));
    }

    @PutMapping("/addProductToCustomerCard")
    public ResponseEntity<?> addProductToCustomerCard(@RequestParam("productId") Long productId,
                                                    @RequestParam("customerId") Long customerId ){
        return ResponseEntity.ok(customerService.addProductToCustomerCard(productId,customerId));
    }
    @PutMapping("/deleteProductFromCustomerCard")
    public ResponseEntity<?> deleteProductFromCustomerCard(@RequestParam("productId") Long productId,
                                                      @RequestParam("customerId") Long customerId ){
        return ResponseEntity.ok(customerService.deleteProductFromCustomerCard(productId,customerId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CustomerRequest customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return customerService.confirmEmail(confirmationToken);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Validated @PathVariable Long id, @RequestBody CustomerUpdate customerUpdate) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerUpdate);
        return ResponseEntity.ok(updatedCustomer);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully.");
    }

    @GetMapping("/password/recovery")
    public ResponseEntity<String> recoverPassword(@RequestParam("email")
                                                  String email) {
        customerService.sendPasswordRecoveryEmail(email);
        return ResponseEntity.ok("New password recovered successfully.");}
}
