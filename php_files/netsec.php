<?php

$passphrase = "vB3c56Rln726aGi0";
$userName= "adatar";

$currentTime = intval(time()/30);
$currentTimeWLag = intval(time()/30)-1;


//echo $currentTime;

$data = $userName . $currentTime;
$dataWlag = $userName . $currentTimeWlag;

//echo $data;

$bigHash = hash_hmac ("md5" , "rohit" ,"rohit");
$bigHashWLag = hash_hmac ("md5" , $dataWLag , $passphrase);

echo $bigHash . "<BR>";
echo $bigHashWLag;


?>
