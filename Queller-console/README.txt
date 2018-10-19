A Java console program for the Queller bot by Quitch
Author: Pedro "Plimapeli" Lima (Fubanga on BGG)
Thanks to Guilherme Toledo for the excellent feedback and Quitch for Queller. 

Queller file page:
https://www.boardgamegeek.com/filepage/141333/queller-bot-war-ring-solo-play

To run the .JAR file, extract it to a folder in your computer, get to that
folder via console (cmd in Windows) and run the command:
java -jar queller.jar

Observations:
1. Only base game is available. No expansion yet.
2. Inputs are case insensitive
3. An UPPERCASE text indicates a Queller term, which you can find on page 2 of Queller's PDF.
4. An action is indicated by arrowheads ">>> ACTION <<<"
5. Give me feedback on the topic on BGG, BGG mail (Fubanga) or
my email: ppslima@gmail.com with the subject "Queller".

Have fun!
---------
Changelog

v1.4:
- Fixed a bug that tried to get a file and didn't find it

v1.3:
- Fixed missing text for a certain action
- Fixed typos
- Changed Nazgûl placement priority for the special case of "Nazgûl Search" card.
- Changed text of Character Chart from "Die has been used?" to "Were you able to move any Minion or Nazgûl?"?

v1.2:
- Added changelog to record the changes history
- Fixed typos
- Fixed Discarded dice not being reset after turn end
- Adjusted Saved and Discarded dice to not show up (it was on only for purposes of testing)
- Queller's terms are in UPPERCASE now
- Actions are indicated by greater than and less than symbols >>> ACTION <<< now

v1.1:
- Fixed some bug that was consuming dice very wrongly
- Fixed input validation for the Queller roll
- Program is now case insensitive for inputs
- Added Creative commons license

v1.0:
- First release
