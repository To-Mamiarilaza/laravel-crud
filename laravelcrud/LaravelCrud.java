package laravelcrud;

import laravelcrud.database.DatabaseInformation;

public class LaravelCrud {

    DatabaseInformation dbInfo;

    public DatabaseInformation getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(DatabaseInformation dbInfo) {
        this.dbInfo = dbInfo;
    }
    
    public static void main(String[] args) { 
        LaravelCrud laravelCrud = new LaravelCrud();
        
        // get database information via le .env
        
    }

    
}
