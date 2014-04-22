<?php
  mysql_connect("127.0.0.1","root","");
  mysql_select_db("securelogin");
  $json = $_SERVER['HTTP_JSON'];//get the post you sent...
  $data = json_decode($json); //decode the json formatted string...
  $username = $data->userId;
  $sql=mysql_query("select * from USERS where username = '$username' ")or die(mysql_error);
  while($row=mysql_fetch_assoc($sql)) $output[]=$row;
  print(json_encode($output));
  mysql_close();
?>