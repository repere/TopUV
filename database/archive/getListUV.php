<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner une liste des UVs d'une meme categorie
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	//Answer message
	$response = array
	(
			'succes' => 0		
	);
	
	// POST test
	if (!empty($_POST))
	{
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
		$UVs=$db->getListUv ($_POST['categorie']);
	
		foreach($UVs as $uv)
		{
			unset($uv->id);
			unset($uv->categorie);
		}
		
		//Answer message
		$response = array
		(
           'succes' => 1,
           'UVs' => $UVs
		);
		
		echo json_encode($response);
		
			
	}
	else 
	{
?>
		<h1>Afficher cat�gorie</h1> 
		<form action="getListUV.php" method="post"> 
		    Categorie : <br/> 
		    <input type="text" name="categorie" placeholder="categorie" /> 
		    
		    <br/><br/> 
		    
		    <input type="submit" value="Envoyer" /> 
		</form> 
<?php
	}
?>