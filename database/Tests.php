<?php
require_once('database/db.php');
require_once('model/Note.php');
require_once('model/UV.php');
require_once('model/User.php');


$config = require_once('config.php');
$db = new DB($config['dsn'], $config['username'], $config['password'], $config['options']);

//--------------------------------------------------------------------------------------------------------------------------------------
//Essai d'une Insertion dans la table user
/*
$us1= new User();
$us1->first_name='GEMIN';
$us1->last_name='Antoine';
$us1->email='antoine.gemin@utt.fr';
$us1->login='gemant';
$us1->password='jjjjjj';
$us1->token='j1j1j1j1j1j';
$table='user';
*/
//$res=$db->insert($us1, $table);
//echo $res;

//------------------------------------------------------------------------------------------------------------------------------------------
//Essai d'un Update
/*
$us1->password='g1g1g1';
$us1->id=1;
$where="id = '1'";
//echo $where;
$db->update($us1,$table,$where,$whereArgs = array());
*/
// echo json_encode($json, JSON_PRETTY_PRINT);            5.4 required!!
//echo json_encode($json);

//------------------------------------------------------------------------------------------------------------------------------------------
// essai de supprission:
/*
$us3= new User();
$us3->first_name='Bassim';
$us3->last_name='Driss';
$us3->email='dris.basim@utt.fr';
$us3->login='dribas';
$us3->password='jjjjhh';
$us3->token='k2k2k2k2k2';
$table='user';
$db->delete($table,'id=6',$whereArgs = array());
*/

?>