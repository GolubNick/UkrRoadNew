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

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "defect")
    private String defect;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "comment")
    private String comment;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public Issue(){
        images = new ArrayList<>();
        address = "";
        defect = "";
        comment = "";
        email = "";
    }
}
