<?php
include "connection.php";

$category=$_GET['category'];

$qry="SELECT places.placeId,places.Title,places.placeImage,location.name,location.state
        FROM places,location WHERE places.locationId=location.id and Category= '$category'";

$raw=mysqli_query($conn,$qry);

while($row =mysqli_fetch_assoc($raw)){
    $data[]=$row;
}
print(json_encode($data));
?>