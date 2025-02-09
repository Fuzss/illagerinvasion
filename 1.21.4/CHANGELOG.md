# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v21.3.0-1.21.3] - 2025-01-01
- Port to Minecraft 1.21.3
### Changed
- Adjust imbuing table icons and descriptions to be more user-friendly
- Allow max imbuing levels to be configured individually per enchantment using a [data map](https://docs.neoforged.net/docs/resources/server/datamaps/) at `illagerinvasion:imbuing_levels`
- Allow invokers to spawn in high tier raids (can be disabled in the config)
- Allow some axe weapon enchantments on hatchets
- Refactor surrendered & projectiles code
- Marauders have a melee attack when their target comes too close
- Overhaul marauder throwing animation
- Rework alchemist ranged attacks
- Slightly update some entity animations
### Fixed
- Fix left-handed illagers holding a bow being incorrectly animated
- Include all illagers in the `minecraft:illagers` tag