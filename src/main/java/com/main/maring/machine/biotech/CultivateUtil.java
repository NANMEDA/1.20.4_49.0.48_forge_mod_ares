package com.main.maring.machine.biotech;

import net.minecraft.world.item.ItemStack;

public class CultivateUtil {

    public enum PhType {
        ACIDIC, NEUTRAL, ALKALINE
    }

    public enum Wavelength {
        ULTRA_SHORT, SHORT, MEDIUM, LONG
    }

    public enum Salinity {
        LOW, HIGH
    }

    public static class CultivateCondition {
        public final PhType ph;
        public final boolean light;
        public final Wavelength wavelength;
        public final Salinity salinity;
        public final boolean oxygen;
        public final ItemStack catalyst;
        public final ItemStack substrate;

        public CultivateCondition(PhType ph,
                         boolean light,
                         Wavelength wavelength,
                         Salinity salinity,
                         boolean oxygen,
                         ItemStack catalyst,
                         ItemStack substrate) {
            this.ph = ph;
            this.light = light;
            this.wavelength = wavelength;
            this.salinity = salinity;
            this.oxygen = oxygen;
            this.catalyst = catalyst;
            this.substrate = substrate;
        }

        public PhType getPh() {
            return ph;
        }

        public boolean hasLight() {
            return light;
        }

        public Wavelength getWavelength() {
            return wavelength;
        }

        public Salinity getSalinity() {
            return salinity;
        }

        public boolean hasOxygen() {
            return oxygen;
        }

        public ItemStack getCatalyst() {
            return catalyst;
        }

        public ItemStack getSubstrate() {
            return substrate;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            CultivateCondition other = (CultivateCondition) obj;

            // 如果 light 不一致，直接 false
            if (this.light != other.light) return false;

            // 如果 light == true，再比较 wavelength
            if (this.light && this.wavelength != other.wavelength) return false;

            return this.ph == other.ph
                    && this.salinity == other.salinity
                    && this.oxygen == other.oxygen
                    && ItemStack.isSameItemSameTags(this.catalyst, other.catalyst)
                    && ItemStack.isSameItemSameTags(this.substrate, other.substrate);
        }

        @Override
        public String toString() {
            return "CultivateCondition{" +
                    "ph=" + ph +
                    ", light=" + light +
                    ", wavelength=" + wavelength +
                    ", salinity=" + salinity +
                    ", oxygen=" + oxygen +
                    ", catalyst=" + catalyst.getItem().getDescriptionId() +
                    ", substrate=" + substrate.getItem().getDescriptionId() +
                    '}';
        }
    }

}

