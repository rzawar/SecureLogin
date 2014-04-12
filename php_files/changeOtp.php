<?php
  mysql_connect("127.0.0.1","root","");
  mysql_select_db("USERS");
  $json = $_SERVER['HTTP_JSON'];//get the post you sent...
  $data = json_decode($json); //decode the json formatted string...
  $otp = $data->otp;
  $username = $data->userId;
  $sql=mysql_query("update USERS set otp = '$otp' where username = '$username' ")or die(mysql_error);
  if($sql!=1)
  echo "Fail";
  else
  echo "Success";
  mysql_close();
?>