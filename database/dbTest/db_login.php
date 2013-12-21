<?php

// Include this file to make a direct connection to the DB
require_once 'db_connect.php';

if (!empty($_POST)) 
{
    // basic select query, again with sql injection protection. We retrieve salt and encrypted to compare with no-encrypted password
	$query = "SELECT login, password, salt, token FROM " . $table_user . " WHERE login = :log";
    
    $query_params = array(
        ':log' => $_POST['login']
    );
    
    try 
    {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) 
    {
        $response["success"] = 0;
        $response["message"] = "Database Error. Please Try Again!";
        die(json_encode($response));      
    }
    
    $login_ok = false;
    
    $row = $stmt->fetch();
    if ($row) {
        
        //Retrieve salt and encrypted password from db
    	$salt = $row['salt'];
        $encrypted_password_from_db = $row['password'];
        
        //Retrieve token
        $token = $row['token'];
        
        //Encode password from the user by using technique from register.php
        $password_encoded = base64_encode( sha1( $_POST['password'] . $salt, true) . $salt );
    	
        //Compare hash password (one from DB and the other from user) between them
        if ($password_encoded === $encrypted_password_from_db) 
        {
            $login_ok = true;
        }
    }

    if ($login_ok) 
    {
    	$response["success"] = 1;
    	$response["token"] = $token;
        $response["message"] = "Login successful !";
        die(json_encode($response));
    } 
    
    else 
    {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials !";
        die(json_encode($response));
    }   
} 

// In order to log manually user without android app, we purpose basic online browser login
// Should be remove ?!
else 
{
	?>
	<h1>Login</h1> 
	<form action="db_login.php" method="post"> 
	    Login : <br/> 
	    <input type="text" name="login" placeholder="login" /> 
	    
	    <br/><br/> 
	    
	    Password  :<br/> 
	    <input type="password" name="password" placeholder="password" value="" /> 
	    
	    <br/><br/> 
	    
	    <input type="submit" value="Login" /> 
	</form> 
	
	<a href="db_register.php">Register</a>
	<?php
}

?> 