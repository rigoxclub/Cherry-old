package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.*;
import org.bukkit.entity.Player;

import java.util.UUID;

import static club.rigox.cherry.utils.Console.*;

public class MongoDB {
    private final Cherry cherry;
    private DBCollection playerCollection;
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
        playerCollection = database.getCollection("players");
        return true;
    }

    public void storePlayer (UUID uuid, int credits) {
        DBObject object = new BasicDBObject("UUID", uuid.toString());
        object.put("credits", credits);
        playerCollection.insert(object);
    }

    public int getPlayerCredits (UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            warn("Player has been added to the database while being checked.");
            storePlayer(uuid, 100);
            return 0;
        }
        credits = (int) found.get("credits");
        debug(String.format("uuid %s has %s credits", uuid, credits));
        return credits;
    }

    public void close() {
        client.close();
        info("MongoDB connection closed!");
    }
}
