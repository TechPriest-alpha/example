Basic TCP-based chat application and testing client.<br/>
Current implementation works via permanent TCP-connection between client and server.
Two client implementations provided: console-based for manual chatting and automated for stress and load testing.
Trivial localization and message storage implementations provided.


In order to start server run<br/> 
java -jar basic-chat-server/target/basic-chat-server-1.0-SNAPSHOT-uber.jar<br/>
Server supports additional options: '-Dserver.port' and '-Dserver.host'.

User clients are started with<br/>
java -jar user-chat-client/target/user-chat-client-1.0-SNAPSHOT-uber.jar<br/>
Client supports additional options: '-Dserver.port' and '-Dserver.host'.

Automated clients are started with<br/>
java -jar automated-chat-client/target/automated-chat-client-1.0-SNAPSHOT-uber.jar <br/>
Automated client supports additional options:
'-Dclient.instances' for number of concurrent clients, '-Dserver.port' and '-Dserver.host'.<br/>
Once connected, automated client authenticates under random alphanumeric name and starts 
sending random chat messages every 100-200 ms.


TODO:
1. Reconnects in cases of failure.
2. Error handling.
3. Various optimizations.
4. Enhanced support for different data encoding options with clients (text, json, other).
5. Authorization.
6. Enhanced command list.