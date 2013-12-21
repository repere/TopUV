<<<<<<< HEAD
<?php
// Include this file to make a direct connection to the DB
require_once 'db_connect.php';

if (!empty($_POST)) {

	// Test if we receive not empty login and/or password
    if (empty($_POST['login']) || empty($_POST['password'])) 
    { 
        $response["success"] = 0;
        $response["message"] = "Please Enter Both a Username and Password.";
        
        die(json_encode($response));
    }
    
    //Query to define if this name of login already exist
    $query = " SELECT 1 FROM $table_user WHERE login = :log";
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
    
    // If we found a row, that means this login already exist
    $row = $stmt->fetch();
    if ($row) 
    {
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this login is already taken";
        die(json_encode($response));
    }
    
    //Encrypt and salt password
	$password = $_POST['password'];
	$salt = sha1(rand());
	$salt = substr($salt, 0, 10);
	$encrypted_password = base64_encode(sha1($password.$salt, true) . $salt);

	//Creation of a unique token
	$token = uniqid('', true);
    
	// insert into query with sql injection protection...
    $query = "INSERT INTO ". $table_user . " ( login, password, salt, token ) VALUES ( :log, :enc_pass, :sel, :tok ) ";
    
    $query_params = array(
        ':log' => $_POST['login'],
        ':enc_pass' => $encrypted_password,
    	':sel' => $salt,
    	':tok' => $token
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
    

    $response["success"] = 1;
    $response["message"] = "You have been successfully added to our database !";
    echo json_encode($response);
    
    
} 

// In order to add manually user without android app, we purpose basic online browser register
else 
{
	?>
	<h1>Register</h1> 
	<form action="db_register.php" method="post"> 
	    Login : <br/> 
	    <input type="text" name="login" value="" /> 
	    
	    <br/><br/>
	     
	    Password : <br/> 
	    <input type="password" name="password" value="" />
	     
	    <br/><br/> 
	    <input type="submit" value="Register" /> 
	</form>
	<?php
}

=======
<?php
// Include this file to make a direct connection to the DB
require_once 'db_connect.php';

if (!empty($_POST)) {

	// Test if we receive not empty login and/or password
    if (empty($_POST['login']) || empty($_POST['password'])) 
    { 
        $response["success"] = 0;
        $response["message"] = "Please Enter Both a Username and Password.";
        
        die(json_encode($response));
    }
    
    //Query to define if this name of login already exist
    $query = " SELECT 1 FROM $table_user WHERE login = :log";
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
    
    // If we found a row, that means this login already exist
    $row = $stmt->fetch();
    if ($row) 
    {
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this login is already taken";
        die(json_encode($response));
    }
    
    //Encrypt and salt password
	$password = $_POST['password'];
	$salt = sha1(rand());
	$salt = substr($salt, 0, 10);
	$encrypted_password = base64_encode(sha1($password.$salt, true) . $salt);

	//Creation of a unique token
	$token = uniqid('', true);
    
	// insert into query with sql injection protection...
    $query = "INSERT INTO ". $table_user . " ( login, password, salt, token ) VALUES ( :log, :enc_pass, :sel, :tok ) ";
    
    $query_params = array(
        ':log' => $_POST['login'],
        ':enc_pass' => $encrypted_password,
    	':sel' => $salt,
    	':tok' => $token
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
    

    $response["success"] = 1;
    $response["message"] = "You have been successfully added to our database !";
    echo json_encode($response);
    
    
} 

// In order to add manually user without android app, we purpose basic online browser register
else 
{
	?>
	<h1>Register</h1> 
	<form action="db_register.php" method="post"> 
	    Login : <br/> 
	    <input type="text" name="login" value="" /> 
	    
	    <br/><br/>
	     
	    Password : <br/> 
	    <input type="password" name="password" value="" />
	     
	    <br/><br/> 
	    <input type="submit" value="Register" /> 
	</form>
	<?php
}

>>>>>>> branch 'master' of https://github.com/repere/TopUV.git
?>
