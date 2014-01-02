<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	require_once('model/Note.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner les notes et les commentaires sur une UV (appel de la fonction getListComment)
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

// answer message 
		$response = array
		(
           'succes' => 0,
         
		);
	
	if (!empty($_POST))
	{
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	  
        // Marks,Comments and authors names, about UV
		$Comments=$db->getListComment($_POST['code']);
		
		// answer message 
		$response = array
		(
           'succes' => 1,
           'Comments' => $Comments
		);
		
				echo json_encode($response);
			
	}
	else 
	{
?>
		<h1>Afficher les Commentaires</h1> 
		<form action="getListComment.php" method="post"> 
		    Code : <br/> 
		    <input type="text" name="code" placeholder="code" /> 
		    
		    <br/><br/> 
		    
		    <input type="submit" value="Envoyer" /> 
		</form> 
<?php
	}
?>
