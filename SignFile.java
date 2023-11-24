import org.json.JSONObject;
import org.json.JSONArray;

public class SignFile {
    // Class variables
    private String name;
    private String owner;
    private String contact;
    private String info;
    private int version = 1;

    // Class Constructors
    public SignFile(SignFile sf){
        setStack(sf.getStack());
    }

    public SignFile(String pName, String pOwner, String pContact, String pInfo){
        setName(pName);
        setOwner(pOwner);
        setContact(pContact);                                                                  
        setInfo(pInfo);
    }

    public SignFile(String[] data){
        setStack(data);
    }

    public SignFile(){
        setName("");
        setOwner("");
        setContact("");
        setInfo("");
    }

    // Set Methods
    public void setName(String pName){
        name = pName;
    }

    public void setOwner(String pOwner){
        owner = pOwner;
    }

    public void setContact(String pContact){
        contact = pContact;
    }

    public void setInfo(String pInfo){
        info = pInfo;
    }

    public void setStack(String[] pStack){
        name = pStack[0];
        owner = pStack[1];
        contact = pStack[2];
        info = pStack[3];
    }

    // Get Methods
    public String getName(){
        return name;
    }

    public String getOwner(){
        return owner;
    }

    public String getContact(){
        return contact;
    }

    public String getInfo(){
        return info;
    }

    public String[] getStack(){
        String[] retVal = {name, owner, contact, info};
        return retVal;
    }

    public int getVersion(){
        return version;
    }

    // JSON Type Shit
    public static JSONObject SignFileToJSON(SignFile sf){
        JSONObject json = new JSONObject();
        json.put("Data", sf.getStack());
        json.put("Version", sf.getVersion());
        return json;         
    }

    public static SignFile JSONToSignFile (JSONObject json){
        SignFile sf = new SignFile("", "", "", "");
        if (!json.has("Version")) {
            new DialogBox("USB Sign: Error", "An error occured while loading the file:\nThe file is not a valid .usbsf-File.");  
        } else {
            JSONArray ja = json.getJSONArray("Data");
            switch (json.getInt("Version")) {
                case  1: 
                    sf = new SignFile(ja.getString(0), ja.getString(1), ja.getString(2), ja.getString(3));
                    break;
                case  2: 
                    // TODO
                    break;
                default:
                    new DialogBox("USB Sign: Error", "Could not detect the sign file version.");
                    break;
            } // end of switch
        } // end of if-else

        return sf;  
    }
}