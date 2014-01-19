<?php

return array
(
	'dsn' => 'mysql:dbname=topuv;host=localhost',
	'username' => 'yourMsqlUserNameDedicatedToYourAndroidDatabase',
	'password' => 'passwordAssociated',
	'options' => array
	(
		PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"
	)
);
