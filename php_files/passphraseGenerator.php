<?php

$userName ="adatar";
$randomPassphrase = substr(md5(uniqid($userName, true)),0,12);
echo $randomPassphrase;

?>
