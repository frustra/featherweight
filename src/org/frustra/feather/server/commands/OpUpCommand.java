package org.frustra.feather.server.commands;

import java.util.ArrayList;
import java.util.List;

import org.frustra.feather.server.Bootstrap;
import org.frustra.feather.server.Command;
import org.frustra.feather.server.CommandException;
import org.frustra.feather.server.CommandUsageException;
import org.frustra.feather.server.Entity;
import org.frustra.feather.server.Player;

public class OpUpCommand extends Command {
	public String getName() {
		return "opup";
	}

	public boolean hasPermission(Entity source) {
		Player p = source.getPlayer();
		return p == null || p.isAllowedOperator() || p.isOperator();
	}

	public void execute(Entity source, String[] arguments) {
		if (arguments.length == 0) {
			if (source.isOperator()) {
				throw new CommandException("You're already an operator");
			}
			Command.execute("op " + source.getName());
			source.sendMessage("You now have op");
		} else if (arguments.length == 1) {
			Player target = Bootstrap.server.fetchPlayer(arguments[0]);
			if (target == null) {
				throw new CommandException(arguments[0] + " hasn't played here");
			}
			if (target.isAllowedOperator()) {
				throw new CommandException(target + " is already allowed to /opup");
			}
			target.makeAllowedOperator();
			target.sendMessage("You now have access to /opup");
			source.sendMessage(target + " now has access to /opup");
		} else throw new CommandUsageException(this);
	}

	public String getUsage(Entity source) {
		return "/opup [player]";
	}

	public List<String> getCompletionList(Entity source, String[] arguments) {
		ArrayList<String> list = new ArrayList<String>();
		if (arguments.length == 1) {
			for (Player p : Bootstrap.server.getPlayers()) {
				if (p.getName().startsWith(arguments[0])) {
					list.add(p.getName());
				}
			}
		}
		return list;
	}
}
