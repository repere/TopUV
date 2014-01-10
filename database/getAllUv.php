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
		
?>