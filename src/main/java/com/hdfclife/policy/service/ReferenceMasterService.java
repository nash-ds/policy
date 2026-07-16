package com.hdfclife.policy.service;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class ReferenceMasterService {

    private final List<Integer> policyTerms  =  List.of(10, 15, 20, 25, 30);
    private final List<String> paymentFrequencies = List.of("MONTHLY", "QUARTERLY", "HALF-YEARLY", "YEARLY");

    public List<?> getReferenceMasterList(String category){
        if(category.equalsIgnoreCase("policyterm")){
            return policyTerms;
        }else if(category.equalsIgnoreCase("paymentfrequency")){
            return paymentFrequencies;
        }else{
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }
    
}
