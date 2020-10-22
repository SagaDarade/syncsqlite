<?php

require_once('conn.php');

$id = 11;
$name = "dsdd";
$addrs="ddd";
$desg="ddd";

$qry = "UPDATE contacts SET name='$name',
							addrs='$addrs',
							desg='$desg'
							WHERE id=$id;";

$result = mysqli_query($conn,$qry);

	if($result){
		echo ("Record Updated");
	}
	else{
		echo ("Record could not updated.");
	}

?>