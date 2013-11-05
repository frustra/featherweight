package org.frustra.feather.mod.commands;

import org.frustra.feather.Feather;
import org.frustra.feather.mod.server.Entity;
import org.frustra.feather.mod.server.Player;
import org.frustra.feather.mod.voting.KickVote;

public class VoteKickCommand extends Command {

	public String getName() {
		return "votekick";
	}

	public void execute(Entity source, String[] arguments) {
		if (arguments.length == 1) {
			Player sourcePlayer = source.getPlayer();
			if (sourcePlayer.karma <= 0) {
				source.sendMessage("Not enough karma!");
				return;
			}

			Player target = Feather.server.getPlayer(arguments[1]);
			KickVote vote = Feather.server.activeKickVotes.get(target);
			if (vote == null) {
				vote = Feather.server.activeKickVotes.put(target, new KickVote(target));
			}

			if (!vote.addVote(source.getPlayer(), false)) {
				source.sendMessage("Still need more votes to kick %s.", new Object[] { target.name });
			}
		} else {
			// TODO throw a ch
			source.sendMessage("Invalid usage.");
		}
	}

	public boolean hasPermission(Entity source) {
		return true;
	}
}