<?php


$currentTime = intval(time()/30);
$currentTimeWLag = intval(time()/30)-1;

$genToken ='';
$genTokenWLag ='';

function generateTokens($userN, $passp)
{
	global $currentTime;
	global $currentTimeWLag;

	$passphrase = $passp;
	$userName = $userN;

	$data = $userName . $currentTime;
	$dataWLag = $userName . $currentTimeWLag;

	$bigHash = hash_hmac("md5" , $data , $passphrase);
	$bigHashWLag = hash_hmac("md5" , $dataWLag , $passphrase);

	$hashSplitArray = str_split ($bigHash);
	$hashSplitArrayWL = str_split ($bigHashWLag);

	global $genToken;
	$genToken = extractTokens($hashSplitArray);

	global $genTokenWLag;
	$genTokenWLag = extractTokens($hashSplitArrayWL);

}


function extractTokens($hashArray)
{
	$token ='';
	$count = 0;
	foreach($hashArray as $ch) {

		if($ch >= '0' && $ch <= '9'){
			$token = $token . $ch;
			$count++;
		}
		if ($count == 6)
			break;
	
	}
	return $token;
}

?>
