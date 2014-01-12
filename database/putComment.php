<?php
	require_once('database/db.php');
	require_once('model/Note.php');
	require_once('model/UV.php');
	require_once('model/User.php');

	if ($_POST["tag"] === "sesameOuvreToi!") 
	{
		// Json Answer
		$reponse = array
		(
				'success' => 0
		);
		
		// DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
		
		//decoding comment
		$commentDecode = base64_decode($_POST['comment']);
	
	 	$new_comment=$db->putComment($_POST['id_user'],$_POST['code'],$commentDecode,$_POST['mark']);
				
				
		// Json Answer
		if ($new_comment != false)
		{
			$reponse = array
			(
				'success' => 2,
			);
		}
		
		else
		{
			$reponse = array
			(
				'success' => 1,
			);
		}
			
		echo json_encode($reponse);
	}
?>