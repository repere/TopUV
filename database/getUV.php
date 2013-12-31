<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	//retourner l'UV (appel de la fonction getUv)
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	if (!empty($_POST))
	{
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
		$UVs=$db->getUv ($_POST['code']);
		$json = array
		(
           'error' => false,
           'UVs' => $UVs
		);
		// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
		echo json_encode($json);
			
	}
	else 
	{
?>
		<h1>Afficher catégorie</h1> 
		<form action="getListUV.php" method="post"> 
		    Categorie : <br/> 
		    <input type="text" name="code" placeholder="code" /> 
		    
		    <br/><br/> 
		    
		    <input type="submit" value="Envoyer" /> 
		</form> 
<?php
	}
?>
