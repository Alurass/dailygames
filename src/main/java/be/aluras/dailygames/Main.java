package be.aluras.dailygames;

import be.aluras.dailygames.commands.Daily;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

public class Main {
    public static void main(String[] args) {
        final MinecraftServer server = MinecraftServer.init();

        //Create an instance (a world)
        final InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        final InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        //generate the world
        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK);
        });
        //light world
        instanceContainer.setChunkSupplier(LightingChunk::new);

        final GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

        handler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new Daily());

        server.start("0.0.0.0", 25565);
    }
}