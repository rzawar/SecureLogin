<?php

if ($_POST["fname"] == '' || $_POST["lname"] == '' || $_POST["username"] == '' || $_POST["password"] == '' || $_POST["rpassword"] == '') 
	echo 'Please go back and enter all the fields.';

else{

	$fname = $_POST["fname"];
	$lname = $_POST["lname"];
	$username = $_POST["username"];
	$password = $_POST["password"];


	include "./connect.php";
	include "./passphraseGenerator.php";


	$sql ="INSERT INTO users VALUES ('$fname','$lname','$username','$password','$passphrase')";
	$result = mysqli_query($con,$sql);

	
	mysqli_close($con);

	//header( 'Location: http://localhost/securelogin/login.php' );

	//http_redirect ([ string $url [, array $params [, bool $session = false [, int $status = 0 ]]]] )
	http_redirect('http://localhost/securelogin/login.php');


}

?>
