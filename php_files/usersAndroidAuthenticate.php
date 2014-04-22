<?php
  mysql_connect("127.0.0.1","root","");
  mysql_select_db("securelogin");
  $sql=mysql_query("select * from USERS")or die(mysql_error);
  while($row=mysql_fetch_assoc($sql)) $output[]=$row;
  print(json_encode($output));
  mysql_close();
?>