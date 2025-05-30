package com.main.maring.animal.entity.villager;

import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import java.util.function.Predicate;
import net.minecraft.core.Holder;

/**
 * 新增村民的职业和兴趣点
 * @author NANMEDA
 * */
public class ModVillager {

	public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, Maring.MODID);

    public static final Supplier<PoiType> HANDMAKE_TABLE_POI = POI_TYPES.register("villager_handmake_table_poi",
            () -> new PoiType(ImmutableSet.copyOf(BlockRegister.VILLAGER_HANDMAKE_TABLE.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static void POIRegister(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
    }
    
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(Registries.VILLAGER_PROFESSION, Maring.MODID);

    public static final Predicate<Holder<PoiType>> IS_HANDMAKE_TABLE_POI = poiTypeHolder -> poiTypeHolder.value() == HANDMAKE_TABLE_POI.get();

    public static final Supplier<VillagerProfession> HANDMAKE_MASTER = VILLAGER_PROFESSIONS.register("handmake_master",
            () -> new VillagerProfession("ruby_master",IS_HANDMAKE_TABLE_POI,
                    IS_HANDMAKE_TABLE_POI, ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_ARMORER));


    public static void VillagerRegister(IEventBus eventBus) {
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
