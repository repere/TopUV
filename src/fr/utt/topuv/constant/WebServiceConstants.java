package fr.utt.topuv.constant;

public class WebServiceConstants
{

	public static final String ROOT = "http://geminantoine.myqnapcloud.com/android/";

    public static class CONNEXION
    {
        public static final String URL = ROOT + "db_login.php";
        
        //To DB
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        
        //From DB
        public static final String ID_USER = "id";
        public static final String SUCCESS = "success";
    }

    public static class UVS
    {
        public static final String URL = ROOT + "getListUV.php";
        
        //From DB
        public static final String CODE = "code";
        public static final String DESIGNATION = "designation";
        public static final String CREDIT = "credit";
        public static final String DESCRIPTION = "description";
        public static final String NOTE = "note";
        public static final String UVS = "UVs";
        
        //To DB
        public static final String CATEGORIE = "categorie";
    }
    
    public static class COMMENT
    {
    	public static final String URL = ROOT + "putComment.php";
    	
    	//From DB
    	public static final String SUCCESS = "success";
    	
    	//To DB
    	public static final String ID_USER = "id";
        public static final String CODE = "code";
        public static final String NOTE = "mark";
        public static final String COMMENT = "comment";   
    }
    
    public static class COMMENTS
    {
        public static final String URL = ROOT + "getListComment.php";
        
        //From DB
        public static final String LASTNAME = "last_name";
        public static final String FIRSTNAME = "first_name";
        public static final String NOTE = "mark";
        public static final String COMMENT = "comment";
        public static final String DATE = "date";
        public static final String COMMENTS = "Comments";
        
        //To DB
        public static final String CODE = "code";
        
    }
}