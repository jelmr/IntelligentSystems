<?php

$DATA_FILE = "data.csv";
$TEMP_DATA_FILE = "tempData.csv";
$MONTHS = array('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December');

$method = strtoupper($_SERVER["REQUEST_METHOD"]);

switch($method){
	case "GET":
		processGET();
		break;
	case "POST":
		processPOST();
		break;
	case "PUT":
		processPUT();
		break;
	case "DELETE":
		processDELETE();
		break;
	default;
		http_response_code(405);
		break;
}

function processGET(){
	$year = $_GET['year'];
	$month = $_GET['month'];

	$data = importData();
	$query = select($data, $year,$month);

	if(count($query) == 0){
		http_response_code(404);
	} else {
		printJson($query);
	}
}

function processPOST(){
	$input = file_get_contents('php://input');
	$data = json_decode($input, true);
	$csvData = toCSV($data);

	if(validKeys($data['year'], $data['month'])){
		updateDataFile($data['year'], $data['month'], $csvData."\n");
	} else {
		http_response_code(400);
		echo "Invalid argument: year should be a number. month must be one of the following : 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'.";
	}
}


function processPUT(){
	$year = $_GET['year'];
	$month = $_GET['month'];

	if(validKeys($year, $month)){
		$input = file_get_contents('php://input');
		$data = array("year"=>$year,"month"=>$month)+json_decode($input, true);
		$csvData = toCSV($data);
		updateDataFile($data['year'], $data['month'], $csvData."\n");
	} else {
		http_response_code(400);
		echo "Invalid argument: year should be a number. month must be one of the following : 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'.";
	}


}

function processDELETE(){
	$input = file_get_contents('php://input');
	$data = json_decode($input, true);

	if(validKeys($data['year'], $data['month'])){
		updateDataFile($data['year'], $data['month'], "");
		http_response_code(204);
	} else {
		http_response_code(400);
		echo "Received: $input";
		echo "Invalid argument: year should be a number. month must be one of the following : 'January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'.";
	}
}


function validKeys($year, $month){
	return is_numeric($year) && in_array($month, $GLOBALS['MONTHS']);
}

function toCSV($data){
	return "\"$data[year] $data[month]\";\"$data[hcip]\";\"$data[muicp]\";\"$data[eicp]\"";
}


function select($data, $year, $month){
	$result = array();

	foreach($data as $line){
		if(matches($line['year'], $year) && matches($line['month'], $month)){
			array_push($result, $line);
		}
	}

	return $result;
}

function updateDataFile($year, $month, $data){
	waitForOtherThreads();
	$source = fopen($GLOBALS['DATA_FILE'], 'r');
	$destination = fopen($GLOBALS['TEMP_DATA_FILE'], 'w');

	$existed = false;
	$placed =  false;

	while (($line = fgets($source)) !== false) {
		$sourceData = readCSV($line);
		$sourceYear=  $sourceData['year'];
		$sourceMonth = $sourceData['month'];

		if(!isLargerThan($year, $month, $sourceYear, $sourceMonth) && !$placed){
			fwrite($destination, $data);
			$placed = true;
		}
		if (($sourceYear == $year) && ($sourceMonth == $month)){
			$existed = true;
		} else {
			fwrite($destination, $line);
		}
	}

	if(!$placed){ fwrite($destination, $data);}
	if(!$existed){ http_response_code(201);}

	fclose($source);
	fclose($destination);
	copy($GLOBALS['TEMP_DATA_FILE'], $GLOBALS['DATA_FILE']);
	unlink($GLOBALS['TEMP_DATA_FILE']);

}

function isLargerThan($year, $month, $sourceYear, $sourceMonth){
	if(intval($year) > intval($sourceYear)){
		return true;
	} elseif (intval($year) < intval($sourceYear)){
		return false;
	} elseif(array_search($month, $GLOBALS["MONTHS"]) > array_search($sourceMonth, $GLOBALS["MONTHS"])){
		return true;
	} else {
		return false;
	}
}

function waitForOtherThreads(){
	// The existence of a TEMP_DATA_FILE indicates that another thread is currently changing the file. We wait for it to finish.
	while(file_exists($GLOBALS['TEMP_DATA_FILE'])){
		time_nanosleep(0,rand(0,1000000)); // Sleep so that not all threads write as soon as the file is deleted.
	}
}


function matches($value, $pattern){
	return ($pattern == "" || $pattern == $value);
}

function importData(){
	$file = fopen($GLOBALS['DATA_FILE'], 'r');

	$data = array();
	while (($line = fgets($file)) !== FALSE) {
		array_push($data, readCSV($line));
	}

	fclose($file);
	return $data;
}


function printJson($array){
	$temp = array();
	foreach($array as $value){
		array_push($temp,$value);
	}
	echo json_encode($temp);
}

function strGetCSV($string, $delim){
	//str_getcsv() is not included in PHP 5.2.3
	$fh = fopen('php://temp', 'r+');
	fwrite($fh, $string);
	rewind($fh);
	$row = fgetcsv($fh,10000, $delim);
	fclose($fh);
	return $row;
}

function readCSV($input){
	$data = strGetCSV($input, ";");
	$date = explode(' ', $data[0]);
	$year = $date[0];
	$month = $date[1];
	array_shift($data);
	array_unshift($data, $year, $month);
	return array_combine(array("year", "month","hcip","muicp","eicp"),$data);
}