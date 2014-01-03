<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	require_once('model/Note.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//retourner les commentaires d'un utilisateur sur des  UVs 
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
	  
        // All Comments ,Marks  of Current User about UVs
		$UserComments=$db->getListUserComment($_POST['id']);
		
		// answer message 
		$response = array
		(
           'succes' => 1,
           'Comments' => $UserComments
		);
		
				echo json_encode($response);
			
	}
	else 
	{
?>
		<h1>Afficher les Commentaires</h1> 
		<form action="getListUserComment.php" method="post"> 
		    User :  <br/> 
					<select name="id" >
							<option value='-1'>Qui etes Vous ?</option>
							<?php 
								$config = require_once('config.php');
		                        $db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
								$users=$db->search('User','user','1=1');
								foreach ($users as $user)
									{
										echo '<option value='. $user->id.'>'.$user->first_name.' '.$user->last_name.'</option>';
	 								}
							?>
								    
					</select> 
					<br/> <br/> 
					<input type="submit" value="Envoyer" /> 
			
					<br/>  
					
		</form> 
<?php
	}
?>
