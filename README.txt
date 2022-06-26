FLAPPY DAVID 
by Jan Klinge, Justin Lippold

PREPERATION:
- Greenfoot Installation
- node.js and npm Installation
- Extract the archive

RUN SERVER:
- In the "server" directory, run "npm install" to install the dependencies
- To start the server, use "npm run start"
- By default, the server will listen on the port 80 for HTTP WebSocket requests

RUN CLIENT:
- Try to open the Scenario using Greenfoot v3.6.0
  OR compile and run the program using the configuration in "client/.idea/" 
  OR run the precompiled jar using the command in CMD "<Greenfoot Installation>\jdk\bin\java.exe --module-path="<Greenfoot Installation>\lib\javafx\lib" --add-modules=javafx.controls,javafx.fxml -jar FlappyDavid.jar"
- For the URI, use the server's endpoint (for local installations for example : "ws://localhost")
- Input a username that has a maximum length of 20 characters

CONTROLS:
- Arrow Keys up and down: Change Avatar
- Space Bar: Ready / Unready toggle
- Escape: Disconnect and Exit

- Space Bar: Jump
