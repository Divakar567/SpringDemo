package com.example.drools.service;

import com.example.drools.model.MemberModel;
import com.example.drools.model.SponsorModel;
import com.example.drools.model.SuperSponsorModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final List<String> eligibilityCodes = List.of("WID", "FSP", "EXD", "ENL");

    private final Random random = new Random();

    public MemberModel getMember(String memberGUID) {
        return MemberModel.builder()
                .memberGUID(memberGUID)
                .eligibilityCode(eligibilityCodes.get(random.nextInt(eligibilityCodes.size())))
                .build();
    }

    public SponsorModel getSponsor(String memberGUID) {
        return SponsorModel.builder()
                .memberGUID(memberGUID)
                .sponsorGUID(UUID.randomUUID().toString())
                .sponsorEligibilityCode(eligibilityCodes.get(random.nextInt(eligibilityCodes.size())))
                .sponsorRelation("Widow")
                .build();
    }

    public SuperSponsorModel getSuperSponsor(String memberGUID, String sponsorGUID) {
        return SuperSponsorModel.builder()
                .memberGUID(memberGUID)
                .sponsorGUID(sponsorGUID)
                .superSponsorGUID(UUID.randomUUID().toString())
                .superSponsorEligibilityCode(eligibilityCodes.get(random.nextInt(eligibilityCodes.size())))
                .superSponsorRelation("Widow")
                .build();
    }
}
