<?php
class UV
{
	public $iduv;
	public $code;
	public $designation;
	public $categorie;
	public $credit;
	public $description;
	public $note;

	// public $sent = false;

	public function toDB()
	{
		$object = get_object_vars($this);
		//unset($object['sent']);
		return $object;
	}
}