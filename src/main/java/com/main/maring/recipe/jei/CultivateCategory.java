package com.main.maring.recipe.jei;

import com.main.maring.Maring;
import com.main.maring.item.ItemRegister;
import com.main.maring.machine.biotech.CultivateUtil;
import com.main.maring.recipe.CultivateRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CultivateCategory implements IRecipeCategory<CultivateRecipe>
{
    public static final int GRID = 16;
    private final IDrawable[] phIcons = new IDrawable[3];
    private final IDrawable[] wavelengthIcons = new IDrawable[4];
    private final IDrawable[] salinityIcons = new IDrawable[2];
    private final IDrawable[] lightIcons = new IDrawable[2];
    private final IDrawable[] oxygenIcons = new IDrawable[2];
    private final Component title;
    private final IDrawable icon;
    private final IDrawable background;

    public CultivateCategory(IGuiHelper helper) {
        title = Component.translatable("jei.maring.title.cultivate");
        ResourceLocation backgroundImage = new ResourceLocation(Maring.MODID, "textures/gui/jei/cultivate.png");

        int y = 58;
        // ACIDIC, NEUTRAL, ALKALINE
        for (int i = 0; i < 3; i++) {
            phIcons[i] = helper.createDrawable(backgroundImage, i * GRID, y, GRID, GRID);
        }
        y += GRID;

        //true, false
        for (int i = 0; i < 2; i++) {
            lightIcons[i] = helper.createDrawable(backgroundImage, i * GRID, y, GRID, GRID);
        }
        y += GRID;

        //ULTRA_SHORT, SHORT, MEDIUM, LONG
        for (int i = 0; i < 4; i++) {
            wavelengthIcons[i] = helper.createDrawable(backgroundImage, i * GRID, y, GRID, GRID);
        }
        y += GRID;

        //HIGH, LOW
        for (int i = 0; i < 2; i++) {
            salinityIcons[i] = helper.createDrawable(backgroundImage, i * GRID, y, GRID, GRID);
        }
        y += GRID;

        //true, false
        for (int i = 0; i < 2; i++) {
            oxygenIcons[i] = helper.createDrawable(backgroundImage, i * GRID, y, GRID, GRID);
        }


        background = helper.createDrawable(backgroundImage, 0, 0, 117, 57);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegister.FROSTFIRE_FRUIT.get()));
    }

    @Override
    public RecipeType<CultivateRecipe> getRecipeType() {
        return MJEIRecipeTypes.CULTIVATE;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @SuppressWarnings("removal")
    @Override
    @Deprecated(forRemoval = true)
    public IDrawable getBackground() {
        return background;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CultivateRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 21+18).addItemStack(recipe.getCondition().substrate);
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 21-18).addItemStack(recipe.getCondition().catalyst);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 21).addItemStack(recipe.getOutput());
    }

    @Override
    public void draw(CultivateRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        CultivateUtil.CultivateCondition condition = recipe.getCondition();

        switch (condition.ph) {
            case ACIDIC -> phIcons[0].draw(guiGraphics, 58 - GRID / 2, 9 - GRID / 2);
            case NEUTRAL -> phIcons[1].draw(guiGraphics, 58 - GRID / 2, 9 - GRID / 2);
            case ALKALINE -> phIcons[2].draw(guiGraphics, 58 - GRID / 2, 9 - GRID / 2);
        }

        lightIcons[condition.light ? 0 : 1].draw(guiGraphics, 35 - GRID / 2, 25 - GRID / 2);

        switch (condition.wavelength) {
            case ULTRA_SHORT -> wavelengthIcons[0].draw(guiGraphics, 80 - GRID / 2, 25 - GRID / 2);
            case SHORT       -> wavelengthIcons[1].draw(guiGraphics, 80 - GRID / 2, 25 - GRID / 2);
            case MEDIUM      -> wavelengthIcons[2].draw(guiGraphics, 80 - GRID / 2, 25 - GRID / 2);
            case LONG        -> wavelengthIcons[3].draw(guiGraphics, 80 - GRID / 2, 25 - GRID / 2);
        }

        switch (condition.salinity) {
            case LOW  -> salinityIcons[1].draw(guiGraphics, 42 - GRID / 2, 47 - GRID / 2);
            case HIGH -> salinityIcons[0].draw(guiGraphics, 42 - GRID / 2, 47 - GRID / 2);
        }

        oxygenIcons[condition.oxygen ? 0 : 1].draw(guiGraphics, 73 - GRID / 2, 47 - GRID / 2);
    }
}
