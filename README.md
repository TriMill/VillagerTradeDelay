# VillagerTradeDelay

VillagerTradeDelay is a simple plugin to add a delay between when a villager is unemployed and when it can be employed again. It converts the villager to a nitwit for the delay period. The config file can be used to change the delay time or change the villager's label while it is unemploying.

## `config.yml`

The default `config.yml` is:
```yml
delay: 6000
custom_name:
  visible: true
  name: §aUnemploying...
```
`delay` sets the amount of time (in ticks) for the villager to spend unemploying. `custom_name` sets the nametag displayed on the villager's head while it is unemploying. If `custom_name.visible` is set to `false` the villager's name will not be changed. If it is set to `true`, the villager's name will be changed to `custom_name.name` and changed back after the villager unemploys.

## Commands

The following commands require operator status to use:
* `/villagertradedelay:reload` - reload the config file
* `/villagertradedelay:resetqueue` - finish unemploying all currently queued villagers

## Building

VillagerTradeDelay uses NMS, and so you must install the Spigot API with BuildTools before building it. Instructions can be found [here](https://www.spigotmc.org/wiki/buildtools/).
