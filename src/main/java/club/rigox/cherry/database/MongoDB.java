package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

import java.util.UUID;

import static club.rigox.cherry.utils.ConsoleUtils.debug;
import static club.rigox.cherry.utils.ConsoleUtils.warn;
import static club.rigox.cherry.utils.ConsoleUtils.info;

public class MongoDB {
    private final Cherry cherry;
    private DBCollection playerCollection;
    private MongoClient client;


    public MongoDB (Cherry plugin) {
        this.cherry = plugin;
    }

    public String databaseConfig(String str) {
        return cherry.getDatabase().getString("mongodb." + str);
    }

    public void connect(){
        MongoClientURI uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                databaseConfig("user"),
                databaseConfig("password"),
                databaseConfig("host")));

        client = new MongoClient(uri);
        DB database = client.getDB(databaseConfig("database"));
        playerCollection = database.getCollection("players");
    }

    public void storePlayer (UUID uuid, double credits) {
        DBObject object = new BasicDBObject("UUID", uuid.toString());
        object.put("credits", credits);
        playerCollection.insert(object);
    }

    public double getPlayerCredits (UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            warn("Player has been added to the database while being checked.");
            storePlayer(uuid, 100);
            return 0;
        }
        double credits = (double) found.get("credits");
        debug(String.format("uuid %s has %s credits", uuid, credits));
        return credits;
    }

    public void updatePlayerCredits(UUID uuid, double credits) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = playerCollection.findOne(r);

        if (found == null) {
            warn("Player has been added to the database while being checked.");
            storePlayer(uuid, 100);
            return;
        }

        BasicDBObject set = new BasicDBObject("$set", r);
        set.append("$set", new BasicDBObject("credits", credits));
        playerCollection.update(found, set);
    }

    public void close() {
        client.close();
        info("MongoDB connection closed!");
    }
}
