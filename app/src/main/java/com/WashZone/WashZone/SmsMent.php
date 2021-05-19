<?php 
    $con = mysqli_connect("localhost", "washzone", "a53381042!", "washzone");
    mysqli_query($con,'SET NAMES utf8');

	$res = mysqli_query($con,"DELETE FROM SMSMENT");


    $SMSMENT = $_POST["SMSMENT"];

    $statement = mysqli_prepare($con, "INSERT INTO SMSMENT VALUES (?)");
    mysqli_stmt_bind_param($statement, "s", $SMSMENT);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>