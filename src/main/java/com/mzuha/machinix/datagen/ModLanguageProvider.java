package com.mzuha.machinix.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Locale;

import static com.mzuha.machinix.MachinixMod.MOD_ID;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, MOD_ID, Locale.US.toString().toLowerCase());
    }

    @Override
    protected void addTranslations() {
        add("block.machinix.crusher_block", "Crusher");
        add("screen.machinix.crusher", "Crusher");
    }
}
