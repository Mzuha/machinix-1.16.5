package com.mzuha.machinix.block.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mzuha.machinix.block.container.MaceratorContainer;
import com.mzuha.machinix.util.MouseUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

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
        renderEnergyAreaTooltip(matrixStack, mouseX, mouseY);
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
        renderEnergyArea(matrixStack, i, j);
    }

    private void renderEnergyAreaTooltip(MatrixStack stack, int mouseX, int mouseY) {
        if (isMouseAboveArea(mouseX, mouseY, guiLeft, guiTop, 156, 13, 8, 64)) {
            this.renderTooltip(
                stack, new StringTextComponent(
                    container.getEnergyStored() + "/" + container.getMaxEnergyStored() + " E"
                ), mouseX - guiLeft, mouseY - guiTop
            );
        }
    }

    private void renderEnergyArea(MatrixStack matrixStack, int i, int j) {
        final int width = 8;
        final int height = 64;
        int stored = (int) (height * (container.getEnergyStored() / (float) container.getMaxEnergyStored()));
        int x = i + 156;
        int y = j + 13;
        this.fillGradient(
            matrixStack, x, y + (height - stored), x + width, y + height, 0xffb51500, 0xff600b00
        );
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
