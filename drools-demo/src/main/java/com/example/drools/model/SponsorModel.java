package com.example.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SponsorModel {
    private String memberGUID;
    private String sponsorGUID;
    private String sponsorRelation;
    private String sponsorEligibilityCode;
}
