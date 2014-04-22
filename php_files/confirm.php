<?php

if ($_POST["fname"] == '' || $_POST["lname"] == '' || $_POST["username"] == '' || $_POST["password"] == '' || $_POST["rpassword"] == ''||$_POST["password_app"] == '' || $_POST["rpassword_app"] == '') 
	echo 'Please go back and enter all the fields.';

else{

	$fname = $_POST["fname"];
	$lname = $_POST["lname"];
	$username = $_POST["username"];
	$password = $_POST["password"];
	$password_app = $_POST["password_app"];


	include "./connect.php";
	include "./passphraseGenerator.php";


	$sql ="INSERT INTO users VALUES ('$fname','$lname','$username','$password','$passphrase','$password_app')";
	$result = mysqli_query($con,$sql);

	
	mysqli_close($con);
	if($result)
	{
		echo "<h2>User registered successfully!!!</h2>";
		echo "<h3><a href=\"http://localhost:8080/NetSecWebFiles/login.php\">Click here for login</a></h3>";
	}
	else{
	echo "<h1>Some error please try again</h1>";
	}
	//header( 'Location: http://localhost/securelogin/login.php' );

	//http_redirect ([ string $url [, array $params [, bool $session = false [, int $status = 0 ]]]] )
	//http_redirect('http://localhost/securelogin/login.php');
	 //header( 'Location: http://localhost:8080/NetSecWebFiles/login.php' ) ;



}

?>
