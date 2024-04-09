package laravelcrud;

import java.util.Scanner;

import laravelcrud.database.DatabaseInformation;
import laravelcrud.database.Table;
import laravelcrud.model.Model;
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
        if (args.length < 2) {
            throw new Exception("Vous devez spécifier le nom de l' entité cible par exemple : -m student !");
        }
        if (!args[0].equals("-m")) {
            throw new Exception("Paramètre " + args[0] + " non reconnue !");
        }
        
        LaravelCrud laravelCrud = new LaravelCrud();
        
        // get database information via le .env
        laravelCrud.initDatabaseInformation();
        
        // get the user target entity name passed from the parameter ( -m student )
        String tableName = args[1];
        Table table = laravelCrud.getDbInfo().getTableWithName(tableName);

        // Generate the model
        Model model = new Model(table);
        model.generate();

    }

    
}
