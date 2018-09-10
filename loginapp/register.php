<?php
  require "init.php";

  $name = $_GET["name"];
  $user_name = $_GET["user_name"];
  $user_password = $_GET["user_password"];

  // Mengambil data sesuai dengan user_name
  $sql = "SELECT * from login_info WHERE user_name = '$user_name'";

  $result = mysqli_query($con,$sql);

  // Mengecek data dan insert data baru
  if (mysqli_num_rows($result)>0) {
    $status = "already exist";
  } else {
    $sql = "INSERT INTO login_info(name,user_name,user_password) values('$name','$user_name','$user_password')";
    if (mysqli_query($con,$sql)) {
      $status = "oke";
    } else {
      $status = "error";
    }
  }

  echo json_encode(array("response"=>$status));

  mysqli_close($con);
?>
