package com.main.maring.block.norm;

import net.minecraft.util.StringRepresentable;

public enum AAirState implements StringRepresentable {
    STILL("still"),
    ACTIVATE("activate"),
    DEACTIVATE("deactivate");

    private final String name;

    AAirState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
