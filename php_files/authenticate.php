<html>
<body>

<?php

$username = $_POST["username"];
$password = $_POST["password"];
$token= $_POST["token"];

include "./connect.php";


$sql ="SELECT * FROM users WHERE username='$username'";
$result = mysqli_query($con,$sql);
$count=mysqli_num_rows($result);


if($count==1){
	$row = mysqli_fetch_array($result);
	
	include "./netsec.php";
	
	generateTokens($username, $row['passphrase']);
	
    if ($password == $row['password'] && ($token == $genToken || $token == $genTokenWLag)){
        echo "Login Successful";
    }
    else {
        echo "Wrong Username or Passwordd";
        }
}
else{
    echo "Wrong Username or Password";
    }
/*

while($row = mysqli_fetch_array($result))
  {
  echo $row['fName'] . " " . $row['lName'] . " " . ;
  echo "<br>";
  }
*/


mysqli_close($con);
?>

</body>
</html>

