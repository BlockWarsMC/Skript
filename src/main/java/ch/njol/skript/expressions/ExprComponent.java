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
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Utils;
import ch.njol.skript.util.chat.ChatMessages;
import ch.njol.util.Kleenean;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Component")
@Since("2.0")
public class ExprComponent extends PropertyExpression<String, Component> {
	static {
		Skript.registerExpression(ExprComponent.class, Component.class, ExpressionType.COMBINED,
				"component %strings%");
	}
	
	@SuppressWarnings({"unchecked", "null"})
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		setExpr((Expression<? extends String>) exprs[0]);
		return true;
	}
	
	@Override
	protected Component[] get(final Event e, final String[] source) {
		return get(source, s -> ChatMessages.parseComponent(s));
	}
	
	@Override
	public Class<? extends Component> getReturnType() {
		return Component.class;
	}
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "component " + getExpr().toString(e, debug);
	}
	
}
