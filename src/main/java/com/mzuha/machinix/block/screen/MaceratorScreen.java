package com.mzuha.machinix.block.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mzuha.machinix.block.container.MaceratorContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class MaceratorScreen extends ContainerScreen<MaceratorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(MOD_ID,
        "textures/gui/macerator_gui.png");

    public MaceratorScreen(MaceratorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    private void renderProgressArrow(MatrixStack stack, int x, int y) {
        if (container.isCrafting()) {
            blit(stack, x + 85, y + 30, 176, 0, 8, container.getScaledProgress());
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        renderProgressArrow(matrixStack, i, j);
    }
}
