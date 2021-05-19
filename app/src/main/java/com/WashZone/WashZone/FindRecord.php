
<?php
$con = mysqli_connect("localhost", "washzone", "a53381042!", "washzone");
    mysqli_query($con,'SET NAMES utf8');

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$ID = $_GET['ID'];
$NAME = $_GET['NAME'];
$NUMBER = $_GET['NUMBER'];
$BIRTH = $_GET['BIRTH'];
$RECORD = $_GET['RDATE'];


$res = mysqli_query($con,"SELECT * FROM SMSRECORD WHERE USER_ID like '$ID' OR USER_NAME like '%$NAME%' AND USER_NUMBER like '%$NUMBER%' AND USER_BIRTH LIKE '%$BIRTH' AND RECORD_DATE LIKE '%$RECORD'");


$result = array();

while($row = mysqli_fetch_array($res)) {
	array_push($result, array('RECORD_NUM'=>$row[0],'USER_ID'=>$row[1],'USER_NAME'=>$row[2],'USER_NUMBER'=>$row[3],'USER_BIRTH'=>$row[4],'RECORD_DATE'=>$row[5])); 
	}

echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE);

mysqli_close($con); 

?>
