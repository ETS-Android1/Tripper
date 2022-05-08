<?php
require 'connection.php';

$phone=trim($_POST['phone']);
$password=trim($_POST['password']);


$query="SELECT * FROM users WHERE phone='$phone' and password='$password'";
$raw=mysqli_query($conn,$query);
$count=mysqli_num_rows($raw);

if($count>0){
    //$userData = array();
    $row =mysqli_fetch_assoc($raw);
    //$userData[] = $row;
    $response["result"] = "success";
    $response["message"] = "Login Successful";
    $response["user"] = $row;

    //$response=$userData;
}else{
    $response["result"] = "failure";
    $response["message"] = "Invaild Login Credentials";
}
echo json_encode($response);
//close the db connection
mysqli_close($conn);

?>