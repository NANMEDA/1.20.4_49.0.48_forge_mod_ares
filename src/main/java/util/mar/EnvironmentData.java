package util.mar;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import util.enums.EnvironmentEnum;

/**
 * 环境数据类，用于保存和加载世界中的环境参数。
 */
public class EnvironmentData extends SavedData {
    private static final String DATA_NAME = "environment_data";
    private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("maring", "maringmar"));
    // 环境参数
    private double humid = 0d;          // 湿度 (0 - 200)					100最佳
    private double oxygen = 0d;         // 氧气含量方便计算 (0 - 200)		20最佳
    private double pressure = 6d;       // 压力 (0 - 6400，单位：百帕)		1000最佳
    private double temperature = -46d;  // 温度 (-273 - 500，单位：摄氏度)	20最佳
    private double mag = 0d;            // 磁场 (0 - 200)					100最佳


    
    public double[] getX() {
    	return new double[] {this.humid,this.oxygen,this.pressure,this.temperature,this.mag};
    }
    
    public void setX(double[] x) {
    	this.humid = x[0];
    	this.oxygen = x[1];
    	this.pressure = x[2];
    	this.temperature = x[3];
    	this.mag = x[4];
    	setDirty();
    }
    
    public boolean suitableMOSS() {
    	return getEnvironmentEnum("humid").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("oxygen").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("pressure").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("temperature").isOrBetter(EnvironmentEnum.STRUGGLE);
    }
    
    /**
     * 更低级的
     * */
    public boolean suitablePLANTL() {
    	return getEnvironmentEnum("humid").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("oxygen").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("pressure").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("temperature").isOrBetter(EnvironmentEnum.STRUGGLE);
    }
    
    public boolean suitablePLANTH() {
    	return getEnvironmentEnum("humid").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("oxygen").isOrBetter(EnvironmentEnum.STRUGGLE)
    			&&getEnvironmentEnum("pressure").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("temperature").isOrBetter(EnvironmentEnum.ALIVE);
    }
    
    public boolean suitableANIMAL() {
    	return getEnvironmentEnum("humid").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("oxygen").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("pressure").isOrBetter(EnvironmentEnum.ALIVE)
    			&&getEnvironmentEnum("temperature").isOrBetter(EnvironmentEnum.ALIVE);
    }
    
    public EnvironmentEnum getEnvironmentEnum(String s) {
    	if(s=="humid") {
    		if(this.humid<5) { 
    			return EnvironmentEnum.DEAD;
    			}
    		else if(this.humid<25||this.humid>180) {
    			return EnvironmentEnum.STRUGGLE;
    		}
    		else if(this.humid<70||this.humid>130) {
    			return EnvironmentEnum.ALIVE;
    		}
    		else{
    			return EnvironmentEnum.PERFECT;
    		}
    	}else if(s=="oxygen") {
    		double o = this.oxygen;
    		if(o<3||o>50) { 
    			return EnvironmentEnum.DEAD;
    			}
    		else if(o<8||o>35) {
    			return EnvironmentEnum.STRUGGLE;
    		}
    		else if(o<16||o>24) {
    			return EnvironmentEnum.ALIVE;
    		}
    		else{
    			return EnvironmentEnum.PERFECT;
    		}
    	}else if(s=="pressure") {
    		if(this.pressure<450||this.pressure>4000) { 
    			return EnvironmentEnum.DEAD;
    			}
    		else if(this.pressure<700||this.pressure>2000) {
    			return EnvironmentEnum.STRUGGLE;
    		}
    		else if(this.pressure<900||this.pressure>1100) {
    			return EnvironmentEnum.ALIVE;
    		}
    		else{
    			return EnvironmentEnum.PERFECT;
    		}
    	}else if(s=="temperature") {
    		if(this.temperature<-80||this.temperature>50) { 
    			return EnvironmentEnum.DEAD;
    			}
    		else if(this.temperature<-20||this.temperature>37) {
    			return EnvironmentEnum.STRUGGLE;
    		}
    		else if(this.temperature<5||this.temperature>30) {
    			return EnvironmentEnum.ALIVE;
    		}
    		else{
    			return EnvironmentEnum.PERFECT;
    		}
    	}
		return EnvironmentEnum.NUL;
	}
    
	public boolean iceMelt() {
		return this.temperature>-10&&this.pressure>500;
	}
	
	public boolean genClouds(){
		return this.humid>20&&this.pressure>200;
	}
    
    public boolean canBurn() {
    	return this.oxygen>16;
    }
    
    // Getter 和 Setter 方法
    public double getHumid() {
        return humid;
    }

    public void setHumid(int humid) {
        this.humid = Math.max(0, Math.min(humid, 200));
        setDirty(); // 标记数据已修改
    }

    public double getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = Math.max(0, Math.min(oxygen, 100));
        setDirty();
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = Math.max(0, Math.min(pressure, 6400));
        setDirty();
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = Math.max(-273, Math.min(temperature, 500));
        setDirty();
    }

    public double getMag() {
        return mag;
    }

    public void setMag(int mag) {
        this.mag = Math.max(0, Math.min(mag, 200));
        setDirty();
    }

    // 保存数据到 NBT
    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putDouble("humid", humid);
        compound.putDouble("oxygen", oxygen);
        compound.putDouble("pressure", pressure);
        compound.putDouble("temperature", temperature);
        compound.putDouble("mag", mag);
        return compound;
    }

    // 从 NBT 加载数据
    public static EnvironmentData load(CompoundTag compound) {
        EnvironmentData data = new EnvironmentData();
        data.humid = compound.getDouble("humid");
        data.oxygen = compound.getDouble("oxygen");
        data.pressure = compound.getDouble("pressure");
        data.temperature = compound.getDouble("temperature");
        data.mag = compound.getDouble("mag");
        return data;
    }

    // 获取或创建数据实例
    public static EnvironmentData getOrCreate(ServerLevel level) {
    	return level.getDataStorage().computeIfAbsent(EnvironmentData.factory(), DATA_NAME);
    }
    
    public static SavedData.Factory<EnvironmentData> factory() {
        return new SavedData.Factory<>(EnvironmentData::new, EnvironmentData::load, DataFixTypes.LEVEL);
     }
    
    public static EnvironmentData get(ServerLevel level) {
        return getOrCreate(level.getServer().getLevel(marKey));
    }


}
