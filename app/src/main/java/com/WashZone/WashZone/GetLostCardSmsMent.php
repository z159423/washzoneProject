
<?php
$con = mysqli_connect("localhost", "washzone", "a53381042!", "washzone");
    mysqli_query($con,'SET NAMES utf8');

if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$Date = $_GET['Date'];



$res = mysqli_query($con,"SELECT * FROM USER WHERE USER_BIRTH like '%$Date'");


$result = array();

while($row = mysqli_fetch_array($res)) {
	array_push($result, array('USER_ID'=>$row[0],'USER_NAME'=>$row[1],'USER_NUMBER'=>$row[2],'USER_BIRTH'=>$row[3],'USER_CAR'=>$row[4],'USER_CARNUMBER'=>$row[5])); 
	}

echo json_encode(array("result"=>$result),JSON_UNESCAPED_UNICODE);

mysqli_close($con); 
/*

$row = mysqli_fetch_array($result);
$data = $row[0];

echo $row;


$stmt = $con->prepare('select * from USER');
$stmt->execute();

if(mysqli_num_rows($result) > 0)
{
	$data = array();

	while($row=$stmt->fetch(PDO::FETCH_ASSOC))
	{
		extract($row);

		array_push($data,
		array('USER_ID' => $USER_ID,
		'USER_NAME' => $USER_NAME,
		'USER_NUMBER' => $USER_NUMBER,
		'USER_BIRTH' => $USER_BIRTH,
		'USER_CAR' => $USER_CAR,
		'USER_CARNUMBER' => $USER_CARNUMBER
		));
	}

	header('Count-Type: application/json; charset-utf8');
	$json = json_encode(array("USER_ID"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}

*/


/*
if($data){
	echo $row;
} */

/*
echo "MySQL에서 가져온 데이터는 아래와 같습니다.<br/>";


while($row = mysqli_fetch_array($result)){
        echo "USER_ID: ".$row['USER_ID']."/ USEDR_NAME: ".$row['USER_NAME']."/ USER_NUMBER : ".$row['USER_NUMBER']
        ."/ USER_BIRTH: ".$row['USER_BIRTH']."/ USER_CAR: ".$row['USER_CAR']."/ USER_CARNUMBER: ".$row['USER_CARNUMBER']
        ."<br/>";
    }


print_r($row);

mysqli_close($con);
*/

?>
