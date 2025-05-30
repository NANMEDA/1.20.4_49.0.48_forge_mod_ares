package com.main.maring.util;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Maps;

import com.main.maring.Maring;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class EntityGravity {

    public static final UUID ARTIFICIAL_GRAVITY_ID = UUID.fromString("242A6B8D-DA4E-4C3C-1234-96EA6096568D");
    private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "maringmar"));
    
    public record GravitySource(BlockPos centre, float gravity, int range) {
    }

    public static class GravityModifier extends AttributeInstance {

        public GravityModifier(Attribute attribute, Consumer<AttributeInstance> onDirty) {
            super(attribute, onDirty);
        }

    }

    private static final Map<ResourceKey<Level>, List<GravitySource>> GRAVMAP = Maps.newConcurrentMap();

    public static final String TAG = "maring:space_gravity";

    public static void addGravitySource(Level level, GravitySource g) {
        List<GravitySource> sources = GRAVMAP.computeIfAbsent(level.dimension(), key -> new ArrayList<>());
        synchronized (sources) {
            sources.add(g);
        }
    }

    public static void removeGravitySource(Level level, GravitySource g) {
        List<GravitySource> sources = GRAVMAP.computeIfAbsent(level.dimension(), key -> new ArrayList<>());
        synchronized (sources) {
            sources.removeIf(g2 -> g2.centre().equals(g.centre()));
        }
    }

    public static float getArtificalGravityModifier(Level level, BlockPos pos) {
        List<GravitySource> sources = GRAVMAP.computeIfAbsent(level.dimension(), key -> new ArrayList<>());
        float g = 0;
        for (var s : sources) {
            if (s.centre().distSqr(pos) < s.range * s.range)
                g += s.gravity;
        }
        return g;
    }

    public static void setGravities(LivingEntity entity, Level level) {
        Attribute attribute = ForgeMod.ENTITY_GRAVITY.get();
        AttributeInstance attributeInstance = entity.getAttribute(attribute);

        /** SET TO DEFAULT */
        if (entity.getPersistentData().getBoolean(TAG)) {
            setGravity(entity, attributeInstance, attribute.getDefaultValue(), false);
        }

        /** SET GRAVITIES */
        if (!entity.getPersistentData().getBoolean(TAG)) {
            float entityGravity = 0.02f;
            if(level.isClientSide) return;
            level = (ServerLevel) level;
        	if(level.dimension()==level.getServer().getLevel(marKey).dimension()) {
        		setGravity(entity, attributeInstance, entityGravity, true);
        	}else{
        		setGravity(entity, attributeInstance, attribute.getDefaultValue(), false);
        	}
        }
    }

    /** SET GRAVITY */
    public static void setGravity(LivingEntity entity, AttributeInstance attributeInstance, double gravity,
            boolean condition) {
        attributeInstance.setBaseValue(gravity);
        entity.getPersistentData().putBoolean(TAG, condition);
    }
}
