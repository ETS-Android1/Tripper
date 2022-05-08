<?php
include "connection.php";

$uid=$_POST['uid'];
$email=$_POST['email'];
$fullName=$_POST['fullName'];
$username=$_POST['username'];
$gender=$_POST['gender'];
$birthdate=$_POST['birthdate'];


$qry= "UPDATE `users` SET `name`= '$fullName', `username`='$username', `email`='$email', `dateOfBirth`= '$birthdate',
         `gender`='$gender',`createdDate` = current_timestamp() WHERE `users`.`uid` = '$uid'";

$res=mysqli_query($conn,$qry);

    if($res==true){
        $response="Profile Updated Succesfully";
    }else{
        $response="Failed to Update Profile";
    }
    echo json_encode($response);
?>