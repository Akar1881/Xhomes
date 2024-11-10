# Xhomes - Your Personal Home Management Plugin for Minecraft

## Description

**Xhomes** is a feature-rich Minecraft plugin that allows players to effortlessly set, manage, and teleport to their personal homes. With intuitive commands and a user-friendly interface, Xhomes enhances the gameplay experience by simplifying home management.

## Key Features

- **Set Home**: Quickly set a home location using `/sethome [homename]` and return to your favorite spots with ease.

- **Home GUI**: Access a visually appealing GUI that allows you to view and select homes, with slots marked by blue beds for easy navigation.

- **Teleportation Countdown**: Enjoy a 5-second countdown before teleportation, accompanied by the message: "Teleporting in 5 seconds, don't move!" to build anticipation.

- **Movement Cancellation**: Teleportation is automatically canceled if the player moves during the countdown, ensuring a safe arrival.

- **Delete Home**: Easily remove homes with the command `/delhome [homename]`.

- **Configurable Limits and Tiers**: Server owners can set maximum home limits based on permission tiers:
  - **Tier 1 (xhomes.hometier1)**: 2 homes
  - **Tier 2 (xhomes.hometier2)**: 4 homes
  - **Tier 3 (xhomes.hometier3)**: 6 homes
  - **Tier 4 (xhomes.hometier4)**: 8 homes

  These limits can be configured in the `config.yml` file.

- **Admin-Friendly Management**: Includes robust tools for server owners to manage home settings and permissions efficiently.

## Permissions

- `/sethome`: `xhomes.sethome`
- `/delhome`: `xhomes.delhome`
- `/home`: `xhomes.home`
- Tier 1: `xhomes.hometier1` (2 homes)
- Tier 2: `xhomes.hometier2` (4 homes)
- Tier 3: `xhomes.hometier3` (6 homes)
- Tier 4: `xhomes.hometier4` (8 homes)

## Compatibility

- **Minecraft Versions**: Java Edition 1.18 - 1.21 and newer
- **Supported Platforms**: Bukkit, Paper, Spigot
- **Environment**: Server-side

## Installation

1. Download the latest version of Xhomes from [Modrinth](https://modrinth.com/plugin/xhomes) || [SpigotMC](https://www.spigotmc.org/resources/xhomes.120062/).
2. Place the downloaded `.jar` file into the `plugins` folder of your Minecraft server.
3. Restart the server to enable the plugin.
4. Configure the plugin settings in the `config.yml` file located in the `Xhomes` folder.

## License

This project is licensed under the [GPL-3.0-only](https://opensource.org/licenses/GPL-3.0).

## Support

For issues, feature requests, or contributions, please visit the [issue tracker](https://github.com/Akar1881/Xhomes/issues) on GitHub.

Elevate your Minecraft gameplay with **Xhomes**—your all-in-one home management solution!
