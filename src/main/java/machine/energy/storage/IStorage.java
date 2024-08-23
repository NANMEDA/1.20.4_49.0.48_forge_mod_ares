package machine.energy.storage;

public interface IStorage{

    abstract long getStorage();
    
    abstract void addStorage(int e);
    
    public abstract EnergyStorageMode getEnum();
    
    abstract long getCapalicity();
    
    abstract int transSpeed();
}
