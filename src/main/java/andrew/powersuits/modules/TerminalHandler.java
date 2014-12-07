package andrew.powersuits.modules;

import java.util.HashMap;
import java.util.Set;

import andrew.powersuits.common.AddonConfig;
import appeng.api.AEApi;
import appeng.api.config.Settings;
import appeng.api.config.SortDir;
import appeng.api.config.SortOrder;
import appeng.api.config.ViewItems;
import appeng.api.features.IWirelessTermHandler;
import appeng.api.util.IConfigManager;
import extracells.api.ECApi;
import extracells.api.IWirelessFluidTermHandler;
import net.machinemuse.utils.ElectricItemUtils;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Eximius88 on 1/13/14.
 */
@Optional.InterfaceList({
                                @Optional.Interface(iface = "extracells.api.IWirelessFluidTermHandler", modid = "extracells", striprefs = true),
                                @Optional.Interface(iface = "appeng.api.features.IWirelessTermHandler", modid = "appliedenergistics2", striprefs = true)
                        })
public class TerminalHandler implements IWirelessTermHandler, IWirelessFluidTermHandler {

@Optional.Method(modid = "appliedenergistics2")
@Override
public boolean canHandle(ItemStack is) {
        if(is == null)
                return false;
        if(is.getUnlocalizedName() == null)
                return false;
        if(is.getUnlocalizedName().equals("item.powerFist"))
                return true;
        return false;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public boolean usePower(EntityPlayer entityPlayer, double v, ItemStack itemStack) {
        boolean ret = false;
        if (( v * AddonConfig.appengMultiplier ) < ( ElectricItemUtils.getPlayerEnergy( entityPlayer ) * AddonConfig.appengMultiplier ))
        {
                ElectricItemUtils.drainPlayerEnergy(entityPlayer, ( v * AddonConfig.appengMultiplier ) );
                ret = true;
        }

        return ret;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public boolean hasPower(EntityPlayer entityPlayer, double v, ItemStack itemStack) {
        return (( v * AddonConfig.appengMultiplier ) < ( ElectricItemUtils.getPlayerEnergy(entityPlayer) * AddonConfig.appengMultiplier ));
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public IConfigManager getConfigManager(ItemStack itemStack) {
        IConfigManager config = new WirelessConfig(itemStack);
        config.registerSetting( Settings.SORT_BY, SortOrder.NAME );
        config.registerSetting( Settings.VIEW_MODE, ViewItems.ALL );
        config.registerSetting( Settings.SORT_DIRECTION, SortDir.ASCENDING );

        config.readFromNBT(itemStack.getTagCompound());
        return config;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public String getEncryptionKey(ItemStack item) {
        {
                if (item == null) {
                        return null;
                }
                NBTTagCompound tag = openNbtData(item);
                if (tag != null) {
                        return tag.getString("encKey");
                }
                return null;
        }
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public void setEncryptionKey(ItemStack item, String encKey, String name) {
        {
                if (item == null) {
                        return;
                }
                NBTTagCompound tag = openNbtData(item);
                if (tag != null) {
                        tag.setString("encKey", encKey);
                }
        }
}

@Optional.Method(modid = "extracells")
@Override
public boolean isItemNormalWirelessTermToo(ItemStack is) {
        return true;
}

@Optional.Method(modid = "appliedenergistics2")
private static void registerAEHandler(TerminalHandler handler){
  AEApi.instance().registries().wireless().registerWirelessHandler(handler);
}

@Optional.Method(modid = "extracells")
private static void registerECHandler(TerminalHandler handler){
  ECApi.instance().registryWirelessFluidTermHandler(handler);
}

public static void registerHandler() {
        if (Loader.isModLoaded("appliedenergistics2")) {
                TerminalHandler handler = new TerminalHandler();
                registerAEHandler(handler);
                System.out.println("MPSA: Registering AE Terminal Handler :MPSA");
                if (Loader.isModLoaded("extracells")) {
                        registerECHandler(handler);
                }

        }

}

public static NBTTagCompound openNbtData(ItemStack item) {
        NBTTagCompound compound = item.getTagCompound();
        if (compound == null) {
                item.setTagCompound(compound = new NBTTagCompound());
        }
        return compound;
}

@Optional.Interface(iface = "appeng.api.util.IConfigManager", modid = "appliedenergistics2", striprefs = true)
class WirelessConfig implements IConfigManager {

HashMap<Enum, Enum> enums = new HashMap<Enum, Enum>();

final ItemStack stack;

public WirelessConfig(ItemStack itemStack) {
        stack = itemStack;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public Enum getSetting(Enum arg0) {
        if(enums.containsKey(arg0)) {
                return enums.get(arg0);
        }
        return null;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public Set<Enum> getSettings() {
        return enums.keySet();
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public Enum putSetting(Enum arg0, Enum arg1) {
        enums.put(arg0, arg1);
        writeToNBT(stack.getTagCompound());
        return arg1;
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public void registerSetting(Enum arg0, Enum arg1) {
        if(!enums.containsKey(arg0)) {
                putSetting(arg0, arg1);
        }

}

@Optional.Method(modid = "appliedenergistics2")
@Override
public void readFromNBT(NBTTagCompound tagCompound)
{
        NBTTagCompound tag = null;
        if(tagCompound.hasKey("configWirelessTerminal")) {
                tag = tagCompound.getCompoundTag("configWirelessTerminal");
        }

        if(tag == null)
                return;

        for (Enum key : enums.keySet())
        {
                try
                {
                        if ( tag.hasKey( key.name() ) )
                        {
                                String value = tag.getString( key.name() );

                                // Provides an upgrade path for the rename of this value in the API between rv1 and rv2
                                //TODO implement on rv2 update
                                /*if( value.equals( "EXTACTABLE_ONLY" ) ){
                                   value = StorageFilter.EXTRACTABLE_ONLY.toString();
                                   } else if( value.equals( "STOREABLE_AMOUNT" ) ) {
                                   value = LevelEmitterMode.STORABLE_AMOUNT.toString();
                                   }*/

                                Enum oldValue = enums.get( key );

                                Enum newValue = Enum.valueOf( oldValue.getClass(), value );

                                putSetting( key, newValue );
                        }
                }
                catch (IllegalArgumentException e)
                {

                }
        }
}

@Optional.Method(modid = "appliedenergistics2")
@Override
public void writeToNBT(NBTTagCompound tagCompound)
{
        NBTTagCompound tag = new NBTTagCompound();
        if(tagCompound.hasKey("configWirelessTerminal")) {
                tag = tagCompound.getCompoundTag("configWirelessTerminal");
        }

        for (Enum e : enums.keySet())
        {
                tag.setString( e.name(), enums.get( e ).toString() );
        }
        tagCompound.setTag("configWirelessTerminal", tag);

}

}

}
