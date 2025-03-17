package be.aluras.dailygames;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DailyReward {
    private final Map<UUID, Long> lastClaimed = new HashMap<>();
    private final long cooldown = Duration.ofHours(24).toMillis();

    public boolean playerHasCooldown(UUID uuid) {
        return lastClaimed.containsKey(uuid) && (System.currentTimeMillis() - lastClaimed.get(uuid)) < cooldown;
    }

    public long remainingCooldown(UUID uuid) {
        if (!playerHasCooldown(uuid)) {
            return 0;
        }
        return (lastClaimed.get(uuid) + cooldown) - System.currentTimeMillis();
    }

    public void openDailyReward(Player player) {
        Inventory dailyInventory = new Inventory(InventoryType.CHEST_1_ROW, "§6Daily Rewards");
        dailyInventory.setItemStack(4, ItemStack.of(Material.DIAMOND));

        player.openInventory(dailyInventory);
        final GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(InventoryClickEvent.class, event -> {
            if (event.getInventory().equals(dailyInventory) && event.getSlot() == 4) {
                UUID uuid = player.getUuid();
                lastClaimed.put(uuid, System.currentTimeMillis());
                player.getInventory().addItemStack(ItemStack.of(Material.DIAMOND).withAmount(1));
                player.sendMessage("§aYou have claimed your daily reward!");
                player.closeInventory();
            }
        });
    }

    public String formatTime(long millis) {
        final long hours = (millis / 1000) / 3600;
        final long minutes = ((millis / 1000) % 3600) / 60;
        final long seconds = (millis / 1000) % 60;
        return hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }
}

