<?php
if(isset($_GET['action']))
{
    require 'consultas_BD.php';
    $cons=new consultas();

    print_r($_GET);
    $action=$_GET['action'];

        $fecha=new DateTime('NOW');
		$fecha1=$fecha->format('Y-m-d H:i:s');
		str_replace(':','_',$fecha1);
		echo" aqui llego desde su app";
    switch($action)
    {
    
       case "registro":
            $usr=$_GET['usr'];
            $pass=$_GET['pass'];
            $resp=$cons->ejecutar("insert into usuarios (usr,pass) Values ('$usr','$pass') ");
             $data[0]=array("respuesta"=>"activado");
            break;
           
        case "login":
            $usr=$_GET['usr'];
            $pass=$_GET['pass'];
            $resp=$cons->ejecutar("select * from usuarios where usr='$usr' and pass='$pass'");
            if(mysqli_num_rows($resp)==1)
                  $data[0]=array("respuesta"=>"ok");
        break;
        case "reg1":
                    //   $data[0]=array("carpeta"=>$carpeta);
                  $data[0]=array("respuesta"=>"activado");
            break;
            case "update":
                $resp=$cons->ejecutar("select * from usuarios where notificar=1");
                $n=mysqli_num_rows($resp);
                echo "n"+$n;
            //    $nom="";
            //for($i=0;$i<$n;$i++)
             //    $nom=$nom+$resp
             //     $data[0]=array("respuesta"=>"ok");
                if($n>0){  
                $data[0]=array("respuesta"=>"activado","nombre"=>"usuarios nuevos:".$n);
                $cons->ejecutar("update usuarios set notificar=1 where notificar=1");
                }
                
                
                break;
                
                
    }
    
}

// obtiene que tipo de operacion realizara el dato del json
	   if(isset($data)) $datares = array( 'status'=>50, 'Registros'=>$data);
	   else             $datares = array( 'status'=>500 );
     
     echo(json_encode($datares));
?>