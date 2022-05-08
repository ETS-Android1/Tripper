<?php
include "connection.php";

$title=$_POST['title'];
$description=$_POST['description'];
$category=$_POST['category'];
$visitTime=$_POST['visitTime'];
$budget=$_POST['budget'];
$address=$_POST['address'];
$uid=$_POST['uid'];
$placeImage=$_POST['imagePlace'];
$locationId=$_POST['locationId'];
    
    $filename="IMG".rand().".jpg";
    
    file_put_contents("images/".$filename,base64_decode($placeImage));
    
    $query="INSERT INTO `places` (`placeId`, `Title`, `Description`, `Category`, `VisitTime`, `Budget`, `Address`,`placeImage`, `uid`,`locationId`) 
    VALUES (NULL, '$title', '$description', '$category', '$visitTime', '$budget', '$address', '$filename','$uid','$locationId')";

    $res=mysqli_query($conn,$query);

    if($res==true){
        $response="Place Added Sucessfully";
    }else{
        $response="Fail to add Place";
    }
    echo json_encode($response);
?>