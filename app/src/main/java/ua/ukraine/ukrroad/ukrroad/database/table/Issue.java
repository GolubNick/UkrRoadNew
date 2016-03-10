package ua.ukraine.ukrroad.ukrroad.database.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "issues")
public class Issue {

    public final static String ID = "id";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "address")
    private String address;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "defect")
    private String defect;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "comment")
    private String comment;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "email")
    private String email;
    @ForeignCollectionField(eager = true)
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

    public int getId(){
        return id;
    }

    public Issue(){
        images = new ArrayList<>();
        address = "";
        defect = "";
        comment = "";
        email = "";
    }
}
