package com.qmxtech.powersuitaddons.client;


import com.qmxtech.powersuitaddons.common.*;
import com.qmxtech.powersuitaddons.tick.MPSACommonTickHandler;
import com.qmxtech.powersuitaddons.tick.MPSARenderHandler;
import com.qmxtech.powersuitaddons.tick.MPSATickHandler;
import com.google.common.base.Charsets;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ClientProxy extends CommonProxy {


    public static final String LANG_PATH = "/assets/powersuitaddons/lang/";
    public static String extractedLanguage = "";


    @SideOnly(Side.CLIENT)
    public static String getCurrentLanguage() {
        return Minecraft.getMinecraft().gameSettings.language;
    }


    @SideOnly(Side.CLIENT)
    public static void loadCurrentLanguage() {

        if (AddonUtils.isClientSide()) {
            if (getCurrentLanguage() != extractedLanguage) {
                extractedLanguage = getCurrentLanguage();
            }
            try {
                InputStream inputStream = ModularPowersuitsAddons.INSTANCE.getClass().getResourceAsStream(LANG_PATH + extractedLanguage + ".lang");
                Properties langPack = new Properties();
                langPack.load(new InputStreamReader(inputStream, Charsets.UTF_8));
                LanguageRegistry.instance().addStringLocalization(langPack, extractedLanguage);
            } catch (Exception e) {
                e.printStackTrace();
                AddonLogger.logError("Couldn't read MPSA localizations for language " + extractedLanguage + " :(");
            }
        }
    }


    @SideOnly(Side.CLIENT)
    public static String translate(String str) {
        loadCurrentLanguage();
        return StatCollector.translateToLocal(str);
    }


    @Override
    public void registerHandlers() {
        super.registerHandlers();

        if (AddonConfig.useHUDStuff) {
            FMLCommonHandler.instance().bus().register(new MPSARenderHandler(Minecraft.getMinecraft()));
        }

        FMLCommonHandler.instance().bus().register(new MPSATickHandler(Minecraft.getMinecraft()));

    }

}
