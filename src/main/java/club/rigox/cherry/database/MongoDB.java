package club.rigox.cherry.database;

import club.rigox.cherry.Cherry;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private Cherry cherry;
    private MongoClientURI uri;
    private MongoClient client;
    private MongoDatabase database;

    public MongoDB (Cherry plugin) {
        this.cherry = plugin;
    }

    public String databaseConfig(String str) {
        return cherry.getDatabase().getString("mongodb." + str);
    }

    public boolean connect(){
        uri = new MongoClientURI(String.format("mongodb+srv://%s:%s@%s/admin?retryWrites=true&w=majority",
                databaseConfig("user"),
                databaseConfig("password"),
                databaseConfig("host")));

        client = new MongoClient(uri);
        database = client.getDatabase(databaseConfig("database"));
        return true;
    }
}
