# ClearDrops

A lightweight Bukkit/Paper plugin that lets players clear dropped items around them within a configurable chunk radius, while protecting rare and valuable items from being removed.

## Compatibility
- **Spigot** - full support
- **Paper** - full support
- **Folia** - full support

## Features
- **Chunk-radius cleanup**  clears all dropped `Item` entities in a configurable radius around the player
- **Exclusion list**  rare items (netherite, diamond, shulker boxes, elytra, dragon egg, enchanted golden apples, enchanted books, totems, beacons, hearts of the sea, nether stars) are never removed
- **Folia-aware**  automatically detects Folia and schedules region-based execution instead of synchronous chunk access
- **Two commands**  `/clean` and `/clearlag` both do the same thing, use whichever you prefer

## Commands
| Command     | Description                    | Permission       |
|-------------|--------------------------------|------------------|
| `/clean`    | Clear dropped items around you | `cleardrops.use` |
| `/clearlag` | Clear dropped items around you | `cleardrops.use` |

## Installation
1. Download the `ClearDrops.jar` file.
2. Place the jar in the `plugins` folder of your server.
3. Restart the server to load the plugin.
4. Edit the generated `config.yml` to your liking.

## Configuration
The `config.yml` file controls the cleanup command
To add or remove an item from the exclusion list, use its [Bukkit Material name](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html). Unknown names are logged with a warning on startup.