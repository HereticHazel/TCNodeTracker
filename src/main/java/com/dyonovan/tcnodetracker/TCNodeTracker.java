package com.dyonovan.tcnodetracker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dyonovan.tcnodetracker.bindings.KeyBindings;
import com.dyonovan.tcnodetracker.events.ClientConnectionEvent;
import com.dyonovan.tcnodetracker.events.KeyInputEvent;
import com.dyonovan.tcnodetracker.events.RightClickEvent;
import com.dyonovan.tcnodetracker.gui.GuiPointer;
import com.dyonovan.tcnodetracker.handlers.ConfigHandler;
import com.dyonovan.tcnodetracker.lib.Constants;
import com.dyonovan.tcnodetracker.lib.DimList;
import com.dyonovan.tcnodetracker.lib.NodeList;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@Mod(
        name = Constants.MODNAME,
        modid = Constants.MODID,
        version = Constants.VERSION,
        dependencies = Constants.DEPENDENCIES,
        acceptableRemoteVersions = "*")
public class TCNodeTracker {

    public static Logger LOGGER = LogManager.getLogger(Constants.MODID);
    public static String hostName;
    public static ArrayList<NodeList> nodelist = new ArrayList<>();
    public static boolean doGui = false;
    public static int xMarker, yMarker, zMarker;
    public static List<DimList> dims = new ArrayList<>();

    @Instance(Constants.MODID)
    public static TCNodeTracker instance;

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        ConfigHandler.init(event.getSuggestedConfigurationFile());

        MinecraftForge.EVENT_BUS.register(new RightClickEvent());
        FMLCommonHandler.instance().bus().register(new ClientConnectionEvent());
        FMLCommonHandler.instance().bus().register(new KeyInputEvent());
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void init(FMLInitializationEvent event) {
        KeyBindings.init();
    }

    @SideOnly(Side.CLIENT)
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new GuiPointer(Minecraft.getMinecraft()));
        ClientCommandHandler.instance.registerCommand(new Exporter());
    }
}
