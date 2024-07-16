package animal.entity;

import animal.entity.jumpspider.JumpSpider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.MOD)
public class MonsterRegister {

    private final static String MODID = "maring";
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    
    public static final RegistryObject<EntityType<JumpSpider>> JUMP_SPIDER = ENTITY_TYPES.register("jump_spider",
            () -> EntityType.Builder.of(JumpSpider::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F) // Hitbox size: width, height
                    .build(new ResourceLocation(MODID, "jump_spider").toString()));

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JUMP_SPIDER.get(), JumpSpider.createAttributes().build());
    }
}