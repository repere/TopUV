<?php
	require_once('database/db.php');
	require_once('model/Note.php');
	require_once('model/UV.php');
	require_once('model/User.php');
	
// identifcation de User par token n'a pas marché , donc j'ai en sorte que je reçois son id au lieu de son token 

	// Json Answer
	$reponse = array
	(
		'success' => 0
	);
	
	if (!empty($_POST)) 
	{
		// DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	
	 $new_comment=$db->putComment($_POST['id'],$_POST['code'],$_POST['comment'],$_POST['mark']);
				
				
				// Json Answer
				if ($new_comment!=false)
				{
					$reponse = array
					(
						'success' => 1,
					);
				}
			
			echo json_encode($reponse);
	}
	else 
	{
?>
		<h1>Notation</h1> 
		<form action="putComment.php" method="post"> 
		 
					Choir UV:  <br/> 
					
					<select name="code" >
							<option value='-1'>Choisir UV</option>
							<?php 
								$config = require_once('config.php');
		                        $db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
								$UVs=$db->getListUv('CS');
								foreach ($UVs as $uv)
									{
										echo '<option value='. $uv->code.'>'.$uv->designation.'</option>';
	 								}
							?>
								    
					</select> 
					<br/> <br/>   
					    
						Commenter:  <br/> 		
					<input type="text" name="comment" placeholder="Commentaire" /> 
					<br/> <br/>  
						Noter:  <br/> 					
					<input type="text" name="mark" placeholder="Note" /> 
					<br/> <br/>  
					
						User:  <br/> 
					<select name="id" >
							<option value='-1'>Qui etes Vous ?</option>
							<?php 
						
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