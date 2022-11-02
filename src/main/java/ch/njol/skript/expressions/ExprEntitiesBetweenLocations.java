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
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.BlockingLogHandler;
import ch.njol.skript.log.LogHandler;
import ch.njol.util.Kleenean;
import ch.njol.util.StringUtils;
import ch.njol.util.coll.iterator.CheckedIterator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.BoundingBox;
import org.eclipse.jdt.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.*;

@Name("Entities In Box")
@Description("")
@Since("02/11/2022")
public class ExprEntitiesBetweenLocations extends SimpleExpression<Entity> {

	static {
		Skript.registerExpression(ExprEntitiesBetweenLocations.class, Entity.class, ExpressionType.SIMPLE,
				"entities within %location% and %location%");
	}

	private Expression<Location> pos1;
	private Expression<Location> pos2;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		pos1 = (Expression<Location>) exprs[0];
		pos2 = (Expression<Location>) exprs[1];

		return true;
	}

	@Override
	@Nullable
	@SuppressWarnings("null")
	protected Entity[] get(Event e) {
		Location p1 = pos1.getSingle(e);
		Location p2 = pos2.getSingle(e);
		BoundingBox box = new BoundingBox(p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ());

		return p1.getWorld().getNearbyEntities(box).toArray(new Entity[]{});
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	@SuppressWarnings("null")
	public String toString(@Nullable Event e, boolean debug) {
		return "all of the within " + pos1.toString() + " and " + pos2.toString();
	}

}
