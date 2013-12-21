<<<<<<< HEAD
<?php  
	// Define variables for DB access
	$host = "localhost"; 
	$dbname = "android_database";     
	$username = "root"; 
    $password = ""; //NEED TO CHANGE IT !
    
    // Define name of table (avoid big change if table name change)
    $table_user = "user";
    $table_comment = "comment";
    $table_uv = "uv";
    
    // Set UTF8 format
    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'); 

    // Connection
    try 
    { 
        $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8", $username, $password, $options); 
    } 
    
    catch(PDOException $ex) 
    { 
        die("Failed to connect to the database. Sorry hackers that is all I can say :s "); 
    }
    
    // Set paramaters to accept exception from pdo
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
?>
=======
<?php  
	// Define variables for DB access
	$host = "localhost"; 
	$dbname = "android_database";     
	$username = "root"; 
    $password = ""; //NEED TO CHANGE IT !
    
    // Define name of table (avoid big change if table name change) 
    $table_user = "user";
    $table_comment = "comment";
    $table_uv = "uv";
    
    // Set UTF8 format
    $options = array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'); 

    // Connection
    try 
    { 
        $db = new PDO("mysql:host={$host};dbname={$dbname};charset=utf8", $username, $password, $options); 
    } 
    
    catch(PDOException $ex) 
    { 
        die("Failed to connect to the database. Sorry hackers that is all I can say :s "); 
    }
    
    // Set paramaters to accept exception from pdo
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
?>
>>>>>>> branch 'master' of https://github.com/repere/TopUV.git
