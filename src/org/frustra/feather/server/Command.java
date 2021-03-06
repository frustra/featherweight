package org.frustra.feather.server;

import java.util.ArrayList;
import java.util.List;

import org.frustra.filament.injection.annotations.OverrideMethod;
import org.frustra.filament.injection.annotations.ProxyMethod;
import org.frustra.filament.injection.annotations.ReplaceSuperClass;

/**
 * The base class for every command. It gets dynamically rewritten to extend the
 * internal Minecraft command class, and proxies Minecraft's internal methods to
 * our own commands.
 */
@ReplaceSuperClass("Command")
public abstract class Command {
	@ReplaceSuperClass("Command")
	public Command() {}

	/**
	 * Gets the base name of the command, as it would be invoked by an entity.
	 * 
	 * @return the command's name
	 */
	public abstract String getName();

	@OverrideMethod("Command.getName")
	private String _getName() {
		return this.getName();
	}

	/**
	 * Whether a particular entity has access to this command or not.
	 * 
	 * @param source the entity that invoked the command
	 * @return the accessibility of the command
	 */
	public abstract boolean hasPermission(Entity source);

	@OverrideMethod("Command.hasPermission")
	private boolean _hasPermission(Object source) {
		return hasPermission(new Entity(source));
	}

	/**
	 * Called when a particular entity has invoked this command. Permissions
	 * will have already been checked.
	 * 
	 * @param source the entity that invoked the command
	 * @param arguments any arguments given by the player, split by spaces
	 */
	public abstract void execute(Entity source, String[] arguments);

	/**
	 * Gets the usage string of the command, as it would be displayed by the
	 * help command.
	 * 
	 * @return the command's usage string
	 */
	public abstract String getUsage(Entity source);

	@OverrideMethod("Command.getUsage")
	private String _getUsage(Object source) {
		return getUsage(new Entity(source));
	}

	/**
	 * Gets the tab completion candidates for an in progress command.
	 * 
	 * @param source the entity invoking the completion
	 * @param arguments the current arguments so far
	 * @return the list of candidate completions
	 */
	public List<String> getCompletionList(Entity source, String[] arguments) {
		return new ArrayList<String>();
	}

	@OverrideMethod("Command.getCompletionList")
	private List<String> _getCompletionList(Object source, String[] arguments) {
		return getCompletionList(new Entity(source), arguments);
	}

	/**
	 * Executes a command string as the server.
	 * 
	 * @param command the command string
	 */
	public static void execute(String command) {
		execute(Bootstrap.minecraftServer, command);
	}

	@OverrideMethod("Command.handleExecute")
	private void _execute(Entity source, String[] arguments) {
		execute(new Entity(source), arguments);
	}

	/**
	 * Executes a command string as if it were run by a particular source
	 * entity.
	 * 
	 * @param source the proxied entity source
	 * @param command the command string
	 */
	public static void execute(Object source, String command) {
		_execute(Bootstrap.commandManager, source, command);
	}

	@ProxyMethod("CommandManager.executeCommand")
	private static native int _execute(Object instance, Object source, String command);

	/**
	 * Add a command to the command manager to it can be executed.
	 * 
	 * @param command the command object to be added
	 */
	public static void addCommand(Command command) {
		_addCommand(Bootstrap.commandManager, command);
	}

	@ProxyMethod("CommandManager.addCommand")
	private static native Object _addCommand(Object instance, Object command);
}
