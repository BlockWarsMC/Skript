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
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.lang.reflect.Field;

@Name("Spigot Async Catcher")
@Description("The current status of the Spigot Async Cathcer")
@Examples("set async catcher to false")
@Since("02/12/2022")
public class ExprAsyncCatcher extends SimpleExpression<Boolean> {

	@org.jetbrains.annotations.Nullable private static Field asyncCatcher;
	static {
		Skript.registerExpression(ExprAsyncCatcher.class, Boolean.class, ExpressionType.SIMPLE, "async catcher");

		try {
			asyncCatcher = Class.forName("org.spigotmc.AsyncCatcher").getDeclaredField("enabled");
			asyncCatcher.setAccessible(true);
		} catch (Throwable ignored) {
			asyncCatcher = null;
		}
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (asyncCatcher == null) {
			Skript.error("Can't access the spigot async catcher. Are you not using spigot?!");
			return false;
		}
		return true;
	}

	@Override
	protected @Nullable Boolean[] get(Event e) {
		try {
			return new Boolean[]{ asyncCatcher.getBoolean(asyncCatcher) };
		} catch (Exception ignored) {}
		return new Boolean[]{ false };
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		return CollectionUtils.array(Boolean.class);
	}

	@Override
	public void change(final Event e, final @Nullable Object[] delta, final Changer.ChangeMode mode) {
		if (asyncCatcher == null) {
			Skript.error("Can't access the spigot async catcher. Are you not using spigot?!");
			return;
		}

		try {
			Boolean value = (Boolean) delta[0];
			if (value == null) return;
			asyncCatcher.setBoolean(asyncCatcher, value);
		} catch (Exception ignored) {}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "async catcher";
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
}
