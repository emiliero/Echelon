# Echelon
<!-- div align="center">
//TODO: Legge inn test coverage, Kotlin versjon, Discord4J versjon og antall forks/stjerner?
  <p>
    <a href="https://discord.gg/bRCvFy9"><img src="https://discordapp.com/api/guilds/222078108977594368/embed.png" alt="Discord server" /></a>
    <a href="https://www.npmjs.com/package/discord.js"><img src="https://img.shields.io/npm/dt/discord.js.svg?maxAge=3600" alt="NPM downloads" /></a>
  </p>
</div -->

A Discord bot written in Kotlin which handles queues for rehearsals in the Programming 2 at Østfold University College amidst of the Covid-19 pandemic. 

## Features

__Everyone__

The bot enables people to join and leave a queue. The ones in the queue can receive help by designated helpers (here: student assistants). You can print the queue and check your position whenever you want. 

__Student Assistants__

A student assistant is able to help the first in line and clear the queue of people. 

## Commands

Commands are available for different roles. 

__Everyone__

`!help` - Display commands

`!join` - Join the queue to get help from a student assistant

`!leave` - Leave the queue. Use this if you don't need help anymore

`!print` - Print the queue

`!position` - Check your position in the queue

__Student assistants__

`!next` - Get the next student in queue

`!clear` - Clear the entire list

`!report` - Prints a report of rehearsal data for the given day

## Future features

Features intended to be implemented:

* Calculation of average queue- and help-time
