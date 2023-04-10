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
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

/**
 * @author Peter Güttinger
 */
@Name("Actual Angle")
@Description("The actual angle between 2 vectors")
@Since("1.0")
public class ExprActualAngle extends SimpleExpression<Number> {
	
	static {
		Skript.registerExpression(ExprActualAngle.class, Number.class, ExpressionType.COMBINED, "angle between target %location% and player %player%");
	}
	
	@SuppressWarnings("null")
	private Expression<Location> exprTarget = null;
	private Expression<Player> exprPlayer = null;

	@SuppressWarnings({"unchecked", "null"})
	@Override
	public boolean init(final Expression<?>[] vars, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		exprTarget = (Expression<Location>) vars[0];
		exprPlayer = (Expression<Player>) vars[1];
		return true;
	}
	
	@Override
	@Nullable
	protected Number[] get(final Event e) {
		final Location target = exprTarget.getSingle(e);
		Player player = exprPlayer.getSingle(e);
		Location pos = player.getLocation();

		double angleFromEntity = Math.atan2(target.z() - pos.z(), target.x() - pos.x()) / 6.2831854820251465;
		double rotationY = positiveModulo((pos.getYaw() / 360), 1);
		double angle = 0.5 - ( rotationY - 0.25 - angleFromEntity );

		return new Number[]{ positiveModulo(angle, 1) };
	}

	private double positiveModulo(double d, double e) {
		return (d % e + e) % e;
	}
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "angle between target %location% and player %player%";
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	
}
