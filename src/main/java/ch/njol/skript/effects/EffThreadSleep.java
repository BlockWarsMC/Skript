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

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.timings.SkriptTimings;
import ch.njol.skript.util.Timespan;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

@Name("Thread Sleep")
@Description("")
@Examples({"wait 2 minutes",
	"halt for 5 minecraft hours",
	"wait a tick"})
@Since("1.4")
public class EffThreadSleep extends Effect {
	static {
		Skript.registerEffect(EffThreadSleep.class, "[thread] sleep [goodnight] %timespan%");
	}

	@SuppressWarnings("null")
	protected Expression<Timespan> duration;

	@SuppressWarnings({"unchecked", "null"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		getParser().setHasDelayBefore(Kleenean.TRUE);
		duration = (Expression<Timespan>) exprs[0];
		if (duration instanceof Literal) { // If we can, do sanity check for delays
			long millis = ((Literal<Timespan>) duration).getSingle().getMilliSeconds();
			if (millis < 50) {
				Skript.warning("Delays less than one tick are not possible, defaulting to one tick.");
			}
		}
		return true;
	}

	@Override
	@Nullable
	protected TriggerItem walk(final Event e) {
		debug(e, true);
		final TriggerItem next = getNext();
		if (next != null && Skript.getInstance().isEnabled()) { // See https://github.com/SkriptLang/Skript/issues/3702
			final Timespan d = duration.getSingle(e);
			if (d == null)
				return null;

			try {
				Thread.sleep(d.getMilliSeconds());
				TriggerItem.walk(next, e);
			} catch (InterruptedException ignored) {}
		}
		return null;
	}

	@Override
	protected void execute(final Event e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "thread sleep" + duration.toString(e, debug) + (e == null ? "" : "...");
	}

}
