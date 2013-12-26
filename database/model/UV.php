<?php
	class UV
	{
		public $id;
		public $code;
		public $designation;
		public $categorie;
		public $credit;
		public $description;
		public $note;
		
		//test : add this to put TM, CS, AUTRES => we will probably delete it and change to categorie instead
		public $cat;
	
		// public $sent = false;
	
		public function toDB()
		{
			$object = get_object_vars($this);
			//unset($object['sent']);
			return $object;
		}
	}
?>