package com.github.inc0grepoz.quickvoid;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;

public class QuickVoid extends JavaPlugin {

    private static QuickVoid instance;
    private MVWorldManager mvwm;

    @Override
    public void onEnable() {
        instance = this;
        MultiverseCore mvCore = (MultiverseCore) getServer()
                .getPluginManager().getPlugin("Multiverse-Core");
        mvwm = mvCore.getMVWorldManager();
        CommandHandler cmd = new CommandHandler(instance);
        getServer().getPluginCommand("quickvoid").setExecutor(cmd);
    }

    /**
     * Returns a running plugin instance of QuickVoid.
     * 
     * @return a plugin
     */
    public static QuickVoid getInstance() {
        return instance;
    }

    /**
     * Creates a new void world using a preset level.dat
     * and Multiverse-Core.
     * 
     * @param name a desired world name
     * @return <code>true</code> if a world is created succesfully,
     * otherwise, returns <code>false</code>
     */
    public boolean createVoidWorld(String name) {
        saveResource("level.dat", false);
        File container = Bukkit.getWorldContainer();
        File oldLevelFile = new File(getDataFolder(), "level.dat");
        File newLevelDir = new File(container, name);
        if (!newLevelDir.isDirectory()) {
            newLevelDir.mkdir();
        }
        File newLevelFile = new File(newLevelDir, "level.dat");
        try {
            Files.move(oldLevelFile.toPath(), newLevelFile.toPath());
        } catch (IOException e) {
            return false;
        }
        boolean success = mvwm.addWorld(name, World.Environment.NORMAL, null, null, null, null);
        if (success) {
            World world = Bukkit.getWorld(name);
            Block ground = world.getSpawnLocation().clone()
                    .subtract(0.0D, 1.0D, 0.0D).getBlock();
            if (ground.getType() == Material.AIR) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        world.getBlockAt(ground.getX() + x, ground.getY(), ground.getZ() + z)
                                .setType(Material.BEDROCK);
                    }
                }
            }
        }
        return success;
    }

}
