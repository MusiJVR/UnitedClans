# UnitedClans
**UnitedClans** is a plugin for Minecraft servers based on Paper, Spigot and Bukkit cores.

**UnitedClans** adds a clan system with economic elements to the server!

## Commands
Here are all the commands that can be used in the plugin:
* `/createclan <clan name> <color>` - This command allows you to create a clan
* `/deleteclan <clan name>` - This command allows you to delete a clan
* `/inviteclan <player>` - This command allows you to invite a player to a clan
* `/acceptclan` - This command allows you to accept an invitation to a clan
* `/kickclan <player>` - This command allows you to kick a player from the clan
* `/leaveclan <clan name>` - This command allows the player to leave the clan
* `/setroleclan <player> <role>` - This command allows you to set the role of the player
* `/menuclan` - This command allows you to open the clan menu
* `/chatclan <message>` - This command allows you to send messages to the clan chat
* `/changeleaderclan <clan name> <player>` - This command allows you to change the clan Leader
* `/bankdepositclan <number>` - This command allows you to deposit currency into the clan's bank account
* `/bankwithdrawclan <number>` - This command allows you to withdraw currency from a clan bank account
* `/topclans <top name>` - This command allows you to open the top clans
* `/letterclan <letter (not necessary)>` - This command allows you to create and view a letter to the clan
* `/helpclan` - This command allows you to display all possible plugin commands

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
* `roles` - These parameters are responsible for setting up roles in the plugin, **I don't recommend touching them**

## Dependencies
In the **UnitedClans** plugin it is possible to use external placeholders `PlaceholderAPI`, which allow you to get clan names, player roles and various clan points
In order to use placeholders, the latest version of [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) must be installed on the server

All possible placeholders are presented here:
* `%unitedclans_clanName%`
* `%unitedclans_clanCountMembers%`
* `%unitedclans_clanKills%`
* `%unitedclans_clanBank%`
* `%unitedclans_playerRole%`
* `%unitedclans_playerKills%`
* `%unitedclans_playerDonations%`

## Issues
Please leave messages about any errors you find [here](https://github.com/MusiJVR/UnitedClans/issues) or on the [Discord](https://discord.gg/xY8WJt7VGr)

## Social Media

- Page on [Modrinth](https://modrinth.com/plugin/unitedclans)
- Page on [GitHub](https://github.com/MusiJVR/UnitedClans)
- Page on [Discord](https://discord.gg/xY8WJt7VGr)