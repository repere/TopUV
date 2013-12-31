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
		foreach($model as $name => $value)
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
	public function lister_uv ($ct)
	{
		$ct1 = 'Techniques et Methodes';             //TM
		$ct2 = 'Connaissances Scientifiques';        //CS
		$ct3 = 'Expression et Communication';        //EC
		$ct4 = 'Management de Entreprise';           //ME
		$ct5 = 'Culture et Technologie';             //CT
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
				$UVs = $this->search('UV','uv','cat= :cat',array(':cat' =>$ct1));
				break;
			case $ct2:
				$UVs = $this->search('UV','uv','cat = :cat',array(':cat'=>$ct2));
				break;
			case $ct3:
				$UVs = $this->search('UV','uv','cat = :cat',array(':cat' =>$ct3));
				break;
			case $ct4:
				$UVs = $this->search('UV','uv','cat = :cat',array(':cat' =>$ct4));
				break;
			case $ct5:
				$UVs = $this->search('UV','uv','cat = :cat',array(':cat' =>$ct5));
				break;
		}
	
		return $UVs;
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// fonction qui calcule la note moyenne d'une UV 
	public function  Note_Moyenne($iduv)
	{	
		$whereArgs=array(':iduv'=>$iduv);
		//Find UV
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
// fonction qui retourne des commentaires sur une UV à partir de son code (unique)
	public function  getListComment($code)
	{
		$whereArgs=array(':code'=>$code);
		//Find UV
		$uv = $this->find('UV','uv','code = :code', $whereArgs);
	   // retourner la liste des commentaires de la table note (contient les comment) " en utilisant iduv retourné par find
	   //$comment=....; 
		return $comment;
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	// fonction qui retourne des commentaires sur une UV à partir de son code (unique)
// fonction pour l'insertion d'un commentaire,une note d'un utilisateur (token) sur une UV (code) 
	public function  putComment($token,$code)
	{
		// le code de cette fonction est déja fait dans le fichier php putComment.php
		// mais on va changer le code un petit peu 
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

}






