<?php 
require_once "conexion_BD.php";
 class consultas extends conexion_BD
 {    
    public function __construct() {  parent::__construct(); }
    public function crear_tabla($tab, $campos)
    {
		 $instr='CREATE TABLE IF NOT EXISTS '.$tab.' ('.$campos.')';
		 $res = $this->_db->query("show tables like '".$tab."'");
		if($res->num_rows==0) $result = $this->_db->query($instr);
    }
    public function extraer($tab, $condicion)
    {
		
		$result = $this->_db->query('SELECT * FROM '.$tab.' where '.$condicion); 
		return $result;
    }

    public function insertar($tab,$campos,$valores)
    {		
		$result = $this->_db->query('INSERT INTO '.$tab.' '.$campos.' VALUES '.$valores); 
		return $result;
    }

    public function eliminar($tab, $condicion)
    {
     $result = $this->_db->query('DELETE FROM '.$tab.' where '.$condicion);      
     return $result;
    }

    public function actualizar($tab, $campos,$valores,$condicion)
    {
		$act='';$ind=0;
		if(is_array($campos)){
		foreach($campos as $key)
		{
			$act.=$key.' = "'.$valores[$ind].'" ';
			$ind++;
			if($ind<count($campos))	$act.=', ';
		 }
		}
		else $act=$campos.'=.'.$valores;
		$sql='UPDATE '.$tab.' SET '.$act.' where '.$condicion;
		//echo $sql;
		$result = $this->_db->query($sql);
		if(is_object($result)){}
		else {$result=$this->_db->error;}
     return $result;
    }

    public function ejecutar($sql)
    {
		//echo $sql;
     $result = $this->_db->query($sql);      
     return $result;
    }
    public function ejecutar2($sql)
    {
		//echo $sql;
     $result = $this->_db->query($sql);      
     return $this->_db->affected_rows;
    }
public function ejecutar3($sql,$option)
    {
     $result = $this->_db->query($sql);      
     return $this->_db->$option;
    }

 }
?>