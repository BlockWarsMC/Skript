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
package ch.njol.skript.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.*;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Name("Run code async or on main")
@Description("Run code async or on main")
@Examples({"run async:",
	"run on main thread:"})
@Since("i got here")
public class SecRunThread extends Section {

	static {
		Skript.registerSection(SecRunThread.class, "run [on] (1¦async|2¦main) [thread]");
	}

	public static class RunThread extends Event {

		public RunThread() {}
		@Override
		public @NotNull HandlerList getHandlers() {
			throw new IllegalStateException();
		}
	}

	private int mark;
	@Nullable
	private Trigger trigger;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, SectionNode sectionNode, List<TriggerItem> triggerItems) {
		mark = parseResult.mark;

		if (sectionNode != null)
			trigger = loadCode(sectionNode, "run");
		else
			return false;
		return true;
	}

	@Override
	protected @Nullable TriggerItem walk(Event e) {
		RunThread ev = new RunThread();
		Object localVars = Variables.copyLocalVariables(e);
		Variables.setLocalVariables(ev, localVars);

		BukkitRunnable runnable = new BukkitRunnable(){
			@Override
			public void run() {
				trigger.execute(ev);
			}
		};

		if (mark == 1) {
			runnable.runTaskAsynchronously(Skript.getInstance());
		} else if (mark == 2) {
			runnable.runTask(Skript.getInstance());
		}

		return super.walk(e, false);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "run [on] (1¦async|2¦main) [thread]";
	}
}
