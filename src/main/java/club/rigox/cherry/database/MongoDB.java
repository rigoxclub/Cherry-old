package club.rigox.cherry.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private MongoClientURI uri;
    private MongoClient client;
    private MongoDatabase database;

    public boolean connect(){
        uri = new MongoClientURI("mongodb+srv://rigox:rigoxAdmin@rigoxclub.7rnl2.mongodb.net/admin?retryWrites=true&w=majority");
        client = new MongoClient(uri);
        database = client.getDatabase("minecraft");
        return true;
    }
}
