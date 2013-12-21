<?php
require_once('database/db.php');
require_once('model/Note.php');
require_once('model/UV.php');
require_once('model/User.php');

// Json Answer
$reponse = array(
	'success' => 0
);

if (!empty($_POST)) 
{


// DB Connection
$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

// Find User
$_Session['id']='23';
$userParameters = array(
	':id' => $_Session['id']
);

$user = $db->find('User', 'user', 'id = :id', $userParameters);

if($user !== false)
{

// Find UV
$uvParameters = array(
	':iduv' => $_POST['iduv']
);

	$uv = $db->find('UV', 'uv', 'iduv = :iduv', $uvParameters);

		if($uv !== false)
	{
	
	//New_note Object Creation
		$note = new Note();
		$note->id_user = $user->id;
		$note->id_uv = $uv->iduv;
		$note->note = $_POST['note'];
		$note->comment = $_POST['comment'];
		$note->date = date('Y-m-d H:i:s');
			
		// New_note Insertion
		$table='comment';
		$id = $db->insert($note,$table);
		
		if($id !== false)
		{
			$note->id = (int) $id;
		
		//Note Moyenne Updating	
		$moy = $db->Note_Moyenne($uv->iduv);
		
			$reponse = array(
				'success' => 1,
				'message' => ' Thank you for your collaboration! ',
				'note_moyenne'=>$moy
			);
		}
	
	}
	echo json_encode($reponse);
}

}else 
{
	?>
	<h1>Notation</h1> 
	<form action="Noter.php" method="post"> 
	    UV : <br/> 
		 <select name="iduv" >
						<option value='-1'>Choisir une UV</option>
						<?php 
						$config = require_once('config.php');
                        $db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
						$UVs=$db->lister_uv ('Techniques et Methodes');
						foreach ($UVs as $uv){
						echo '<option value='.$uv->iduv .'>'.$uv->designation .'</option>';
											}
						?>
							    
		</select>  
	    <br/><br/> 
	    
	    Comment :<br/> 
	    <input type="memo" name="comment" placeholder="Comment" value="" /> 
	    
	    <br/><br/> 
		 Note : <br/> 
	    <input type="text" name="note" placeholder="Note" /> 
	    
	    <br/><br/>
	    
	    <input type="submit" value="Envoyer" /> 
	</form> 
	
	<?php
}

