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

import ch.njol.skript.util.EnchantmentType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionList;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

/**
 * @author AsoDesu_
 */
@Name("Can Be Enchanted with")
@Description("Checks if an item can be enchanted with an enchantment")
@Since("01/11/2022")
public class CondCanEnchant extends Condition {
	static {
		Skript.registerCondition(CondCanEnchant.class,
			"%itemstack% can be enchanted with %enchantmenttype%");
	}

	@SuppressWarnings("null")
	private Expression<ItemStack> itemstack;
	private Expression<EnchantmentType> enchantment;

	@SuppressWarnings("null")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final ParseResult parseResult) {
		itemstack = (Expression<ItemStack>) exprs[0];
		enchantment = (Expression<EnchantmentType>) exprs[1];
		return true;
	}

	@Override
	public boolean check(final Event e) {
		ItemStack item = itemstack.getSingle(e);
		EnchantmentType enchant = enchantment.getSingle(e);
		if (item == null || enchant == null) return false;
		return enchant.getType().canEnchantItem(item);
	}

	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return itemstack + " can be enchanted with " + enchantment;
	}

}
