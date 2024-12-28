package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.MerchantRequest;
import com.noisy.rrssProject.model.dto.response.MerchantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Merchant;
import com.noisy.rrssProject.service.MerchantService;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantResponse> getMerchantById(@PathVariable Long id) {
        MerchantResponse response = merchantService.getMerchantById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MerchantResponse>> getAllMerchants() {
        List<MerchantResponse> responseList = merchantService.getAllMerchants();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantRequest merchant) {
        Merchant createdMerchant = merchantService.createMerchant(merchant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMerchant);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody MerchantRequest merchant) {
        return ResponseEntity.ok(merchantService.saveMerchant(merchant));
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return merchantService.confirmEmail(confirmationToken);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MerchantResponse> updateMerchant(@PathVariable Long id, @RequestBody MerchantRequest merchant) {
        MerchantResponse updatedMerchant = merchantService.updateMerchant(id, merchant);
        return ResponseEntity.ok(updatedMerchant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok("Merchant deleted successfully.");
    }
}
