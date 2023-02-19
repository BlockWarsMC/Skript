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
package ch.njol.skript.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptCommand;
import ch.njol.skript.SkriptEventHandler;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.EnchantmentType;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;

/**
 * @author AsoDesu_
 */
@Name("Can Be Enchanted with")
@Description("Checks if an item can be enchanted with an enchantment")
@Examples("player's held item can be enchanted with mending")
@Since("01/11/2022")
public class CondIsEvents extends Condition {
	static {
		Skript.registerCondition(CondIsEvents.class,
			"%string% has events (1¦enabled|2¦disabled)");
	}

	@SuppressWarnings("null")
	private Expression<String> script;

	@SuppressWarnings("null")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		script = (Expression<String>) exprs[0];
		setNegated(parseResult.mark == 1);
		return true;
	}

	@Override
	public boolean check(final Event e) {
		String fileName = script.getSingle(e);
		if (fileName == null) return false;
		File scriptFile = SkriptCommand.getScriptFromName(fileName);
		if (scriptFile == null) return false;
		if (scriptFile.isDirectory()) return false;
		return SkriptEventHandler.isEventsDisabled(scriptFile);
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "";
	}

}
