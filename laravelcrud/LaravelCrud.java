package laravelcrud;

import laravelcrud.database.DatabaseInformation;
import laravelcrud.database.Table;
import laravelcrud.util.FileUtil;

public class LaravelCrud {

    DatabaseInformation dbInfo;

    public DatabaseInformation getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(DatabaseInformation dbInfo) {
        this.dbInfo = dbInfo;
    }

    // Methods

    public void initDatabaseInformation() throws Exception {
        String configFile = FileUtil.toString("./.env");

        String username = FileUtil.getFirstLineWith(configFile, "DB_USERNAME").split("=")[1];
        String password = FileUtil.getFirstLineWith(configFile, "DB_PASSWORD").split("=")[1];
        String host = FileUtil.getFirstLineWith(configFile, "DB_HOST").split("=")[1];
        String database = FileUtil.getFirstLineWith(configFile, "DB_DATABASE").split("=")[1];
        String type = FileUtil.getFirstLineWith(configFile, "DB_CONNECTION").split("=")[1];

        setDbInfo(new DatabaseInformation(database, host, username, password, type));
        getDbInfo().fetchInformations();

        System.out.println("CONNECTION EFFECTUÉE !");
    }
    
    public static void main(String[] args) throws Exception { 
        LaravelCrud laravelCrud = new LaravelCrud();
        
        // get database information via le .env
        laravelCrud.initDatabaseInformation();

        for (Table table : laravelCrud.getDbInfo().getTables()) {
            System.out.println("-> " + table.getName());
        }
    }

    
}
