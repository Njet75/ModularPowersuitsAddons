package com.qmxtech.powersuitaddons.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.machinemuse.api.IModularItem;
import net.machinemuse.api.MuseItemTag;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AddonUtils {

public static boolean canItemFitInInventory(EntityPlayer player, ItemStack itemstack) {
        for (int i = 0; i < player.inventory.getSizeInventory() - 4; i++) {
                if (player.inventory.getStackInSlot(i) == null) {
                        return true;
                }
        }
        if (!itemstack.isItemDamaged()) {
                if (itemstack.getMaxStackSize() == 1) {
                        return false;
                }
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                        ItemStack invstack = player.inventory.getStackInSlot(i);
                        if (invstack != null && invstack.getItem() == itemstack.getItem() && invstack.isStackable() && invstack.stackSize < invstack.getMaxStackSize() && invstack.stackSize < player.inventory.getInventoryStackLimit() && (!invstack.getHasSubtypes() || invstack.getItemDamage() == itemstack.getItemDamage())) {
                                return true;
                        }
                }
        }
        return false;
}

public static double getFoodLevel(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                Double foodLevel = itemTag.getDouble("Food");
                if (foodLevel != null) {
                        return foodLevel;
                }
        }
        return 0.0;
}

public static void setFoodLevel(ItemStack stack, double d) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setDouble("Food", d);
        }
}

public static double getSaturationLevel(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                Double saturationLevel = itemTag.getDouble("Saturation");
                if (saturationLevel != null) {
                        return saturationLevel;
                }
        }
        return 0.0F;
}

public static void setSaturationLevel(ItemStack stack, double d) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setDouble("Saturation", d);
        }
}

public static int getTorchLevel(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                Integer torchLevel = itemTag.getInteger("Torch");
                if (torchLevel != null) {
                        return torchLevel;
                }
        }
        return 0;
}

public static void setTorchLevel(ItemStack stack, int i) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setInteger("Torch", i);
        }
}

public static double getWaterLevel(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                Double waterLevel = itemTag.getDouble("Water");
                if (waterLevel != null) {
                        return waterLevel;
                }
        }
        return 0;
}

public static void setWaterLevel(ItemStack stack, double d) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setDouble("Water", d);
        }
}

public static void setLiquid(ItemStack stack, String name) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setString("Liquid", name);
        }
}

public static String getLiquid(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                String s = itemTag.getString("Liquid");
                if (s != null) {
                        return s;
                }
        }
        return "";
}

public static int getCoalLevel(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                Integer coalLevel = itemTag.getInteger("Coal");
                if (coalLevel != null) {
                        return coalLevel;
                }
        }
        return 0;
}
public static void setCoalLevel(ItemStack stack, int i) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setInteger("Coal", i);
        }
}

public static String getEIONoCompete(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                if (itemTag != null) {
                        return itemTag.getString("eioNoCompete");
                } else {
                        return "";
                }
        }
        return "";
}

public static void setEIONoCompete(ItemStack stack, String s) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setString("eioNoCompete", s);
        }
}

public static boolean getEIOFacadeTransparency(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                if (itemTag != null) {
                        return itemTag.getBoolean("eioFacadeTransparency");
                }
        }
        return false;
}

public static void setEIOFacadeTransparency(ItemStack stack, boolean b) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = MuseItemUtils.getMuseItemTag(stack);
                itemTag.setBoolean("eioFacadeTransparency", b);
        }
}

public static NBTTagCompound getFluidTermTag(ItemStack stack) {
        NBTTagCompound ret = null;
        if (stack != null && stack.getItem() instanceof IModularItem) {
                ret = MuseItemUtils.getMuseItemTag(stack).getCompoundTag("AppEng EC Wireless Fluid Terminal");
        }
        return ret;
}

public static void setFluidTermTag(ItemStack stack, NBTTagCompound tag) {
        NBTTagCompound t = MuseItemUtils.getMuseItemTag(stack);
        t.setTag("AppEng EC Wireless Fluid Terminal", tag);
        stack.stackTagCompound.setTag(MuseItemTag.NBTPREFIX(), t);
}

public static boolean getCanShrink(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = stack.getTagCompound();
                NBTTagCompound cmTag = ((itemTag.hasKey("CompactMachines")) ? itemTag.getCompoundTag("CompactMachines") : null);
                if (cmTag != null && cmTag.hasKey("canShrink")) {
                        return cmTag.getBoolean("canShrink");
                }
        }
        return false;
}

public static void setCanShrink(ItemStack stack, boolean b) {
        if (stack != null && stack.getItem() instanceof IModularItem) {
                NBTTagCompound itemTag = stack.stackTagCompound;
                NBTTagCompound cmTag = ((itemTag.hasKey("CompactMachines")) ? itemTag.getCompoundTag("CompactMachines") : (new NBTTagCompound()));
                cmTag.setBoolean("canShrink", b);
                itemTag.setTag("CompactMachines", cmTag);
        }
}

public static NBTTagCompound getNBTTag(ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (tag == null) {
                tag = new NBTTagCompound();
                itemStack.setTagCompound(tag);
        }

        return tag;
}

public static boolean isClientWorld(World world) {
        return world.isRemote;
}

public static boolean isServerWorld(World world) {
        return !world.isRemote;
}

public static boolean isClientSide() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
}

public static boolean isServerSide() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER;
}
}
