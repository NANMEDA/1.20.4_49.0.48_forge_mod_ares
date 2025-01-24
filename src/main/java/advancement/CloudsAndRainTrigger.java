package advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;

public class CloudsAndRainTrigger extends SimpleCriterionTrigger<CloudsAndRainTrigger.TriggerInstance> {
	   public Codec<CloudsAndRainTrigger.TriggerInstance> codec() {
		      return CloudsAndRainTrigger.TriggerInstance.CODEC;
		   }

		   public void trigger(ServerPlayer p_38773_, ItemStack p_38774_) {
		      this.trigger(p_38773_, (p_38777_) -> {
		         return p_38777_.matches(p_38774_);
		      });
		   }

		   public static record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {
		      public static final Codec<CloudsAndRainTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((p_308133_) -> {
		         return p_308133_.group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player")
		        		 .forGetter(CloudsAndRainTrigger.TriggerInstance::player), 
		        		 ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item")
		        		 .forGetter(CloudsAndRainTrigger.TriggerInstance::item)).apply(p_308133_, CloudsAndRainTrigger.TriggerInstance::new);
		      });

		      public static Criterion<FilledBucketTrigger.TriggerInstance> filledBucket(ItemPredicate.Builder p_297424_) {
		         return CriteriaTriggers.FILLED_BUCKET.createCriterion(new FilledBucketTrigger.TriggerInstance(Optional.empty(), Optional.of(p_297424_.build())));
		      }

		      public boolean matches(ItemStack p_38792_) {
		         return !this.item.isPresent() || this.item.get().matches(p_38792_);
		      }

		      public Optional<ContextAwarePredicate> player() {
		         return this.player;
		      }
		   }
}
