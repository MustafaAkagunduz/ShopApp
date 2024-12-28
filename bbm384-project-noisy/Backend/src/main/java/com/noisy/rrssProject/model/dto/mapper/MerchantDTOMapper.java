package com.noisy.rrssProject.model.dto.mapper;

import com.noisy.rrssProject.model.dto.request.MerchantRequest;
import com.noisy.rrssProject.model.dto.response.MerchantResponse;
import com.noisy.rrssProject.model.enums.AccountType;
import com.noisy.rrssProject.model.entity.Merchant;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class MerchantDTOMapper implements Function<Merchant, MerchantResponse> {
    @Override
    public MerchantResponse apply(Merchant merchant) {
        return new MerchantResponse(
                merchant.getId(),
                merchant.getName(),
                merchant.getEmail(),
                merchant.getAccountType(),
                merchant.getProducts()
        );
    }
    public Merchant MerchantRequestToMerchant(MerchantRequest request){
         Merchant merchant = new Merchant();
         merchant.setName(request.name());
         merchant.setEmail(request.email());
         merchant.setPassword(request.password());
         merchant.setPhoneNumber(request.phoneNumber());
         merchant.setAccountType(AccountType.MERCHANT_PASSIVE);
         return merchant;
    }

}
