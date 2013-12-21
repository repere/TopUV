<?php
require_once('database/db.php');
require_once('model/UV.php');
require_once('model/Note.php');
require_once('model/User.php');


$json = array(
	'error' => true
);

$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

//-----------------------------------------------------------------------------------------------------------------------------------------
//  lister les tous  users
    /*  	
	$Users = $db->search('User','user','1 or 1',$a=array());
	$json = array(
           'error' => false,
           'Users' => $Users
	     );
            */
//-----------------------------------------------------------------------------------------------------------------------------------------
//  lister les tous  UVs
    	/*
	$UVs = $db->search('UV','uv','1 or 1',$a=array());
	$json = array(
           'error' => false,
           'UVs' => $Users
	     );
		 */   	
//-----------------------------------------------------------------------------------------------------------------------------------------	
// Lister les UVs par categorie		
$UVs=$db->lister_uv ('Techniques et Methodes');
$json = array(
           'error' => false,
           'UVs' => $UVs
			);
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
echo json_encode($json);
?>