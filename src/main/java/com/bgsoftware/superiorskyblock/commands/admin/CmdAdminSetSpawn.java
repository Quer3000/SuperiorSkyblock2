package com.bgsoftware.superiorskyblock.commands.admin;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.commands.CommandContext;
import com.bgsoftware.superiorskyblock.api.commands.arguments.CommandArgument;
import com.bgsoftware.superiorskyblock.commands.InternalSuperiorCommand;
import com.bgsoftware.superiorskyblock.core.logging.Log;
import com.bgsoftware.superiorskyblock.core.messages.Message;
import com.bgsoftware.superiorskyblock.core.serialization.Serializers;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CmdAdminSetSpawn implements InternalSuperiorCommand {

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("setspawn");
    }

    @Override
    public String getPermission() {
        return "superior.admin.setspawn";
    }

    @Override
    public String getDescription(java.util.Locale locale) {
        return Message.COMMAND_DESCRIPTION_ADMIN_SET_SPAWN.getMessage(locale);
    }

    @Override
    public List<CommandArgument<?>> getArguments() {
        return Collections.emptyList();
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return false;
    }

    @Override
    public void execute(SuperiorSkyblockPlugin plugin, CommandContext context) {
        Player player = (Player) context.getDispatcher();
        Location playerLocation = player.getLocation();

        Location spawnLocation = new Location(player.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(),
                playerLocation.getBlockZ(), playerLocation.getYaw(), playerLocation.getPitch());

        String newSpawnLocation = Serializers.LOCATION_SPACED_SERIALIZER.serialize(spawnLocation);

        try {
            plugin.getSettings().updateValue("spawn.location", newSpawnLocation);
            plugin.getGrid().updateSpawn();
        } catch (Exception error) {
            Log.entering("ENTER", spawnLocation);
            Log.error(error, "An unexpected error occurred while setting spawn:");
        }

        Message.SPAWN_SET_SUCCESS.send(player, newSpawnLocation);
    }

}
