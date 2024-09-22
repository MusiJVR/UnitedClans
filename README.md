# UnitedClans
**UnitedClans** is a plugin for Minecraft servers based on Paper, Spigot and Bukkit cores.

**UnitedClans** adds a clan system with economic elements to the server!

All technical settings and features presented here are for the latest version of the plugin and will not always work on older versions.

## Commands
Here are all the commands that can be used in the plugin:
* `/uchelp` - This command allows you to display all possible plugin commands
* `/ucreloadconfig` - This command allows you to reload the plugin config
* `/ucinfo <clan name (not necessary)>` - This command allows you to view information about the clan
* `/uccreate <clan name> <color>` - This command allows you to create a clan
* `/ucdelete <clan name>` - This command allows you to delete a clan
* `/ucinvite <player>` - This command allows you to invite a player to a clan
* `/ucaccept` - This command allows you to accept an invitation to a clan
* `/uckick <player>` - This command allows you to kick a player from the clan
* `/ucleave <clan name>` - This command allows the player to leave the clan
* `/ucsetrole <player> <role>` - This command allows you to set the role of the player
* `/ucmenu` - This command allows you to open the clan menu
* `/ucchat <message>` - This command allows you to send messages to the clan chat
* `/ucchangeleader <clan name> <player>` - This command allows you to change the clan Leader
* `/ucbankdeposit <number>` - This command allows you to deposit currency into the clan's bank account
* `/ucbankwithdraw <number>` - This command allows you to withdraw currency from a clan bank account
* `/uctop <top name>` - This command allows you to open the top clans
* `/ucletter <letter (not necessary)>` - This command allows you to create and view a letter to the clan

## Config
When the server starts, the config file will be automatically created in this path: `plugins/UnitedClans/config.yml`

```yml
lang: en_en
clan-msg-pattern: '%clan% [%sender%]:%message%'
clan-max-player: '25'
server-currency: DIAMOND
clan-creation-price: '32'
roles:
  leader: Leader
  elder: Elder
  member: Member
  no-clan: NoClan
```

* `lang` - This parameter is responsible for the localization of the plugin (`en_en` or `ru_ru`)
* `clan-msg-pattern` - This parameter is responsible for the clan message pattern
* `clan-max-player` - This parameter is responsible for the maximum number of players in the clan (the value must not be less than `1` and greater than `100`)
* `server-currency` - This parameter is responsible for the server currency that the plugin will use as the main one
* `clan-creation-price` - This parameter is responsible for the cost of creating a clan, in the currency specified in the `server-currency` parameter (the value must not be less than `0` and greater than `64`)
* `roles` - These parameters are responsible for setting up roles in the plugin, *I don't recommend touching them*

## Permissions
The plugin has permissions:

| **Permissions**                  | **Meaning**                                |
|----------------------------------|--------------------------------------------|
| `unitedclans.uchelp`             | Permission to use command `uchelp`         |
| `unitedclans.ucreloadconfig`     | Permission to use command `ucreloadconfig` |
| `unitedclans.ucinfo`             | Permission to use command `ucinfo`         |
| `unitedclans.uccreate`           | Permission to use command `uccreate`       |
| `unitedclans.ucdelete`           | Permission to use command `ucdelete`       |
| `unitedclans.ucinvite`           | Permission to use command `ucinvite`       |
| `unitedclans.ucaccept`           | Permission to use command `ucaccept`       |
| `unitedclans.uckick`             | Permission to use command `uckick`         |
| `unitedclans.ucleave`            | Permission to use command `ucleave`        |
| `unitedclans.ucsetrole`          | Permission to use command `ucsetrole`      |
| `unitedclans.ucmenu`             | Permission to use command `ucmenu`         |
| `unitedclans.ucchat`             | Permission to use command `ucchat`         |
| `unitedclans.ucchangeleader`     | Permission to use command `ucchangeleader` |
| `unitedclans.ucbankdeposit`      | Permission to use command `ucbankdeposit`  |
| `unitedclans.ucbankwithdraw`     | Permission to use command `ucbankwithdraw` |
| `unitedclans.uctop`              | Permission to use command `uctop`          |
| `unitedclans.ucletter`           | Permission to use command `ucletter`       |

## Dependencies
In the **UnitedClans** plugin it is possible to use external placeholders `PlaceholderAPI`, which allow you to get clan names, player roles and various clan points.
In order to use placeholders, the latest version of [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) must be installed on the server.

This table presents all possible placeholders:

|         **Placeholders**         |     **Meaning**     |
|----------------------------------|---------------------|
| `%unitedclans_clanName%`         | Clan name           |
| `%unitedclans_clanCountMembers%` | Clan count members  |
| `%unitedclans_clanKills%`        | Clan count kills    |
| `%unitedclans_clanBank%`         | Clan bank           |
| `%unitedclans_playerRole%`       | Player clan role    |
| `%unitedclans_playerKills%`      | Player count kills  |
| `%unitedclans_playerDonations%`  | Player donations    |

## Issues
Please leave messages about any errors you find [here](https://github.com/MusiJVR/UnitedClans/issues) or on the [Discord](https://discord.gg/xY8WJt7VGr)

## Social Media

- Page on [Modrinth](https://modrinth.com/plugin/unitedclans)
- Page on [GitHub](https://github.com/MusiJVR/UnitedClans)
- Page on [Discord](https://discord.gg/xY8WJt7VGr)