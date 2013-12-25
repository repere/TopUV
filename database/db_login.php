<?php
	require_once('database/db.php');
	require_once('model/User.php');
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Login check
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
	if (!empty($_POST)) 
	{
		$parameters = array
		(
			':login' => $_POST['login'],
		);
		//JSon response
		$reponse = array
		(
			'success' => 0
		);
	
	//DB Connection
	$config = require_once('config.php');
	$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
	//Acces Control
	$user = $db->find('User', 'user', 'login = :login', $parameters);
	if($user !== false)
	{
	
		//Retrieve salt,encrypted password and token from db
		$salt=$user->salt;
		$encrypted_password_from_db=$user->password;
		$token=$user->token;
		
		//Encode password from the user
		$password_encoded = base64_encode( sha1( $_POST['password'] . $salt, true) . $salt );
		
		//Compare hash password (one from DB and the other from user) between them
		if ($password_encoded === $encrypted_password_from_db) 
        {
			$reponse = array
			(
				'success' => 1,
				'token' => $token,
				'message'=> "Login successful !"
			);
		
		}	
		
		else 
		{
			$reponse = array
			(
				'success' => 0,
				'message'=> "Invalid Credentials !"
			);
		
		}
	 }
	 
	 echo json_encode($reponse);
		
	}  
	
	else 
	// In order to log manually user without android app, we purpose basic online browser login
	// Should be remove ?!
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

