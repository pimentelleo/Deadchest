package me.crylonz;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Iterator;

import static me.crylonz.DeadChest.chestData;
import static me.crylonz.DeadChest.fileManager;

public class DeadChestManager {

    /**
     * Remove all active deadchests
     *
     * @return number of deadchests removed
     */
    public static int cleanAllDeadChests() {

        int chestDataRemoved = 0;
        if (chestData != null && !chestData.isEmpty()) {
            Iterator<ChestData> chestDataIt = chestData.iterator();
            while (chestDataIt.hasNext()) {

                ChestData chestData = chestDataIt.next();
                if (chestData.getChestLocation().getWorld() != null) {

                    Location loc = chestData.getChestLocation();
                    loc.getWorld().getBlockAt(loc).setType(Material.AIR);
                    chestData.removeArmorStand();
                    chestDataIt.remove();
                    chestDataRemoved++;
                }
            }
            fileManager.saveModification();
        }
        return chestDataRemoved;
    }

    /**
     * Generate an hologram at the given position
     *
     * @param location position to place
     * @param text     text to display
     * @param shiftX   x shifting
     * @param shiftY   y shifting
     * @param shiftZ   z shifting
     * @return the generated armorstand
     */
    public static ArmorStand generateHologram(Location location, String text, float shiftX, float shiftY, float shiftZ) {
        if (location != null && location.getWorld() != null) {
            Location holoLoc = new Location(location.getWorld(),
                    location.getX() + shiftX,
                    location.getY() + shiftY,
                    location.getZ() + shiftZ);

            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(holoLoc, EntityType.ARMOR_STAND);
            armorStand.setInvulnerable(true);
            armorStand.setGravity(false);
            armorStand.setCanPickupItems(false);
            armorStand.setVisible(false);
            armorStand.setCustomName("× " + text + " ×");
            armorStand.setCustomNameVisible(true);

            return armorStand;
        }
        return null;
    }

    /**
     * get the number of deadchest for a player
     *
     * @param p player
     * @return number of deadchests
     */
    static int playerDeadChestAmount(Player p) {
        int count = 0;
        if (p != null) {
            for (ChestData chestData : chestData) {
                if (p.getUniqueId().toString().equals(chestData.getPlayerUUID()))
                    count++;
            }
        }
        return count;
    }
}
