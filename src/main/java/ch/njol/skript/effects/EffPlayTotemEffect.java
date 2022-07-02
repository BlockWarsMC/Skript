/**
 *   This file is part of Skript.
 *
 *  Skript is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Skript is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Skript.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright Peter Güttinger, SkriptLang team and contributors
 */
package ch.njol.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Play Totem Effect")
@Description("Play a totem effect for player")
@Examples("play totem effect to event-player")
@Since("your mother")
public class EffPlayTotemEffect extends Effect {

	static {
		Skript.registerEffect(EffPlayTotemEffect.class,
			"play totem effect to %players%");
	}

	@SuppressWarnings("null")
	private Expression<Player> players;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		players = (Expression<Player>) exprs[0];
		return true;
	}

	@Override
	protected void execute(final Event e) {
		for (Player player : players.getArray(e))
			player.playEffect(EntityEffect.TOTEM_RESURRECT);
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "play totem effect to " + players.toString(e, debug);
	}
}
