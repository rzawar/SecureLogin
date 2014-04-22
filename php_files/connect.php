
<?php

$user = "root";
$pass = "";
$host = "localhost";
$database = "securelogin";

$con=mysqli_connect($host, $user, $pass, $database); 

if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

?>


