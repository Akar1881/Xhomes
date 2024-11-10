# Xhomes - Your Personal Home Management Plugin for Minecraft

## Description

**Xhomes** is a powerful Minecraft plugin that allows players to easily set, manage, and teleport to personal homes. With an intuitive GUI and seamless functionality, Xhomes enhances the gameplay experience by simplifying home management.

## Key Features

- **Set Home**: Use `/sethome [homename]` to quickly set a home location and travel back to your favorite spots.

- **Home GUI**: Access a visually appealing GUI to view and select homes, featuring slots filled with blue beds for easy navigation.

- **Teleportation Countdown**: Experience a 5-second countdown with the message: "Teleporting in 5 seconds, don't move!" to build excitement.

- **Movement Cancellation**: Teleportation is canceled if a player moves during the countdown, ensuring safe arrival.

- **Delete Home**: Easily remove homes with the command `/delhome [homename]`.

- **Configurable Limits and Tiers**: Server owners can set maximum home limits based on permission tiers:
  - **Tier 1 (xhomes.hometier1)**: 2 homes
  - **Tier 2 (xhomes.hometier2)**: 4 homes
  - **Tier 3 (xhomes.hometier3)**: 6 homes
  - **Tier 4 (xhomes.hometier4)**: 8 homes

  These limits are configurable in the `config.yml` file.

- **Admin-Friendly Management**: Robust tools for server owners to manage home settings and permissions.

## Permissions

- `/sethome`: `xhomes.sethome`
- `/delhome`: `xhomes.delhome`
- `/home`: `xhomes.home`
- Tier 1: `xhomes.hometier1` (2 homes)
- Tier 2: `xhomes.hometier2` (4 homes)
- Tier 3: `xhomes.hometier3` (6 homes)
- Tier 4: `xhomes.hometier4` (8 homes)

## Compatibility

- **Minecraft Version**: Java Edition 1.19 - 1.21.3
- **Supported Platforms**: Bukkit, Paper, Spigot
- **Environment**: Server-side

## Installation

1. Download the latest version of Xhomes from [Modrinth](https://modrinth.com/plugin/xhomes).
2. Place the downloaded `.jar` file into the `plugins` folder of your Minecraft server.
3. Restart the server to enable the plugin.
4. Configure the plugin settings in the `config.yml` file located in the `Xhomes` folder.
5. 
## License

This project is licensed under the [GPL-3.0-only](https://opensource.org/licenses/GPL-3.0).

## Support

For issues, feature requests, or contributions, please visit the [issue tracker](https://github.com/Akar1881/Xhomes/issues) on Modrinth.

Elevate your Minecraft gameplay with **Xhomes**—your all-in-one home management solution!
