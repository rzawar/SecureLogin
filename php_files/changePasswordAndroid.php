<?php
  mysql_connect("127.0.0.1","root","");
  mysql_select_db("securelogin");
  $json = $_SERVER['HTTP_JSON'];//get the post you sent...
  $data = json_decode($json); //decode the json formatted string...
  $userId = $data->userId;
  $password = $data->password;
  $sql=mysql_query("update USERS set password = '$password' where username = '$userId' ")or die(mysql_error);
  if($sql!=1)
  echo "Fail";
  else
  echo "Success";
  mysql_close();
?>