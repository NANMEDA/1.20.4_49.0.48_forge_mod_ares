package com.main.maring.recipe;

import com.google.gson.JsonObject;
import com.main.maring.machine.biotech.CultivateUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CultivateRecipe implements Recipe<RecipeWrapper> {
    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack output;
    private final CultivateUtil.CultivateCondition condition;
    private String group;

    public CultivateRecipe(ResourceLocation id,String group, Ingredient input, ItemStack output,CultivateUtil.CultivateCondition condition) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
        this.condition = condition;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public CultivateUtil.CultivateCondition getCondition(){
        return this.condition;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.input);
        return nonnulllist;
    }


    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    public boolean matches(RecipeWrapper inv, Level level, CultivateUtil.CultivateCondition condition) {
        return this.condition.equals(condition) && matches(inv,level);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.output.copy();
    }

    public ItemStack getOutput(){
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CULTIVATE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CULTIVATE.get();
    }

    public static class CultivateRecipeSerializer implements RecipeSerializer<CultivateRecipe> {
        public CultivateRecipeSerializer() {
        }
        @Override
        public CultivateRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String groupIn = GsonHelper.getAsString(json, "group", "");
            Ingredient input = Ingredient.of(ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "input")));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonObject condJson = GsonHelper.getAsJsonObject(json, "condition");

            CultivateUtil.PhType ph = CultivateUtil.PhType.valueOf(GsonHelper.getAsString(condJson, "ph").toUpperCase());
            boolean light = GsonHelper.getAsBoolean(condJson, "light");
            CultivateUtil.Wavelength wavelength = CultivateUtil.Wavelength.valueOf(GsonHelper.getAsString(condJson, "wavelength").toUpperCase());
            CultivateUtil.Salinity salinity = CultivateUtil.Salinity.valueOf(GsonHelper.getAsString(condJson, "salinity").toUpperCase());
            boolean oxygen = GsonHelper.getAsBoolean(condJson, "oxygen");

            ItemStack catalyst = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(condJson, "catalyst"));
            ItemStack substrate = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(condJson, "substrate"));

            CultivateUtil.CultivateCondition condition = new CultivateUtil.CultivateCondition(
                    ph, light, wavelength, salinity, oxygen, catalyst, substrate
            );

            return new CultivateRecipe(recipeId,groupIn, input, output, condition);
        }

        @Override
        public CultivateRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(16385);
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();

            CultivateUtil.PhType ph = buffer.readEnum(CultivateUtil.PhType.class);
            boolean light = buffer.readBoolean();
            CultivateUtil.Wavelength wavelength = buffer.readEnum(CultivateUtil.Wavelength.class);
            CultivateUtil.Salinity salinity = buffer.readEnum(CultivateUtil.Salinity.class);
            boolean oxygen = buffer.readBoolean();
            ItemStack catalyst = buffer.readItem();
            ItemStack substrate = buffer.readItem();

            CultivateUtil.CultivateCondition condition = new CultivateUtil.CultivateCondition(
                    ph, light, wavelength, salinity, oxygen, catalyst, substrate
            );

            return new CultivateRecipe(recipeId, groupIn, input, output, condition);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CultivateRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output.copy());

            // 写入 CultivateCondition 各字段
            CultivateUtil.CultivateCondition cond = recipe.condition;
            buffer.writeEnum(cond.ph);
            buffer.writeBoolean(cond.light);
            buffer.writeEnum(cond.wavelength);
            buffer.writeEnum(cond.salinity);
            buffer.writeBoolean(cond.oxygen);
            buffer.writeItem(cond.catalyst);
            buffer.writeItem(cond.substrate);
        }


    }

}

