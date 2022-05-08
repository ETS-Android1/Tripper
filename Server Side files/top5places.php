<?php
include "connection.php";

$qry="SELECT places.placeId,places.Title,places.Description,places.placeImage, 
      reviews.star5+reviews.star4+reviews.star3+reviews.star2+reviews.star1 
      as total_stars from reviews INNER JOIN places 
      where places.placeId=reviews.placeId ORDER BY total_stars DESC limit 5";

$raw=mysqli_query($conn,$qry);

while($row =mysqli_fetch_assoc($raw)){
    $data[]=$row;
}
print(json_encode($data));
?>