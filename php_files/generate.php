<html>

<head>
<title> SecureLogin | Generate Emulator </title>
</head>

<body>

<div style="text-align: center;">
<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: #FFFFFF; border: 2px solid #D4D4D4; border-radius: 5px; box-shadow: 0px 0px 8px #D4D4D4; margin: 50px auto auto;">

<div style="background: #D4D4D4; border-radius: 5px 5px 0px 0px; padding: 15px;"><span style="font-family: verdana,arial; font-size: 1.00em; font-weight:bold;">USER DETAILS</span></div>
<div style="background: ; padding: 15px">

<style type="text/css" scoped>
td { text-align:left; font-family: verdana,arial; color: #000000; font-size: 1.00em; }
input { border: 1px solid #CCCCCC; border-radius: 5px; color: #666666; display: inline-block; font-size: 1.00em;  padding: 5px; width: 100%; }
input[type="button"], input[type="reset"], input[type="submit"] { height: auto; width: auto; cursor: pointer; box-shadow: 0px 0px 5px #D4D4D4; float: right; margin-top: 10px; }
table.center { margin-left:auto; margin-right:auto; }
.error { font-family: verdana,arial; color: #000000; font-size: 1.00em; }
</style>

<?php

$username = $_POST["username"];
$passphrase = $_POST["passphrase"];

if ($username != '' && $passphrase != '')
{
	include "./netsec.php";
	generateTokens($username, $passphrase);	
	echo $genToken." ".intval(time()/30);
	
}

?>
	<form method="post" action="generate.php" name="aform" target="_top">

	<input type="hidden" name="action" value="login">
	<input type="hidden" name="hide" value="">

	<table class='center'>
	<tr><td>Username:</td><td><input type="text" name="username"></td></tr>
	<tr><td>Passphrase:</td><td><input type="text" name="passphrase"></td></tr>
	<tr><td>&nbsp;</td><td><input type="submit" value="Login"></td></tr>
	<tr><td colspan=2>&nbsp;</td></tr>
	</table>
	</form>
	</div></div></div>


</body>
</html>
