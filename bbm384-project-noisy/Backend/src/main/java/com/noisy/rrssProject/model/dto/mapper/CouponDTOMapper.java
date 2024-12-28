package com.noisy.rrssProject.model.dto.mapper;

import com.noisy.rrssProject.model.dto.request.CouponRequest;
import com.noisy.rrssProject.model.dto.response.CouponResponse;
import com.noisy.rrssProject.model.entity.Coupon;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class CouponDTOMapper implements Function<Coupon, CouponResponse> {

    @Override
    public CouponResponse apply(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getExpiryDate(),
                coupon.isUsed()
        );
    }

    public Coupon CouponRequestToCoupon(CouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setDiscount(request.discount());
        coupon.setExpiryDate(request.expiryDate());
        return coupon;
    }

}
