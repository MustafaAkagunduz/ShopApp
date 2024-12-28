package com.noisy.rrssProject.service;

import com.noisy.rrssProject.exception.CouponNotFoundException;
import com.noisy.rrssProject.model.dto.mapper.CouponDTOMapper;
import com.noisy.rrssProject.model.dto.request.CouponRequest;
import com.noisy.rrssProject.model.dto.response.CouponResponse;
import com.noisy.rrssProject.repository.CouponRepository;
import com.noisy.rrssProject.model.entity.Coupon;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponDTOMapper mapper;

    public CouponService(CouponRepository couponRepository, CouponDTOMapper mapper) {
        this.couponRepository = couponRepository;
        this.mapper = mapper;
    }

    public CouponResponse getCouponById(Long id){
        return couponRepository.findById(id).map(mapper).orElseThrow(
                ()->new CouponNotFoundException("Coupon could not find by id:" +id)
        );
    }
    public Coupon findCouponById(Long id){
        return couponRepository.findById(id).orElseThrow(()->new CouponNotFoundException("Coupon could not find by id:" +id));
    }

    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }


    public CouponResponse updateCoupon(Long id, CouponRequest request) {
        Coupon coupon = mapper.CouponRequestToCoupon(request);
        coupon.setId(id);
        return mapper.apply(couponRepository.save(coupon));
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    public Coupon createCoupon(CouponRequest request) {
        Coupon coupon = mapper.CouponRequestToCoupon(request);
        return couponRepository.save(coupon);
    }

    public List<Coupon> createCouponNTimes(CouponRequest request, int n) {
        List<Coupon> coupons = new ArrayList<>();
        for(int i=0;i<n;i++){
            Coupon coupon = mapper.CouponRequestToCoupon(request);
            coupons.add(coupon);
        }
        return couponRepository.saveAll(coupons);
    }
}
