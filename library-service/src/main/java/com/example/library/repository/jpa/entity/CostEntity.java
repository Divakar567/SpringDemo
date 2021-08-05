package com.example.library.repository.jpa.entity;

import com.example.library.model.Cost;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class CostEntity {
    private Long amount;
    private String currency;

    public static CostEntity from(Cost cost) {
        CostEntity entity = new CostEntity();
        entity.setAmount(cost.getAmount());
        entity.setCurrency(cost.getCurrency().name());
        return entity;
    }

    public void update(Cost cost) {
        this.setAmount(cost.getAmount());
        this.setCurrency(cost.getCurrency().name());
    }

    public Cost toBook() {
        Cost cost = new Cost();
        cost.setAmount(this.getAmount());
        cost.setCurrency(Cost.Currency.valueOf(this.getCurrency()));
        return cost;
    }
}
