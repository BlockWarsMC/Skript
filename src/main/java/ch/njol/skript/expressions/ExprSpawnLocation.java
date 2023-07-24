package ch.njol.skript.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

@Name("Spawn Location")
@Description({"Sets player's spawn location"})
@Examples({"on player spawn location:",
	"	set spawn location to {hub}"})
@Since("ur mom")
public class ExprSpawnLocation extends SimpleExpression<Location> {

	static {
		Skript.registerExpression(ExprSpawnLocation.class, Location.class, ExpressionType.SIMPLE, "[player]['s] spawn location");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (!getParser().isCurrentEvent(PlayerSpawnLocationEvent.class)) {
			Skript.error("'player spawn location' can only be used in Player Spawn Location events");
			return false;
 		}
		return true;
	}

	@Override
	@Nullable
	public Location[] get(Event e) {
		if (e instanceof PlayerSpawnLocationEvent locationEvent) {
			return new Location[]{ locationEvent.getSpawnLocation() };
		}
		return null;
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (getParser().getHasDelayBefore().isTrue()) {
			Skript.error("Can't change the location anymore after the player spawn location event has already passed");
			return null;
		}
		switch (mode) {
			case SET:
			case DELETE:
			case RESET:
				return CollectionUtils.array(Location.class);
		}
		return null;
	}

	@SuppressWarnings("null")
	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		if (!(e instanceof PlayerSpawnLocationEvent locationEvent)) return;
		if (delta.length == 0) return;
		Object change = delta[0];

		if (change instanceof Location location) {
			locationEvent.setSpawnLocation(location);
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the player spawn location";
	}

}