package ua.ukraine.ukrroad.ukrroad.database.table;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "issues")
public class Issue {

    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField(canBeNull = false, columnName = "image")
    private String imagePath;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "address")
    private String address;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "defect")
    private String defect;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "comment")
    private String comment;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "email")
    private String email;

    public Issue(){

    }
}
