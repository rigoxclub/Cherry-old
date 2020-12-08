package club.rigox.economy.database;

import com.mongodb.*;

import java.util.UUID;

public class MongoDB {
    private MongoClient client = new MongoClient();
    private DBCollection players;
    private DB database;

    public boolean connect (String ip, int port) {
        // Connect to the specified IP and PORT.
        client = new MongoClient(ip, port);

        // Get the database called "minecraft"
        database = client.getDB("minecraft");

        // Get the collection called "players" on the "minecraft"
        // database to store objects.
        players = database.getCollection("players");

        return true;
    }

    public void storePlayer (UUID uuid, int credits) {
        // Store first player on the PlayerJoin listener.
        DBObject object = new BasicDBObject("uuid", uuid);
        object.put("credits", credits);

        // Insert object to the players collection.
        players.insert(object);
    }
}
