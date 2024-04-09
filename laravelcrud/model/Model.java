package laravelcrud.model;

import java.io.IOException;
import java.util.List;

import laravelcrud.database.Column;
import laravelcrud.database.Table;
import laravelcrud.util.FileUtil;
import laravelcrud.util.WordFormatter;

public class Model {

    // Fields
    String modelName;
    Table table;

    // Getter and setter
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    // Constructor
    public Model(Table table) {
        setTable(table);
        setModelName(WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(table.getName())));
    }

    // Method
    public String getAllFields() {
        String fields = "";

        List<Column> columns = getTable().getAllColumnsWithoutPK();
        for (Column column : columns) {
            fields += "\n\t\t'" + column.getName() + "',";
        }

        return fields.substring(0, fields.length() - 1) + "\n\t";
    }

    public void generate() throws IOException {
        System.out.println("- Génération model pour l'entité : " + getTable().getName());
        String template = FileUtil.toStringInnerFile("/template/model.template");

        template = template.replace("#modelName#", getModelName());
        template = template.replace("#tableName#", getTable().getName());
        template = template.replace("#primaryKeyName#", getTable().getPrimaryKeyColumn().getName());
        template = template.replace("#fields#", getAllFields());

        FileUtil.createFileWithContent(template, "./app/Models", getModelName() + ".php");
    }
    
}
