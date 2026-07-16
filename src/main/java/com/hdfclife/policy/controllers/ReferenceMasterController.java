package com.hdfclife.policy.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfclife.policy.service.ReferenceMasterService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/reference-master")
public class ReferenceMasterController {
    private final ReferenceMasterService referenceMasterService;

    @GetMapping("/{category}")
    public List<?> getReferenceData(@PathVariable String category) {
        return referenceMasterService.getReferenceMasterList(category);
    }
    

}
