<?php
	require_once('database/db.php');
	require_once('model/UV.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner une liste des UVs d'une meme categorie
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	if($_POST['tag'] === "sesameOuvreToi!")
	{
		//Answer message
		$response = array
		(
				'succes' => 0		
		);
		
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
		$UVs=$db->search('UV','uv','1=1');
			
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
		print "Ouai ?! On peut savoir ce que tu fais là ?";
	}
?>