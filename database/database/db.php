<?php
class DB
{
	private $pdo;

	public function DB($dsn, $username, $password, $options = null)
	{
		$this->pdo = new PDO($dsn, $username, $password, $options);
	}

	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public function find($class, $table, $where, $whereArgs = array(), $order = null)
	{
		$sql = "SELECT * FROM $table WHERE $where";
		if($order != null)
		{
			$sql .= " ORDER BY $order";
		}
		$sql .= ' LIMIT 1';
		$pdoStatement = $this->pdo->prepare($sql);
		$pdoStatement->execute($whereArgs);
		
		return $pdoStatement->fetchObject($class);
	}

	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public function search($class, $table, $where, $whereArgs = array())
	{
		$sql = "SELECT * FROM $table WHERE $where";
		$pdoStatement = $this->pdo->prepare($sql);
		$pdoStatement->execute($whereArgs);
		
		return $pdoStatement->fetchAll(PDO::FETCH_CLASS, $class);
	}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public function insert($model, $table)
	{
		$fields = '';
		$values = '';
		$whereArgs = array();
		foreach($model->toDB() as $name => $value)
		{
			if($fields != '')
			{
				$fields .= ', ';
				$values .= ', ';
			}
			$fields .= $name;
			$values .= ":$name";
			$whereArgs[":$name"] = $value;
		}
		
		$sql = "INSERT INTO $table ($fields) VALUES ($values)";
		$pdoStatement = $this->pdo->prepare($sql);
		$result = $pdoStatement->execute($whereArgs);
		
		if(!$result)
		{
			return false;
		}
		
		return $this->pdo->lastInsertId();
	}
	
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public function update($model, $table, $where, $whereArgs = array())
	{
		$set = '';
		foreach($model->toDB() as $name => $value)
		{
			if($set != '')
			{
				$set .= ', ';
			}
			$set .= "$name = :$name";
			$whereArgs[":$name"] = $value;
		}
		$sql = "UPDATE $table SET $set WHERE $where";
		$pdoStatement = $this->pdo->prepare($sql);
		
		return $pdoStatement->execute($whereArgs);
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
	public function delete($table, $where, $whereArgs = array())
	{
		$sql = "DELETE FROM $table WHERE $where";
		$pdoStatement = $this->pdo->prepare($sql);
		
		return $pdoStatement->execute($whereArgs);
	}
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
// fonction qui retourne une liste des UV d'une meme Categorie
	
	public function getListUv ($ct)
	{
		$ct1 = 'TM';        //TM
		$ct2 = 'CS';        //CS
		$ct3 = 'EC';        //EC
		$ct4 = 'ME';        //ME
		$ct5 = 'CT';        //CT
		switch ($ct) 
		{
		    case $ct1:
		        $UVs = $this->search('UV','uv','categorie= :categorie',array(':categorie' =>$ct1));
		        break;
		    case $ct2:
		        $UVs = $this->search('UV','uv','categorie = :categorie',array(':categorie'=>$ct2));
		        break;
		    case $ct3:
		       $UVs = $this->search('UV','uv','categorie = :categorie',array(':categorie' =>$ct3));
		        break;
			case $ct4:
		       $UVs = $this->search('UV','uv','categorie = :categorie',array(':categorie' =>$ct4));
		        break;
			case $ct5:
		       $UVs = $this->search('UV','uv','categorie = :categorie',array(':categorie' =>$ct5));
		        break;
		}
		
		return $UVs;
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// fonction qui calcule la note moyenne d'une UV 
	public function  Note_Moyenne($iduv)
	{	
		$whereArgs=array(':iduv'=>$iduv);
		//Find UV  by id 
		$uv = $this->find('UV','uv','id = :iduv', $whereArgs);
		
		//Average Mark Calculation
		$notes=$this->search('Note','note','id_uv = :iduv', $whereArgs);
		$note_moyenne=0;
		
		foreach($notes as $note) 
			{
				$note_moyenne=$note_moyenne+$note->note;
			}
			
		$note_moyenne=$note_moyenne/count($notes);
	  
		$uv->note=$note_moyenne;
		
		//Average Mark Updating
		$table='uv';
	    $where='id = :iduv';
		
		$up=$this->update($uv,$table,$where,$whereArgs);
		
		return $note_moyenne;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// fonction qui retourne une UV à partir de son code (unique)
	public function  getUV($code)
	{
		$whereArgs=array(':code'=>$code);
		
		$uv = $this->search('UV','uv','code = :code', $whereArgs);
	
		return $uv;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// fonction qui retourne les commentaires sur une UV à partir de son code (unique)
	public function  getListComment($code)
	{
		
		$whereArgs=array(':code'=>$code);
		// Find UV by Code
		$uv = $this->find('UV','uv','code = :code', $whereArgs);
		$id_uv=$uv->id;
		
		//Find all comments about the UV
		$whereArgs=array(':id_uv' =>$id_uv);
		$comments = $this->search('Note','note','id_uv = :id_uv', $whereArgs);
		
		// Find comments authors
		$i=0;
		$result = array(array()) ;
		foreach($comments as $comment)
		{	
			$id_user=$comment->id_user;
			$whereArgs=array(':id_user' =>$id_user);
			$users = $this->search('User','user','id = :id_user', $whereArgs);
					
					foreach($users as $user)
					{
					$result[$i]['first_name'] = $user->first_name;
					$result[$i]['last_name'] = $user->last_name;
					$result[$i]['comment'] = $comment->comment;
					$result[$i]['mark'] = $comment->note;
					$result[$i]['date'] = $comment->date;							
					}
					
			$i=$i+1;
		
		}
	
		return $result;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
// fonction pour l'insertion d'un commentaire,une note d'un utilisateur (id) sur une UV (code) 
	public function  putComment($id,$code,$comment,$mark)
	{
	
		$userParameters = array
		(
			':id' => $id
		);
		
		// find user by id 
		$user = $this->find('User', 'user', 'id= :id', $userParameters);
		
		if($user !== false)
		{
			// Find UV by code
			$uvParameters = array
			(
				':code' => $code
			);
		
	
			$uv = $this->find('UV', 'uv', 'code= :code', $uvParameters);
			
			if($uv !== false)
			{
			//New_note Object Creation
				$note = new Note();
				$note->id_user = $user->id;
				$note->id_uv = $uv->id;
				$note->note = $mark;
				$note->comment = $comment;
				$note->date = date('Y-m-d');
					
			// New_Mark Insertion
				$table='note';
				$insertion = $this->insert($note,$table);
				
			//Average Mark Updating	
				$moy = $this->Note_Moyenne($uv->id);
	 
			}
		}
	return $insertion ;		
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
// Top UVs By category
	public function topUv ($ct)
	{
		$ct1 = 'TM';        //TM
		$ct2 = 'CS';        //CS
		$ct3 = 'EC';        //EC
		$ct4 = 'ME';        //ME
		$ct5 = 'CT';        //CT
		$ct6 = 'ALL';
		switch ($ct) 
		{
		    case $ct1:
		        $UVs = $this->search('UV','uv','categorie= :categorie ORDER BY note DESC',array(':categorie' =>$ct1));
		        break;
		    case $ct2:
		        $UVs = $this->search('UV','uv','categorie = :categorie ORDER BY note DESC',array(':categorie'=>$ct2));
		        break;
		    case $ct3:
		       $UVs = $this->search('UV','uv','categorie = :categorie ORDER BY note DESC',array(':categorie' =>$ct3));
		        break;
			case $ct4:
		       $UVs = $this->search('UV','uv','categorie = :categorie ORDER BY note DESC',array(':categorie' =>$ct4));
		        break;
			case $ct5:
		       $UVs = $this->search('UV','uv','categorie = :categorie ORDER BY note DESC',array(':categorie' =>$ct5));
		        break;
			case $ct6:
			   $UVs = $this->search('UV','uv','1=1 ORDER BY note DESC');

		}
		
		return $UVs;
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// fonction qui retourne les commentaires sur une UV à partir de son code (unique)
	public function getListUserComment($id)
	{
		
		$whereArgs=array(':id'=>$id);
		// Find User by id
		$user = $this->find('User','user','id = :id', $whereArgs);
		$id_user=$user->id;
		
		//Find all comments of this user
		$whereArgs=array(':id_user' =>$id_user);
		$userComments = $this->search('Note','note','id_user = :id_user', $whereArgs);
		
		// Find UVs Commented 
		$i=0;
		$result = array(array()) ;
		foreach($userComments as $comment)
		{	
			$id_uv=$comment->id_uv;
			$whereArgs=array(':id_uv' =>$id_uv);
			$UVs = $this->search('UV','uv','id = :id_uv', $whereArgs);
					
					foreach($UVs as $uv)
					{
					$result[$i]['Code'] = $uv->code;
					$result[$i]['Designation'] = $uv->designation;
					$result[$i]['User Comment '] = $comment->comment;
					$result[$i]['User Mark '] = $comment->note;
					$result[$i]['date'] = $comment->date;
					$result[$i]['Average Mark'] = $uv->note;							
					}
					
			$i=$i+1;
		
		}
	
		return $result;
	}
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
}






