package com.example.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperSponsorModel {
    private String memberGUID;
    private String sponsorGUID;
    private String superSponsorGUID;
    private String superSponsorRelation;
    private String superSponsorEligibilityCode;
}
