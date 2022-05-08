<?php
require 'connection.php';

$phone=trim($_POST['phone']);


$query="SELECT * FROM users WHERE phone='$phone'";
$raw=mysqli_query($conn,$query);
$count=mysqli_num_rows($raw);

if($count>0){
    //$userData = array();
    $row =mysqli_fetch_assoc($raw);
    //$userData[] = $row;
    $response["result"] = "success";
    $response["message"] = "Successful";
    

    //$response=$userData;
}else{
    $response["result"] = "failure";
    $response["message"] = "Invaild";
}
echo json_encode($response);
//close the db connection
mysqli_close($conn);

?>