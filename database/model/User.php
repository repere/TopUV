<?php
class User
{
	public $id;
	public $first_name;
	public $last_name;
	public $email;
	public $login;
	public $password;
	public $salt;
	public $token;

	public function toDB()
	{
		$object = get_object_vars($this);
		return $object;
	}
}