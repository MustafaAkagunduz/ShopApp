package com.noisy.rrssProject.controller;

import java.util.List;

import com.noisy.rrssProject.model.dto.request.CouponRequest;
import com.noisy.rrssProject.model.dto.response.CouponResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.noisy.rrssProject.model.entity.Coupon;
import com.noisy.rrssProject.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long id) {
        CouponResponse response = couponService.getCouponById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CouponResponse>> getAllCoupons() {
        List<CouponResponse> responseList = couponService.getAllCoupons();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping()
    public ResponseEntity<Coupon> createCoupon(@RequestBody CouponRequest coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
    }
    @PostMapping("/{n}")
    public ResponseEntity<List<Coupon>> createNCoupon(@RequestBody CouponRequest coupon, @PathVariable int n) {
        List<Coupon> createdCoupons = couponService.createCouponNTimes(coupon,n);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest coupon) {
        CouponResponse updatedCoupon = couponService.updateCoupon(id, coupon);
        return ResponseEntity.ok(updatedCoupon);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted successfully.");
    }
}
