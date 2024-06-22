package nl.bryansuk.pillarcore.components.gamemode;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameModeCommand {
    public static void createGameModeCommand(Commands commands) {

        // MAIN GAMEMODE COMMAND
        LiteralArgumentBuilder<CommandSourceStack> mainCommand = Commands.literal("gamemode")
                .requires(source -> permissionLogic(source, "pillarcore.command.gamemode"));

        addGameModeToCommand(mainCommand, GameMode.SURVIVAL);
        addGameModeToCommand(mainCommand, GameMode.ADVENTURE);
        addGameModeToCommand(mainCommand, GameMode.CREATIVE);
        addGameModeToCommand(mainCommand, GameMode.SPECTATOR);

        commands.register(mainCommand.build(), "Gamemode command", List.of("gm", "gmode"));

        commands.register(createAliasGameModeCommand(GameMode.SURVIVAL, "gms").build(), "Gamemode survival");
        commands.register(createAliasGameModeCommand(GameMode.ADVENTURE, "gma").build(), "Gamemode adventure");
        commands.register(createAliasGameModeCommand(GameMode.CREATIVE, "gmc").build(), "Gamemode creative");
        commands.register(createAliasGameModeCommand(GameMode.SPECTATOR, "gmsp").build(), "Gamemode spectator");


    }

    private static LiteralArgumentBuilder<CommandSourceStack> createAliasGameModeCommand(GameMode gameMode, String alias){
        return Commands.literal(alias)
                .requires(source -> permissionLogic(source, "pillarcore.command.gamemode"))
                .executes(context -> changeGameModeLogic(context, gameMode, false))
                .then(addPlayerArgumentToCommand(gameMode));
    }

    private static void addGameModeToCommand(LiteralArgumentBuilder<CommandSourceStack> command, GameMode gameMode) {
        String gameModeString = gameMode.toString().toLowerCase();
        command.then(Commands.literal(gameModeString)
                .requires(source -> permissionLogic(source, "pillarcore.command.gamemode." + gameModeString))
                .executes(context -> changeGameModeLogic(context, gameMode, false))

                // Argument for when we want to specify a different player
                .then(addPlayerArgumentToCommand(gameMode)));
    }

    private static ArgumentBuilder<CommandSourceStack, ?> addPlayerArgumentToCommand(GameMode gameMode){
        return Commands.argument("player", ArgumentTypes.player())
                .requires(source -> permissionLogic(source, "pillarcore.command.gamemode.others"))
                .executes(context -> changeGameModeLogic(context, gameMode, true));
    }


    private static boolean permissionLogic(CommandSourceStack source, String permission){
        Entity executor = source.getExecutor();
        if (executor instanceof Player p ) {
            return p.hasPermission(permission);
        }
        return false;
    }

    private static int changeGameModeLogic(CommandContext<CommandSourceStack> context, GameMode gameMode, boolean hasTargetArgument) throws CommandSyntaxException {
        Player target = null;

        if (hasTargetArgument){
            target = context.getArgument("player", PlayerSelectorArgumentResolver.class)
                    .resolve(context.getSource()).getFirst();
        } else {
            Entity executor = context.getSource().getExecutor();
            if (executor instanceof Player player) target = player;
        }

        if (target != null) target.setGameMode(gameMode);
        return Command.SINGLE_SUCCESS;
    }
}
