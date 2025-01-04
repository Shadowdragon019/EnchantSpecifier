package com.roxxane.enchant_specifier.data_generation;

import com.roxxane.enchant_specifier.Es;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EsLanguageProvider extends LanguageProvider {
    public EsLanguageProvider(PackOutput output, String locale) {
        super(output, Es.id, locale);
    }

    @Override
    protected void addTranslations() {
        add("chat.enchant_specifier.reload_error", "§cEnchant Specifier failed to reload!");
    }
}
