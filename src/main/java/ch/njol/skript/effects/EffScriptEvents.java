package ch.njol.skript.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptEventHandler;
import ch.njol.skript.config.Config;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.sections.EffScriptFile;
import ch.njol.util.Kleenean;
import ch.njol.util.OpenCloseable;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static ch.njol.skript.ScriptLoader.disabledScriptFilter;
import static ch.njol.skript.SkriptCommand.getScriptFromName;

/**
 * @author AsoDesu_
 */
@Name("Script Events")
@Description("Enables or disables the EVENTS for scripts, works with individual scripts or a directory")
@Examples({
	"enable events for scripts \"games/SKYZONE/game.sk\"",
	"disable events for scripts \"decision_donut/\""
})
@Since("18/02/2023")
public class EffScriptEvents extends Effect {

	static {
		Skript.registerEffect(EffScriptEvents.class, "(1¦enable|2¦disable) events [(in|for)] s(c|k)ript[s] %string%");
	}

	static final int ENABLE = 1, DISABLE = 2;
	int mark;
	@Nullable private Expression<String> script;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		mark = parseResult.mark;
		script = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected void execute(Event e) {
		assert script != null;
		String fileName = script.getSingle(e);
		if (fileName == null) return;
		File file = getScriptFromName(fileName);
		if (file == null) return;
		List<File> scripts = getFiles(file);

		// This isn't executed on the async loader thread, because we want games to start AFTER their events have been re-registered.
		// It seems kinda silly to start a game BEFORE it has any events registered
		if (mark == ENABLE) scripts.forEach(SkriptEventHandler::enableEvents);
		else if (mark == DISABLE) scripts.forEach(SkriptEventHandler::disableEvents);
	}

	public static List<File> getFiles(File directory) {
		if (!directory.isDirectory()) {
			return Collections.singletonList(directory);
		}
		File[] files = directory.listFiles(disabledScriptFilter);
		Arrays.sort(files);
		List<File> loadedDirectories = new ArrayList<>(files.length);
		List<File> loadedFiles = new ArrayList<>(files.length);
		for (File file : files) {
			if (file.isDirectory()) {
				loadedDirectories.addAll(getFiles(file));
			} else {
				loadedFiles.add(file);
			}
		}
		loadedDirectories.addAll(loadedFiles);
		return loadedDirectories;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return mark == 1 ? "enable" : "disable" + " events for scripts " + script.toString(e, debug);
	}
}
