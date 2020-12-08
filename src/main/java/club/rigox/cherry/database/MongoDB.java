package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.*;

import java.util.UUID;

import static club.rigox.cherry.utils.Console.info;
import static club.rigox.cherry.utils.Console.warn;

public class MongoDB {
    private final Cherry cherry;
    private DBCollection player;
    MongoClient client;
    private int credits;

    public MongoDB (Cherry plugin) {
        this.cherry = plugin;
    }

    public String databaseConfig(String str) {
        return cherry.getDatabase().getString("mongodb." + str);
    }

    public boolean connect(){
        MongoClientURI uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                databaseConfig("user"),
                databaseConfig("password"),
                databaseConfig("host")));

        client = new MongoClient(uri);
        DB database = client.getDB(databaseConfig("database"));
        player = database.getCollection("players");
        return true;
    }

    public void storePlayer (UUID uuid, int credits) {
        DBObject object = new BasicDBObject("UUID", uuid.toString());
        object.put("credits", credits);
        player.insert(object);
    }

    public void readPlayer (UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid);
        DBObject found = player.findOne(r);

        if (found == null) {
            warn("Player has been added to the database while being checked.");
            storePlayer(uuid, 100);
            return;
        }
        credits = (int) found.get("credits");
    }

    public void close() {
        client.close();
        info("MongoDB connection closed!");
    }
}
