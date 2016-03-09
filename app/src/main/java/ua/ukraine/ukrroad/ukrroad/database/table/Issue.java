package ua.ukraine.ukrroad.ukrroad.database.table;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "issues")
public class Issue {

    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "address")
    private String address;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "defect")
    private String defect;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "comment")
    private String comment;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "email")
    private String email;
    @DatabaseField(foreign = true)
    private Collection<Image> images;

    public void setImage(Collection<Image> images){
        this.images = images;
    }

    public Collection<Image> getImages(){
        return images;
    }

    public void addImage(Image image){
        images.add(image);
    }

    public Issue(){

    }
}
