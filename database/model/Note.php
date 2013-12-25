<?php
	class Note
	{
		public $id;
		public $id_user;
		public $id_uv;
		public $note;
		public $comment;
		public $date;
	
		public function toDB()
		{
			$object = get_object_vars($this);
			//unset($object['message']);
			return $object;
		}
	}
?>