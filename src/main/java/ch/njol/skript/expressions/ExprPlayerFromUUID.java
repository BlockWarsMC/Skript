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
package ch.njol.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Peter Güttinger
 */
@Name("Player From UUID")
@Description("A player from their UUID")
@Examples({"player's gamemode is survival",
		"set the player's gamemode to creative"})
@Since("i got here")
public class ExprPlayerFromUUID extends SimpleExpression<Player> {
	
	static {
		Skript.registerExpression(ExprPlayerFromUUID.class, Player.class, ExpressionType.SIMPLE, "player (from|with) uuid %string%");
	}

	private Expression<String> uuid;

	@Override
	protected @Nullable Player[] get(Event e) {
		UUID id;
		try { id = UUID.fromString(Objects.requireNonNull(uuid.getSingle(e))); }
		catch (Exception ignored) { return new Player[0]; }

		Player p = Bukkit.getPlayer(id);
		if (p == null) return new Player[0];

		return new Player[]{ p };
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		uuid = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "player with uuid " + uuid;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
}
