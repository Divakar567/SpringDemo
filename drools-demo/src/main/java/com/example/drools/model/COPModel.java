package com.example.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class COPModel {
    private String memberGUID;
    private String baseStateCode;
    private String eligibilityType;
    private String eligibilityCode;
    private String sponsorEligibilityCode;
    private String superSponsorEligibilityCode;
    private String companyOfEligibility;
    private String companyOfPlacement;
}
