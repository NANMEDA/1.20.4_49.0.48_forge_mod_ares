package event;

import event.forge.EntityTickEvent;
import event.forge.ItemEntityTickAtEndEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import util.EntityGravity;
import util.ItemGravity;

@Mod.EventBusSubscriber(modid = "maring")
public class Events {

    @SubscribeEvent
    public static void itemEntityEndTick(ItemEntityTickAtEndEvent event) {
        ItemEntity itemEntity = event.getEntity();
        Level level = itemEntity.level();

        /** ITEM ENTITY GRAVITY SYSTEM */
        ItemGravity.setGravities(itemEntity, level);
    }

    @SubscribeEvent
    public static void entityTick(EntityTickEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();

        /** ARTIFICIAL GRAVITY FOR LIVING ENTITIES */
        if (entity instanceof LivingEntity living) {

            double artificialGravity = EntityGravity.getArtificalGravityModifier(level, entity.blockPosition());

            // You can text this by placing a barrel, then un-commenting the following code.
//            if (artificialGravity == 0 && level instanceof ServerLevel slevel) {
//                Stream<PoiRecord> points = slevel.getPoiManager().getInRange(p -> p.is(PoiTypes.FISHERMAN),
//                        entity.getOnPos(), 5, Occupancy.ANY);
//                points.forEach(r -> {
//                    BlockPos pos = r.getPos();
//                    GravitySource g = new GravitySource(pos, 0.5f, 10);
//                    EntityGravity.addGravitySource(slevel, g);
//                });
//            }

            Attribute attribute = ForgeMod.ENTITY_GRAVITY.get();
            artificialGravity *= attribute.getDefaultValue();
            AttributeInstance attributeInstance = living.getAttribute(attribute);
            AttributeModifier modifier = attributeInstance.getModifier(EntityGravity.ARTIFICIAL_GRAVITY_ID);
            if (modifier != null && modifier.getAmount() != artificialGravity) {
                attributeInstance.removeModifier(EntityGravity.ARTIFICIAL_GRAVITY_ID);
                modifier = null;
            }
            if (modifier == null) {
                modifier = new AttributeModifier(EntityGravity.ARTIFICIAL_GRAVITY_ID, "beyond_earth:artificial_grabity",
                        artificialGravity, Operation.ADDITION);
                attributeInstance.addTransientModifier(modifier);
            }
        }

    }


    @SubscribeEvent
    public static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        Attribute attribute = ForgeMod.ENTITY_GRAVITY.get();
        double gravity = entity.getAttributeValue(attribute) / attribute.getDefaultValue();
        float scale = (float) (gravity - 1);
        scale *= 10 * scale;
        event.setDistance(event.getDistance() - scale);
    }

    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        Level level = event.getLevel();

        if (entity instanceof LivingEntity livingEntity) {
            /** ENTITY GRAVITY SYSTEM */
            EntityGravity.setGravities(livingEntity, level);
        }
    }
}
