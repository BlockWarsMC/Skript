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
package ch.njol.skript.effects.fawe;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.fastasyncworldedit.core.function.pattern.ExpressionPattern;
import com.fastasyncworldedit.core.util.MaskTraverser;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extension.factory.MaskFactory;
import com.sk89q.worldedit.extension.factory.PatternFactory;
import com.sk89q.worldedit.extension.input.ParserContext;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.function.mask.ExpressionMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Replace Blocks")
@Description({"Replace a blocks in a cuboid area using World Edit"})
@Examples({"replace blocks \"##solid\" with \"red_concrete\" between {_pos1} and {_pos2}"})
@Since("30/10/2022")
public class EffReplaceBlocks extends Effect {

	static {
		Skript.registerEffect(EffReplaceBlocks.class,
			"[worldedit] replace blocks [of mask] %string% with [pattern] %string% between %location% and %location%");
	}

	private Expression<String> maskText;
	private Expression<String> patternText;
	private Expression<Location> pos1Location;
	private Expression<Location> pos2Location;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		maskText = (Expression<String>) exprs[0];
		patternText = (Expression<String>) exprs[1];

		pos1Location = (Expression<Location>) exprs[2];
		pos2Location = (Expression<Location>) exprs[3];

		return true;
	}

	@Override
	protected void execute(Event e) {
		Location pos1 = this.pos1Location.getSingle(e);
		Location pos2 = this.pos2Location.getSingle(e);

		new BukkitRunnable(){
			@Override
			public void run() {
				EditSession session = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(pos1.getWorld()));
				CuboidRegion region = new CuboidRegion(session.getWorld(),
					BukkitAdapter.adapt(pos1).toBlockPoint(), BukkitAdapter.adapt(pos2).toBlockPoint());

				ParserContext context = new ParserContext();
				context.setActor(BukkitAdapter.adapt(Bukkit.getConsoleSender()));
				context.setExtent(session);

				Mask mask = new MaskFactory(WorldEdit.getInstance()).parseFromInput(maskText.getSingle(e), context);
				Pattern pattern = new PatternFactory(WorldEdit.getInstance()).parseFromInput(patternText.getSingle(e), context);

				new MaskTraverser(mask).setNewExtent(session);

				session.replaceBlocks(region, mask, pattern);
				session.commit();
				session.close();
			}
		}.runTaskAsynchronously(Skript.getInstance());
	}

	@Override
	public @NotNull String toString(@Nullable Event e, boolean debug) {
		return "replace blocks " + maskText.toString() + " with " + patternText.toString() + " between " + pos1Location.toString() + " and " + pos2Location.toString();
	}
}
