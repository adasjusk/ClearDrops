# ClearDrops plugin

A lightweight Spigot/Paper/Folia plugin that lets players clear dropped items around them within a configurable chunk radius, while protecting rare and valuable items from being removed.

## Features
- **Chunk-radius cleanup**  clears all dropped `Item` entities in a configurable radius around the player
- **Exclusion list**  rare items (netherite, diamond, shulker boxes, elytra, dragon egg, enchanted golden apples, enchanted books, totems, beacons, hearts of the sea, nether stars) are never removed
- **Two commands**  `/clean` and `/clearlag` both do the same thing, use whichever you prefer

## Commands
| Command     | Description                    | Permission       |
|-------------|--------------------------------|------------------|
| `/clean`    | Clear dropped items around you | `cleardrops.use` |
| `/clearlag` | Clear dropped items around you | `cleardrops.use` |


## Configuration
The `config.yml` file controls the cleanup command
To add or remove an item from the exclusion list, use its [Bukkit Material name](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html). Unknown names are logged with a warning on startup.

### Info
- Platforms: Spigot; Paper; Purpur; Folia
- MC Version: 26.x
- Made by human with 🧡
