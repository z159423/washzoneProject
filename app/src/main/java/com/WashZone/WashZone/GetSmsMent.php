
<?php
$con = mysqli_connect("localhost", "washzone", "a53381042!", "washzone");
    mysqli_query($con,'SET NAMES utf8');

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}



$res = mysqli_query($con,"SELECT * FROM SMSMENT");


$result = array();

while($row = mysqli_fetch_array($res)) {
	array_push($result, array('SMSMENT'=>$row[0])); 
	}

echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE);

mysqli_close($con); 

?>
