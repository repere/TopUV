<?php
	require_once('database/db.php');
	require_once('model/User.php');
	
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------
	//Login check
	//Solution found on openclassrooms.com and written by Ebola
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
	if (!empty($_POST)) 
	{
		//JSon response
		$reponse = array
		(
			'success' => 0,
			'id' => 0
		);
		
		$parameters = array
		(
				':login' => $_POST['login'],
		);
		
		// On initialise $existence_ft
	    $existence_ft = '';
	
	    // Si le fichier existe, on le lit
	    if(file_exists('antiBruteForce/'.$_POST['login'].'.txt'))
	    { 
	        // On ouvre le fichier
	        $fichier_tentatives = fopen('antiBruteForce/'.$_POST['login'].'.txt', 'r+');
	
	        // On récupère son contenu dans la variable $infos_tentatives
	        $contenu_tentatives = fgets($fichier_tentatives);
	
	        // On découpe le contenu du fichier pour récupérer les informations
	        $infos_tentatives = explode(';', $contenu_tentatives);
	
	
	        // Si la date du fichier est celle d'aujourd'hui, on récupère le nombre de tentatives
	        if($infos_tentatives[0] == date('d/m/Y'))
	        {
	            $tentatives = $infos_tentatives[1];
	        }
	        // Si la date du fichier est dépassée, on met le nombre de tentatives à 0 et $existence_ft à 2
	        else
	        {
	            $existence_ft = 2;
	            $tentatives = 0; // On met la variable $tentatives à 0
	        }
	
	
	    }
	    
	    // Si le fichier n'existe pas encore, on met la variable $existence_ft à 1 et on met les $tentatives à 0
	    else
	    {
	        $existence_ft = 1;
	        $tentatives = 0;
	    }
	
	
	    // S'il y a eu moins de 10 identifications ratées dans la journée, on laisse passer
	    if($tentatives < 10)
	    {
	        //DB Connection
			$config = require_once('config.php');
			$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);
			
			//Acces Control
			$user = $db->find('User', 'user', 'login = :login', $parameters);
			if($user !== false)
	        {
				//Retrieve salt,encrypted password and token from db
				$salt=$user->salt;
				$encrypted_password_from_db=$user->password;
				$id=$user->id;
				
				//Encode password from the user
				$password_encoded = base64_encode( sha1( $_POST['password'] . $salt, true) . $salt );
				
				//Compare hash password (one from DB and the other from user) between them
				if ($password_encoded === $encrypted_password_from_db) 
		        {
					$reponse = array
					(
						'success' => 1,
						'id' => $id,
					);
				}
	           	// Si le mot de passe est faux
		        else
		        {    
					// Si le fichier n'existe pas encore, on le créer
	               	if($existence_ft == 1)
	               	{
	                	$login = $user->login;
	               		$creation_fichier = fopen('antiBruteForce/'.$login.'.txt', 'a+'); // On créer le fichier puis on l'ouvre
	                   	fputs($creation_fichier, date('d/m/Y').';1'); // On écrit à l'intérieur la date du jour et on met le nombre de tentatives à 1
	                   	fclose($creation_fichier); // On referme
	               	}
	               	// Si la date n'est plus a jour
	               	elseif($existence_ft == 2)
	               	{
	                   	fseek($fichier_tentatives, 0); // On remet le curseur au début du fichier
	                   	fputs($fichier_tentatives, date('d/m/Y').';1'); // On met à jour le contenu du fichier (date du jour;1 tentatives)
	               	}
	               	else
	               	{
	                   	fseek($fichier_tentatives, 11); // On place le curseur juste devant le nombre de tentatives
	                   	fputs($fichier_tentatives, $tentatives + 1); // On ajoute 1 au nombre de tentatives
	               	}
				}
        	}	
	    }
	    
	    // Si on a ouvert un fichier, on le referme (eh oui, il ne faut pas l'oublier)
	    if($existence_ft != 1)
	    {
	    fclose($fichier_tentatives);
	    }
		
	    echo json_encode($reponse);
	}  
		
	else 
	// In order to log manually user without android app, we purpose basic online browser login
	// Should be remove ?!
	{
?>
		<h1>Login</h1> 
		<form action="db_login.php" method="post"> 
		    Login : <br/> 
		    <input type="text" name="login" placeholder="login" /> 
		    
		    <br/><br/> 
		    
		    Password  :<br/> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    
		    <br/><br/> 
		    
		    <input type="submit" value="Login" /> 
		</form> 
	
	<a href="db_register.php">Register</a>
<?php
	}
?>