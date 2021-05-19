<?php 
    $con = mysqli_connect("localhost", "washzone", "a53381042!", "washzone");
    mysqli_query($con,'SET NAMES utf8');

    $USER_ID = mysqli_insert_id($con);
    $USER_NAME = $_POST["USER_NAME"];
    $USER_NUMBER = $_POST["USER_NUMBER"];
    $USER_BIRTH = $_POST["USER_BIRTH"];
    $USER_CAR = $_POST["USER_CAR"];
    $USER_CARNUMBER = $_POST["USER_CARNUMBER"];

    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "isssss", $USER_ID, $USER_NAME, $USER_NUMBER, $USER_BIRTH, $USER_CAR, $USER_CARNUMBER);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>