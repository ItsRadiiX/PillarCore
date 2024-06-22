package nl.bryansuk.pillarcore;

import nl.bryansuk.foundationapi.components.FoundationComponent;
import nl.bryansuk.foundationapi.dependencies.Dependency;
import nl.bryansuk.foundationapi.plugin.FoundationPlugin;
import nl.bryansuk.foundationapi.startup.StartupTask;
import nl.bryansuk.pillarcore.components.gamemode.GameModeModule;

import java.util.List;

public class PillarCore extends FoundationPlugin {

    @Override
    protected List<Dependency<?>> getDependencies() {
        return List.of();
    }

    @Override
    protected List<FoundationComponent> getComponents() {
        return List.of(new GameModeModule(this, getFoundationLogger()));
    }

    @Override
    protected List<StartupTask> startupTasks() {
        return List.of();
    }

    @Override
    protected void onPluginLoad() {

    }

    @Override
    protected void onPluginEnable() {
        getFoundationLogger().logToConsole("Plugin enabled");
    }

    @Override
    protected void onPluginDisable() {

    }
}
