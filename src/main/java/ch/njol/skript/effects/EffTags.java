package ch.njol.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.chat.ChatMessages;
import ch.njol.util.Kleenean;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.StyleBuilderApplicable;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("MiniMessage tags")
@Description("Add/Remove Mini-Message tag resolvers")
@Examples({
	"register minimessage tag \"notif\" to style \"<##ffff00>\"",
	"register minimessage tag \"error\" to insert \"<color:##ff0000>ERROR: </color>\"",
	"register minimessage tag \"info\" to insert and close \"<color:##0000ff>INFO: \"",
	"delete minimessage tag \"error\""
})
@Since("13/08/23")
public class EffTags extends Effect {

	static {
		Skript.registerEffect(EffTags.class,
			"register minimessage tag %string% to style %string%", // 0
			"register minimessage tag %string% to insert [(1¦and close)] %string%", // 1
			"delete minimessage tag[s] %strings%" // 2
		);
	}

	private Expression<String> name;
	@Nullable
	private Expression<String> replacement = null;
	int pattern;
	boolean close = false;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.pattern = matchedPattern;
		this.name = (Expression<String>) exprs[0];
		if (this.pattern < 2) {
			this.replacement = (Expression<String>) exprs[1];
		}
		if (this.pattern == 1 && parseResult.mark == 1) {
			close = true;
		}
		return true;
	}

	@Override
	protected void execute(Event e) {
		if (pattern == 2) {
			removeTags(e);
			return;
		}
		if (this.replacement == null) return;

		String name = this.name.getSingle(e);
		Component content = ChatMessages.parseComponent(this.replacement.getSingle(e));
		TagResolver resolver;
		switch (pattern) {
			case 0 -> {
				Style style = content.style();
				resolver = TagResolver.resolver(name, Tag.styling(s -> {
					s.merge(style);
				}));
			}
			case 1 -> {
				if (close) resolver = TagResolver.resolver(name, Tag.selfClosingInserting(content));
				else resolver = TagResolver.resolver(name, Tag.inserting(content));
			}
			default -> { return; }
		}
		ChatMessages.addTagResolver(resolver);
	}

	private void removeTags(Event e) {
		String[] names = this.name.getArray(e);
		for (String tagName : names) {
			ChatMessages.removeTagResolver(tagName);
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}
}
