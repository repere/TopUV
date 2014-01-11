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
	
		public function toDB()
		{
			$object = get_object_vars($this);
			//unset($object['sent']);
			return $object;
		}
	}
?>