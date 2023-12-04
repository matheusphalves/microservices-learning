package com.ms.stock.exchange.models;

import java.util.List;
import java.util.UUID;

public record InvestorRecord(
        UUID id,
        String name,
        List<InvestorAssetPosition> investorAssets) {

    public void addInvestorAsset(InvestorAssetPosition investorAssetPosition){
        this.investorAssets.add(investorAssetPosition);
    }

    public void updateInvestorAsset(InvestorAssetPosition investorAssetPosition){

        this.investorAssets
                .stream()
                .filter((investorAsset -> investorAsset.equals(investorAssetPosition)))
                .findFirst()
                .ifPresent(investorAsset -> investorAsset = investorAssetPosition);

    }
}
