<?php
// Include this file to make a direct connection to the DB
require_once 'database/db.php';
require_once('model/Note.php');
require_once('model/UV.php');
require_once('model/User.php');
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//New User Registering 
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

if (!empty($_POST)) 
{
// Test if we receive not empty input fields
    if (empty($_POST['login']) || empty($_POST['password'])|| empty($_POST['firstname'])
	|| empty($_POST['lastname']) || empty($_POST['email'])) 
    { 
        $response["success"] = 0;
        $response["message"] = "Please Enter All Fields !";
        
        die(json_encode($response));
    }
    
	$parameters = array
(
	':login' => $_POST['login'],
);
//DB Connection
	$config = require_once('config.php');
	$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
//Verify if this name of login already exist
	$user = $db->find('User', 'user', 'login = :login', $parameters);
						     
// If this login already exist
	if($user !== false)
	{
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this login is already taken";
        die(json_encode($response));
    }
 // If it doesn' exist   
    //Encrypt and salt password
	$password = $_POST['password'];
	$salt = sha1(rand());
	$salt = substr($salt, 0, 10);
	$encrypted_password = base64_encode(sha1($password.$salt, true) . $salt);

	//Creation of a unique token
    	$token = uniqid('',true);
		
	//Creation of object to insert	
 	    $new_user = new User();
		$new_user->first_name=$_POST['firstname'];
		$new_user->last_name=$_POST['lastname'];
		$new_user->email=$_POST['email'];
		$new_user->login =$_POST['login'];
		$new_user->password=$encrypted_password;
		$new_user->salt=$salt;
		$new_user->token=$token ;

		
	//The new user insertion (Registering)
		$id = $db->insert($new_user,'user');
		if($id !== false)
		{
				$response["success"] = 1;
				$response["message"] = "You have been successfully added to our database !";
				echo json_encode($response);
			
		}
   
} else 
// In order to add manually user without android app, we purpose basic online browser register
{
	?>
	<h1>Register</h1> 
	
	<form action="db_register.php" method="post"> 
	    First Name : <br/> 
	    <input type="text" name="firstname" value="" /> 
	    
	    <br/><br/>
		Last Name : <br/> 
	    <input type="text" name="lastname" value="" /> 
	    
	    <br/><br/>
		E-mail : <br/> 
	    <input type="text" name="email" value="" /> 
	    
	    <br/><br/>
		Login: <br/> 
	    <input type="text" name="login" value="" /> 
	    
	    <br/><br/>
	     
	    Password : <br/> 
	    <input type="password" name="password" value="" />
	     
	    <br/><br/> 
	    <input type="submit" value="Register" /> 
	</form>
	<?php
}

?>