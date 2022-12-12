package com.example.drools.controller;

import com.example.drools.model.COPModel;
import com.example.drools.model.MemberModel;
import com.example.drools.model.SponsorModel;
import com.example.drools.model.SuperSponsorModel;
import com.example.drools.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class COPController {

    private final KieContainer kieContainer;

    private final MemberService memberService;

    @GetMapping("/cop-determination")
    public COPModel getQuestions(@RequestParam String memberGUID, @RequestParam String baseStateCode) {
        KieSession kieSession = kieContainer.newKieSession();
        MemberModel memberModel = memberService.getMember(memberGUID);
        memberModel.setEligibilityCode("EXD");
        SponsorModel sponsorModel = memberService.getSponsor(memberGUID);
        sponsorModel.setSponsorEligibilityCode("ENL");
        SuperSponsorModel superSponsorModel = memberService.getSuperSponsor(memberGUID, sponsorModel.getSponsorGUID());
        superSponsorModel.setSuperSponsorEligibilityCode("ENL");
        COPModel copModel = COPModel.builder()
                .memberGUID(memberGUID)
                .baseStateCode(baseStateCode)
                .eligibilityCode(memberModel.getEligibilityCode())
                .sponsorEligibilityCode(sponsorModel.getSponsorEligibilityCode())
                .superSponsorEligibilityCode(superSponsorModel.getSuperSponsorEligibilityCode())
                .build();

        kieSession.insert(memberModel);
        kieSession.insert(sponsorModel);
        kieSession.insert(superSponsorModel);
        kieSession.insert(copModel);
        kieSession.setGlobal("$copModel", copModel);
        kieSession.fireAllRules();
        kieSession.dispose();
        return copModel;
    }
}