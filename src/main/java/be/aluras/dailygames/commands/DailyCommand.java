package be.aluras.dailygames.commands;

import be.aluras.dailygames.DailyReward;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
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

public class DailyCommand extends Command {
    private final DailyReward reward;

    public DailyCommand(DailyReward reward) {
        super("daily");
        this.reward = reward;

        setDefaultExecutor((sender, context) -> {
            if (sender instanceof Player player) {
                UUID uuid = player.getUuid();

                if (reward.playerHasCooldown(uuid)) {
                    long remainingTime = reward.remainingCooldown(uuid);
                    player.sendMessage("§cYou have already claimed your daily! Wait " + reward.formatTime(remainingTime) + " to claim it again.");
                } else {
                    reward.openDailyReward(player);
                }
            }
        });
    }


}
