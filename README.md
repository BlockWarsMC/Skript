# Skript

### This is a fork of [Skript](https://github.com/SkriptLang/Skript/), for the Blockwars Minecraft Event!
- This fork contains changes created by the [Blockwars Team](https://github.com/BlockWarsMC), [SkriptLang](SkriptLang), [TPGamesNL](https://github.com/TPGamesNL) and [AyhamAl-Ali](AyhamAl-Ali)
- Changes Made:
  - [Extra Vehicle Events/Expressions](https://github.com/SkriptLang/Skript/pull/4329)
  - [Adds InventoryMoveItemEvent](https://github.com/SkriptLang/Skript/pull/5462)
  - Remove Warnings (they were annoying Aso)
  - Update `EffScriptFile` to an EffectSection
  - Update `SimpleEvents` to add PlayerInteractEvent
  - Adds `EffReplaceBlocks` for integration with FAWE
  - Adds `EffRemoveEntity` to remove an entity instead of killing it
  - Adds the ability to add fonts and other stylings into titles
  - Adds disabling/enabling events without disabling/enabling skript files
  - Adds support for Kyori Adventure & MiniMessage
#### This fork contains changes specifically for the BlockWars codebase

Skript is a plugin for Paper, which allows server owners and other people
to modify their servers without learning Java. It can also be useful if you
*do* know Java; some tasks are quicker to do with Skript, and so it can be used
for prototyping etc.

This Github fork of Skript is based on Mirreski's improvements which was built
on Njol's original Skript.

## Requirements
Skript requires **Paper** to work, and **FastAsyncWorldEdit** is also required.

Requires: **1.19+** to work, older/newer versions may work, but this fork is intented on
our needed functionality, and **not** backwards compatibility.

## Documentation
Documentation is available [here](https://skriptlang.github.io/Skript) for the
latest version of Skript.

## Compiling
Skript uses Gradle for compilation. Use your command prompt of preference and
navigate to Skript's source directory. Then you can just call Gradle to compile
and package Skript for you:

```bash
./gradlew clean build # on UNIX-based systems (mac, linux)
gradlew clean build # on Windows
```

You can get source code from the [releases page](https://github.com/SkriptLang/Skript/releases).
You may also clone this repository, but that code may or may not be stable.

### Compiling Modules
Parts of Skript are provided as Gradle subprojects. They require Skript, so
they are compiled *after* it has been built. For this reason, if you want them
embedded in Skript jar, you must re-package it after compiling once. For example:

```
./gradlew jar
```

Note that modules are not necessary for Skript to work. Currently, they are
only used to provide compatibility with old WorldGuard versions.

### Testing
Skript has some tests written in Skript. Running them requires a Minecraft
server, but our build script will create one for you. Running the tests is easy:

```
./gradlew (quickTest|skriptTest|skriptTestJava8|skriptTestJava17)
```

<code>quickTest</code> runs the test suite on newest supported server version.
<code>skriptTestJava17</code> (1.17+) runs the tests on the latest supported Java version.
<code>skriptTestJava8</code> (1.13-1.16) runs the tests on the oldest supported Java version.
<code>skriptTest</code> runs both skriptTestJava8 and skriptTestJava17

By running the tests, you agree to Mojang's End User License Agreement.

### Importing to Eclipse
With new Eclipse versions, there is integrated Gradle support, and it actually works now.
So, first get latest Eclipse, then import Skript as any Gradle project. Just
make sure to **keep** the configuration when the importer asks for that!

If you encounter strange issues, make sure you follow the instructions above and have
actually downloaded latest Eclipse or update your installation correctly. Skript's
new Gradle version (starting from dev26) does not work very well with older Eclipse
versions. Also, do *not* use Gradle STS; it is outdated.

### Importing to IDEA
You'll need to make sure that nullness annotations are working correctly. Also,
when sending pull requests, make sure not to change IDEA configuration files
that may have been stored in the repository.

### Releasing
```
./gradlew clean build
./gradlew <flavor>Release
```
Available flavors are github and spigot. Please do not abuse flavors by
compiling your own test builds as releases.

## Contributing
Please review our [contribution guidelines](https://github.com/SkriptLang/Skript/blob/master/.github/contributing.md).
In addition to that, if you are contributing Java code, check our
[coding conventions](https://github.com/SkriptLang/Skript/blob/master/code-conventions.md).

## Relevant Links
* [skUnity forums](https://forums.skunity.com)
* [Add-on releases at skUnity](https://forums.skunity.com/forums/addon-releases)
* [Skript Chat Discord invite](https://discord.gg/0lx4QhQvwelCZbEX)
* [Skript Hub](https://skripthub.net)
* [Original Skript at Bukkit](https://dev.bukkit.org/bukkit-plugins/skript) (inactive)

Note that these resources are not maintained by Skript's developers. Don't
contact us about any problems you might have with them.

## Developers
You can find all contributors [here](https://github.com/SkriptLang/Skript/graphs/contributors).

All code is owned by its writer, licensed for others under GPLv3 (see LICENSE)
unless otherwise specified.
