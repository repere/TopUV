<?php
	require_once('database/db.php');
	require_once('model/Note.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner une liste des UVs d'une meme categorie
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	if($_POST['tag'] === "sesameOuvreToi!")
	{
		//Answer message
		$response = array
		(
				'success' => 0		
		);
		
	
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
		$comments=$db->search('Note','note','1=1');
		
		$i=0;
		$result = array(array()) ;
		foreach($comments as $comment)
		{
			$id_user=$comment->id_user;
			$whereArgs=array(':id_user' => $id_user);
			$users = $db->search('User','user','id = :id_user', $whereArgs);
				
			foreach($users as $user)
			{
				$result[$i]['first_name'] = $user->first_name;
				$result[$i]['last_name'] = $user->last_name;
				$result[$i]['id'] = $comment->id;
				$result[$i]['id_user'] = $comment->id_user;
				$result[$i]['id_uv'] = $comment->id_uv;
				$result[$i]['comment'] = $comment->comment;
				$result[$i]['mark'] = $comment->note;
				$result[$i]['date'] = $comment->date;
			}
				
			$i=$i+1;
		
		}
			
		//Answer message
		$response = array
		(
			'success' => 1,
			'Comments' => $result
		);
			
		echo json_encode($response);
	}
	else
	{
		print "Ouai ?! On peut savoir ce que tu fais là ?";
	}	
?>