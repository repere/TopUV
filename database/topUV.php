<?php
	require_once('database/db.php');
	require_once('model/User.php');
	require_once('model/UV.php');
	require_once('model/Note.php');
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
//Top UV by Category ( Default :All UVs of All Categories)
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

// Answer Message 
		$response = array
		(
           'succes' => 0,
		);
	if (!empty($_POST)) 
	{
		//DB Connection
		$config = require_once('config.php');
		$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
	  
        // Top UVs by Category
		$tri=$db->topUv($_POST['cat']);
		
		foreach($tri as $uv)
		{
			unset($uv->id);
			unset($uv->credit);
			unset($uv->description);
		}
		
		// Answer message 
		$response = array
		(
           'succes' => 1,
           'Top' => $tri
		);
		
				echo json_encode($response);
	}			
	else 
	{
?>
		<h1>TOP UV</h1> 
		<form action="topUV.php" method="post"> 
		 
					Choisir Catégorie:  <br/> 
					
					<select name="cat" >
					
							<option value='-1'>Choisir Catégorie</option>
							<option value='TM'>TM</option>
							<option value='CS'>CS</option>
							<option value='EC'>EC</option>
							<option value='ME'>ME</option>
							<option value='CT'>CT</option>
							<option value='ALL'>ALL</option>
							
					</select>
					
					  <br/><br/> 
		    
		    <input type="submit" value="Envoyer" /> 
		   
		</form> 
	
<?php
	}
?>