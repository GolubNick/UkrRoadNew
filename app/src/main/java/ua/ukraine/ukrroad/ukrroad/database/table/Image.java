package ua.ukraine.ukrroad.ukrroad.database.table;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "images")
public class Image {

    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "image_path")
    private String imagePath;
    @DatabaseField(foreign = true)
    protected Issue issue;

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Image(){}

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }
}
