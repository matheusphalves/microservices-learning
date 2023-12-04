package com.ms.stock.exchange.models;

import java.util.Objects;
import java.util.UUID;

public class Asset {

    private UUID id;
    private String name;
    private int marketVolume;

    public Asset(UUID id, String name, int marketVolume) {
        this.id = id;
        this.name = name;
        this.marketVolume = marketVolume;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarketVolume() {
        return marketVolume;
    }

    public void setMarketVolume(int marketVolume) {
        this.marketVolume = marketVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asset asset)) return false;
        return Objects.equals(id, asset.id) && Objects.equals(name, asset.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
