package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.CustomerNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.CustomerDTOMapper;
import com.noisy.rrssProject.model.dto.request.CustomerRequest;
import com.noisy.rrssProject.model.dto.request.CustomerUpdate;
import com.noisy.rrssProject.model.dto.response.CustomerProductListResponse;
import com.noisy.rrssProject.model.dto.response.CustomerResponse;
import com.noisy.rrssProject.model.dto.response.PasswordResponse;
import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.model.enums.Gender;
import com.noisy.rrssProject.repository.ConfirmationTokenRepository;
import com.noisy.rrssProject.repository.CustomerRepository;
import com.noisy.rrssProject.model.entity.ConfirmationToken;
import com.noisy.rrssProject.model.entity.Customer;
import com.noisy.rrssProject.model.entity.Product;
import com.noisy.rrssProject.model.entity.Reward;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper mapper;
    private final RewardService rewardService;
    private final ProductService productService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository, CustomerDTOMapper mapper, RewardService rewardService, ProductService productService, ConfirmationTokenRepository confirmationTokenRepository, EmailService emailService) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.rewardService = rewardService;
        this.productService = productService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    public CustomerResponse getCustomerById(Long id){
        return customerRepository.findById(id).map(mapper).orElseThrow(()-> new CustomerNotFoundException("Customer could not get by id: "+id));
    }

    public CustomerProductListResponse getCustomerProductsById(Long id){
        return customerRepository.findById(id).map(mapper::mapCustomerToCustomerProductListResponse).orElseThrow(()-> new CustomerNotFoundException("Customer could not get by id: "+id));
    }

    public Customer findCustomerById(Long id){
        return customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer could not find by id: "+id));
    }
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElseThrow(()->
                new CustomerNotFoundException("Customer could not find by email: "+email));
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper).collect(Collectors.toList());
    }

    public Customer createCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsCustomerByEmail(customerRequest.email()))
            throw new RuntimeException("Email is already exists!");
        if (customerRepository.existsCustomerByUsername(customerRequest.username()))
            throw new RuntimeException("Username is already exists!");

        Customer customer = mapper.mapCustomerRequestToCustomer(customerRequest);
        if(customer.getGender() == null)  customer.setGender(Gender.MALE);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        emailService.sendEmail(customer);
        return customerRepository.save(customer);
    }
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            Customer customer = customerRepository.findByEmailIgnoreCase(token.getCustomer().getEmail());
            customer.setEnabled(true);
            if(customer.getAccountType()== AccountType.ADMIN_PASSIVE) customer.setAccountType(AccountType.ADMIN_ACTIVE);
            else customer.setAccountType(AccountType.CUSTOMER_ACTIVE);

            customerRepository.save(customer);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }



    public CustomerResponse updateCustomer(Long id, CustomerUpdate customerUpdate) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        customer.setName(customerUpdate.name());
        customer.setPassword(customerUpdate.password());
        customer.setEmail(customerUpdate.email());
        if (customerUpdate.phoneNumber() != null) customer.setPhoneNumber(customerUpdate.phoneNumber());
        if (customerUpdate.profilePicture() != null) customer.setProfilePicture(customerUpdate.profilePicture());
        return mapper.apply(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }


    public CustomerResponse assignRewardToCustomer(Long rewardId, Long customerId) {
        Customer customer = findCustomerById(customerId);
        Reward reward = rewardService.findRewardById(rewardId);
        List<Reward> rewardList = customer.getRewards();
        rewardList.add(reward);
        customer.setRewards(rewardList);
        return mapper.apply(customerRepository.save(customer));
    }

    public CustomerProductListResponse addProductToCustomerCard(Long productId, Long customerId) {
        Customer customer = findCustomerById(customerId);
        Product product = productService.findProductById(productId);
        List<Product> productList = customer.getProducts();

        if (!productList.contains(product)) {
            productList.add(product);
            customer.setProducts(productList);
            customerRepository.save(customer);
        }

        return mapper.mapCustomerToCustomerProductListResponse(customer);
    }

    public CustomerProductListResponse deleteProductFromCustomerCard(Long productId, Long customerId) {
        Customer customer = findCustomerById(customerId);
        Product product = productService.findProductById(productId);
        List<Product> productList = customer.getProducts();

        if (productList.contains(product)) {
            productList.remove(product);
            customer.setProducts(productList);
            customerRepository.save(customer);
        }

        return mapper.mapCustomerToCustomerProductListResponse(customer);
    }

    public void sendPasswordRecoveryEmail(String email) {

        if (!customerRepository.existsCustomerByEmail(email))
            throw new RuntimeException("There is no such email!");

        String newPasword = generateType1UUID().randomUUID().toString();

        Customer customer = customerRepository.findByEmailIgnoreCase(email);

        emailService.sendEmail(new PasswordResponse(customer,
                newPasword,"Your password successfully" +
                "recovered!"));
        customer.setPassword(passwordEncoder.encode(newPasword));
        customerRepository.save(customer);
    }
    private UUID generateType1UUID() {
        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();
        return new UUID(most64SigBits, least64SigBits);
    }
    private long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong | variant3BitFlag;
    }
    private long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
}

}
