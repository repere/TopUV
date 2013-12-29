package fr.utt.topuv.constant;

public class WebServiceConstants
{

	public static final String ROOT = "http://79.92.123.65/android/";

    public static class CONNEXION
    {
        public static final String URL = ROOT + "db_login.php";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";
        public static final String SUCCESS = "success";
    }

    public static class UVS
    {
        public static final String URL = ROOT + "getListUV.php";
        
        //From DB
        public static final String CODE = "code";
        public static final String DESIGNATION = "designation";
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
    	public static final String MARK = "mark";
        public static final String COMMENT = "comment";
        public static final String TOKEN = "token";
        
    }
    
    public static class COMMENTS
    {
        public static final String URL = ROOT + "getListComment.php";
        
        //From DB
        public static final String LASTNAME = "lastname";
        public static final String SURNAME = "surname";
        public static final String MARK = "mark";
        public static final String COMMENT = "comment";
        public static final String DATE = "date";
        public static final String SUCCESS = "success";
        public static final String COMMENTS = "comments";
        
        //To DB
        public static final String CODE = "code";
        
    }

    public static class UV
    {
        public static final String URL = ROOT + "getUV.php";
        
        //From DB
        public static final String DESCRIPTION = "description";
        public static final String MARK = "mark";
        public static final String CREDIT = "credit";
        
        //To DB
        public static final String CODE = "code";
    }
}