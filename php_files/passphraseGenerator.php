<?php

$passphrase = substr(md5(uniqid($username, true)),0,12);
echo $randomPassphrase;

?>
