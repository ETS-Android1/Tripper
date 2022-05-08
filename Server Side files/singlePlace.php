<?php
include "connection.php";

$placeId=$_GET['placeId'];

$qry="SELECT places.Title,places.placeImage,places.Description,places.VisitTime,places.Budget,places.Address,places.modifiedDate 
        from places where placeId='$placeId'";

$raw=mysqli_query($conn,$qry);

while($row =mysqli_fetch_assoc($raw)){
    $data[]=$row;
}
print(json_encode($data));
?>