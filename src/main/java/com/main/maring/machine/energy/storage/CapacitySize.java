package com.main.maring.machine.energy.storage;

public enum CapacitySize {
    SMALL(1_9200),
    MEDIUM(19_2000),
    LARGE(192_0000);

    private final int capacity;

    CapacitySize(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}