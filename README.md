# Ticket Management System

## How to get the server and client running?

### Generating classes via protobuf plugin

1. Within the _idistrsys_ folder start a CMD.
2. Build the project via _gradle clean build --console 'verbose'_: the verbose option enables printing of all executed tasks.
3. Refresh your gradle project within your IDE (e.g. Eclipse)
4. Implement client and server interfaces

### Starting the server

1. Within the _idistrsys_ folder start a CMD.
2. Run the server via **java -jar server\build\libs\server-1.0.jar**

The _shared_ project is automatically build before the server is started.
Look at the dependencies block in the server/build.gradle.

### Starting the client

1. Within the _idistrsys_ folder start a CMD.
2. Run the server via **gradle client:run**

The _shared_ project is automatically included in the dependencies of the client and server.
If you are interested inspect the `idistrsys/build.gradle` to see all the relevant settings, configurations and tasks.
