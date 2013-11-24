BungeeQuery
===========

TCP based query for Bungee servers

How to use:

1. Create a TCP socket to (host, 25566)
2. Send a single command per connection (will do multi-per-connection at some point later)

Currently implemented commands:

PLAYERS - returns a json object containing your servers and players
