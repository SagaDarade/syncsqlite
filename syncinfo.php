<?php
require"conn.php";
	
		$Name=$_POST['name'];	
		
		$Address=$_POST['addrs'];	
		
		$Designation=$_POST['desg'];	
		
		$qry="insert into contacts (name,addrs,desg) values('$Name','$Address','$Designation');";
		
		$result=mysqli_query($conn,$qry);
	
		if(($conn->query($qry) === TRUE))
		{
			echo json_encode(array("response"=>"ok"));
		}
		else
		{
			echo json_encode(array("response"=>"fail"));
		}
		
		
			$id = 11;
			$name = "saaaaaaa";
			$addrs="ddd";
			$desg="ddd";

			$qry1 = "UPDATE contacts SET name='$name',
										addrs='$addrs',
										desg='$desg'
										WHERE id=$id;";

			$result = mysqli_query($conn,$qry1);

			if($result){
				echo ("Record Updated");
			}
			else{
				echo ("Record could not updated.");
			}


			$id = 12;
			$name = "sagar";
			$addrs="akole";
			$desg="develop";

			$qry2 = "DELETE FROM contacts WHERE id=$id;";

			$result = mysqli_query($conn,$qry2);

			if($result){
				echo ("Record Deleted");
			}
			else{
				echo ("Record could not delete.");
			}
		
		
		
$conn->close();
	
?>