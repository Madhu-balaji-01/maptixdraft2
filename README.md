# MapTix 

This is an Android app that provides shoppers with a time-efficient way to find the items they need in a supermarket setting. Currently, signages and directories are used 
for marking out the sections of a supermarket, but they are not always in sight and they can be broad in description at best. In addition, shopping lists, an indispensable 
constituent in the grocery shopper’s arsenal, are usually done on note-taking applications or written down by hand. 
This app intends to integrate both of these user experiences into one: a shopping list that saves what the shopper intends to purchase, and drawing from the items input,
an output map of the supermarket highlighting where each item can be found in the shortest path. This app uses Firebase as the supporting database, housing the items and 
their details as well as the user’s saved shopping lists, while Dijkstra’s algorithm is used to implement the shortest path.
