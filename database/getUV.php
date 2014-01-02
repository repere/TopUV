<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner l'UV (appel de la fonction getUv)
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
	
		// get the UV by code
		$UVs=$db->getUv ($_POST['code']);
		
		foreach($UVs as $uv)
		{
			unset($uv->id);
			unset($uv->code);
			unset($uv->designation);
			unset($uv->categorie);
		}
		
		// answer message 
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
		<h1>Afficher UV</h1> 
		<form action="getUV.php" method="post"> 
		    Code : <br/> 
		    <input type="text" name="code" placeholder="code" /> 
		    
		    <br/><br/> 
		    
		    <input type="submit" value="Envoyer" /> 
		</form> 
<?php
	}
?>
