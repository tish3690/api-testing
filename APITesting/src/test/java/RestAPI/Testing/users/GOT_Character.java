package RestAPI.Testing.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GOT_Character {
    
	@JsonProperty("_id")
    String id;
	
	
    String house;
    String name;
    Boolean male;
    
    
    public GOT_Character() {
        
    }
    
    @Override
    public String toString() {
        return "GOT_Character [id=" + id + ", house=" + house + ", name=" + name + ", male=" + male + "]";
    }
    public GOT_Character(String id, String house, String name, Boolean male) {
        super();
        this.id = id;
        this.house = house;
        this.name = name;
        this.male = male;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHouse() {
        return house;
    }
    public void setHouse(String house) {
        this.house = house;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getMale() {
        return male;
    }
    public void setMale(Boolean male) {
        this.male = male;
    }
    
    

}