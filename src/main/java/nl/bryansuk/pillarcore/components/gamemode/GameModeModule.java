package nl.bryansuk.pillarcore.components.gamemode;

import io.papermc.paper.command.brigadier.Commands;
import nl.bryansuk.foundationapi.components.FoundationComponent;
import nl.bryansuk.foundationapi.logging.FoundationLogger;
import nl.bryansuk.foundationapi.plugin.FoundationPlugin;

public class GameModeModule extends FoundationComponent{

    public GameModeModule(FoundationPlugin plugin, FoundationLogger logger) {
        super(plugin, logger);
    }

    @Override
    public void onComponentEnable() throws Exception {}

    @Override
    public void onComponentDisable() throws Exception {

    }

    @Override
    public void registerCommands(Commands commands) {
        GameModeCommand.createGameModeCommand(commands);
    }
}
