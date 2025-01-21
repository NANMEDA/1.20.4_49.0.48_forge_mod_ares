package util.enums;

public enum EnvironmentEnum {
	NUL,
	DEAD,
	STRUGGLE,
	ALIVE,
	PERFECT;
	
    public boolean isOrBetter(EnvironmentEnum other) {
        return this.ordinal() >= other.ordinal();
    }
}
