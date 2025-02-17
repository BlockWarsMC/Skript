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
package ch.njol.skript.sections;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ch.njol.skript.command.CommandEvent;
import ch.njol.skript.command.ScriptCommand;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.*;
import ch.njol.skript.log.RedirectingLogHandler;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.ExceptionUtils;
import ch.njol.skript.util.Getter;
import ch.njol.skript.variables.Variables;
import ch.njol.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptCommand;
import ch.njol.skript.config.Config;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.util.FileUtils;
import ch.njol.util.Kleenean;
import ch.njol.util.OpenCloseable;
import org.jetbrains.annotations.NotNull;
import org.skriptlang.skript.lang.script.Script;

import static ch.njol.skript.SkriptCommand.toggleFiles;

@Name("Enable/Disable/Reload Script File")
@Description("Enables, disables, or reloads a script file.")
@Examples({"reload script \"test\"",
	"enable script file \"testing\"",
	"unload script file \"script.sk\""})
@Since("2.4")
public class EffScriptFile extends EffectSection {

	public static class ScriptLoadEvent extends Event {
		private final String file;
		private final boolean success;

		public ScriptLoadEvent(String file, boolean success) {
			this.file = file;
			this.success = success;
		}
		public String getFile() {
			return file;
		}
		public boolean isSuccess() {
			return success;
		}

		@Override
		public @NotNull HandlerList getHandlers() {
			throw new IllegalStateException();
		}
	}

	static {
		Skript.registerSection(EffScriptFile.class, "(1¦enable|1¦load|2¦reload|3¦disable|3¦unload) s(c|k)ript[s] [file] %string%");

		EventValues.registerEventValue(ScriptLoadEvent.class, String.class, new Getter<String, ScriptLoadEvent>() {
			@Override
			public String get(ScriptLoadEvent arg) {
				return arg.getFile();
			}
		}, 0);
		EventValues.registerEventValue(ScriptLoadEvent.class, Boolean.class, new Getter<Boolean, ScriptLoadEvent>() {
			@Override
			public Boolean get(ScriptLoadEvent arg) {
				return arg.isSuccess();
			}
		}, 0);
	}

	private static final int ENABLE = 1, RELOAD = 2, DISABLE = 3;

	private int mark;
	@Nullable
	private Expression<String> fileName;
	@Nullable
	private Trigger trigger;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult, @Nullable SectionNode sectionNode, @Nullable List<TriggerItem> triggerItems) {
		mark = parseResult.mark;
		fileName = (Expression<String>) exprs[0];

		if (sectionNode != null)
			trigger = loadCode(sectionNode, "script", ScriptLoadEvent.class);
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return (mark == ENABLE ? "enable" : mark == RELOAD ? "disable" : mark == DISABLE ? "unload" : "") + " script file " + (fileName != null ? fileName.toString(e, debug) : "");
	}

	@Override
	@Nullable
	protected TriggerItem walk(Event e) {
		String sName = fileName != null ? fileName.getSingle(e) : "";
		String name = sName != null ? sName : "";

		File file = SkriptCommand.getScriptFromName(name);
		if (file == null) {
			runTrigger(name, false, e);
			return super.walk(e, false);
		}

		OpenCloseable logHandler = OpenCloseable.EMPTY;
		if (e instanceof CommandEvent) {
			logHandler = new RedirectingLogHandler(((CommandEvent) e).getSender(), "").start();
		}

		switch (mark) {
			case ENABLE -> {
				if (!file.isDirectory()) {
					try {
						file = SkriptCommand.toggleFile(file, true);
					} catch (IOException ex) {
						runTrigger(name, true, e);
						break;
					}

					final String fileName = file.getName();
					ScriptLoader.loadScripts(file, logHandler)
						.thenAccept(scriptInfo -> runTrigger(name, true, e));
				} else {
					Set<File> scriptFiles;
					try {
						scriptFiles = toggleFiles(file, true);
					} catch (IOException ex) {
						runTrigger(name, true, e);
						break;
					}

					final String fileName = file.getName();
					ScriptLoader.loadScripts(scriptFiles, logHandler)
						.thenAccept(scriptInfo -> runTrigger(name, true, e));
				}
			}
			case RELOAD -> {
				if (!file.isDirectory()) {
					if (file.getName().startsWith("-")) {
						runTrigger(name, false, e);
						break;
					}
					Script script = ScriptLoader.getScript(file);
					if (script != null)
						ScriptLoader.unloadScript(script);
					ScriptLoader.loadScripts(file, logHandler)
						.thenAccept(scriptInfo -> runTrigger(name, true, e));
				} else {
					ScriptLoader.unloadScripts(ScriptLoader.getScripts(file));
					ScriptLoader.loadScripts(file, logHandler)
						.thenAccept(scriptInfo -> runTrigger(name, true, e));
				}
			}
			case DISABLE -> {
				if (!file.isDirectory()) {
					if (file.getName().startsWith("-")) {
						runTrigger(name, false, e);
						break;
					}

					Script script = ScriptLoader.getScript(file);
					if (script == null) {
						runTrigger(name, false, e);
						break;
					}

					ScriptLoader.unloadScript(script);
					runTrigger(name, true, e);
				} else {
					ScriptLoader.unloadScripts(ScriptLoader.getScripts(file));

					try {
						toggleFiles(file, false);
					} catch (Exception ex) {
						runTrigger(name, false, e);
						break;
					}
					runTrigger(name, true, e);
				}
			}
			default -> {
				assert false;
			}
		}
		return super.walk(e, false);
	}

	private void runTrigger(String name, Boolean success, Event runIn) {
		if (trigger != null) {
			ScriptLoadEvent ev = new ScriptLoadEvent(name, success);
			Object localVars = Variables.copyLocalVariables(runIn);
			Variables.setLocalVariables(ev, localVars);
			trigger.execute(ev);
		}
	}
}