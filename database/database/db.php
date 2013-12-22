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
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
	
	}






