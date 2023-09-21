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

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

@Name("Play Totem Effect")
@Description("Play a totem effect for player")
@Examples("play totem effect to event-player")
@Since("your mother")
public class EffSendEquipmentChange extends Effect {

	static {
		Skript.registerEffect(EffSendEquipmentChange.class,
			"make %players% see %entities%['s] (0¦boot[s]|0¦shoe[s]|1¦leg[ging][s]|2¦chestplate[s]|3¦helm[et][s]) slot as %itemstack%");
	}

	@SuppressWarnings("null")
	private Expression<Player> players;
	private Expression<Entity> entities;
	private Expression<ItemStack> item;
	private EquipmentSlot slot;

	private final static EquipmentSlot[] slots = {EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		players = (Expression<Player>) exprs[0];
		entities = (Expression<Entity>) exprs[1];
		item = (Expression<ItemStack>) exprs[2];
		slot = slots[parseResult.mark & 3];
		return true;
	}

	@Override
	protected void execute(final Event e) {
		ItemStack itemStack = item.getSingle(e);
		for (Entity entity : entities.getArray(e)) {
			if (!(entity instanceof LivingEntity livingEntity)) return;
			for (Player player : players.getArray(e)) {
				player.sendEquipmentChange(livingEntity, slot, itemStack);
			}
		}
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "make players see entities slot as item";
	}
}





























