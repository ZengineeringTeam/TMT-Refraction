package com.teamwizardry.refraction.client.jei;

import com.google.common.collect.ImmutableList;
import com.teamwizardry.librarianlib.client.core.ClientTickHandler;
import com.teamwizardry.librarianlib.client.sprite.Sprite;
import com.teamwizardry.librarianlib.client.sprite.Texture;
import com.teamwizardry.refraction.api.AssemblyRecipe;
import com.teamwizardry.refraction.api.Constants;
import com.teamwizardry.refraction.api.Utils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saad on 10/12/2016.
 */
public class AssemblyTableRecipeWrapper implements IRecipeWrapper {

	private static ResourceLocation loc = new ResourceLocation(Constants.MOD_ID, "textures/gui/assembly_recipe_alpha_bar.png");
	private static Texture texture = new Texture(loc);
	private static Sprite
			BAR = texture.getSprite("bar", 64, 8),
            SLOT = texture.getSprite("slotcomp", 18, 18);
    private ArrayList<ItemStack> inputs = new ArrayList<>();
    private ItemStack output;
    private Color maxColor, currentColor;
	private AssemblyRecipe recipe;
	private int cycleTimer = 0, transitionTicks = 0;
	private boolean cycleSwitch, transition = false;

	public AssemblyTableRecipeWrapper(AssemblyRecipe recipe) {
		this.recipe = recipe;
		inputs.addAll(recipe.getItems());
        output = recipe.getResult();
        maxColor = recipe.getMinColor();
		currentColor = recipe.getMinColor();
		cycleSwitch = true;
		transition = true;
	}

	@Override
    public void getIngredients(@NotNull IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, output);
    }

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (!transition) {
			if (cycleTimer < 50) cycleTimer++;
			else {
				cycleTimer = 0;
				transition = true;
			}
		} else {
			currentColor = Utils.mixColors(currentColor, maxColor, 0.9);
			if (transitionTicks < 50) transitionTicks++;
			else {
				if (cycleSwitch) {
					currentColor = recipe.getMaxColor();
					maxColor = recipe.getMinColor();
				} else {
					currentColor = recipe.getMinColor();
					maxColor = recipe.getMaxColor();
				}
				transitionTicks = 0;
				transition = false;
				cycleSwitch = !cycleSwitch;
			}
		}

		GlStateManager.pushMatrix();

		texture.bind();

		GlStateManager.color(1f, 1f, 1f);
		SLOT.draw(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - SLOT.getWidth() / 2, 100);

		GlStateManager.color(0.3f, 0.3f, 0.3f);
		BAR.draw(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 + 33, 0);
		GlStateManager.color(1f, 1f, 1f);

		GlStateManager.rotate(180, 0, 0, 1);
		GlStateManager.translate(-(recipeWidth - (BAR.getWidth() / 2) + 8), -8, 0);
		BAR.drawClipped(ClientTickHandler.getTicks(), 0, 0, (int) (currentColor.getAlpha() / 255.0 * 64), 8);
		GlStateManager.translate((recipeWidth - (BAR.getWidth() / 2) + 8), 8, 0);
		GlStateManager.rotate(180, 0, 0, -1);

		GlStateManager.color(0.3f, 0, 0);
		BAR.draw(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 + 33, 10);

		GlStateManager.color(1f, 0, 0);
		GlStateManager.rotate(180, 0, 0, 1);
		GlStateManager.translate(-(recipeWidth - (BAR.getWidth() / 2) + 8), -18, 0);
		BAR.drawClipped(ClientTickHandler.getTicks(), 0, 0, (int) (currentColor.getRed() / 255.0 * 64), 8);
		GlStateManager.translate((recipeWidth - (BAR.getWidth() / 2) + 8), 18, 0);
		GlStateManager.rotate(180, 0, 0, -1);

		GlStateManager.color(0, 0.3f, 0);
		BAR.draw(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 - 33, 0);
		GlStateManager.color(0, 1f, 0);
		BAR.drawClipped(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 - 33, 0, (int) (currentColor.getGreen() / 255.0 * 64), 8);

		GlStateManager.color(0, 0, 0.3f);
		BAR.draw(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 - 33, 10);
		GlStateManager.color(0, 0, 1f);
		BAR.drawClipped(ClientTickHandler.getTicks(), recipeWidth / 2 + 1 - BAR.getWidth() / 2 - 33, 10, (int) (currentColor.getBlue() / 255.0 * 64), 8);

		GlStateManager.popMatrix();
	}

    @NotNull
    @Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return ImmutableList.of();
	}

	@Override
	public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}

}
