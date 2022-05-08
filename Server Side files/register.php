<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

//Load Composer's autoloader
require 'vendor/autoload.php';
require "connection.php";


   /* function sendEmail_verify($name,$email,$verify_token){
        $mail = new PHPMailer(true);
        try {
            //Server settings
            //$mail->SMTPDebug = SMTP::DEBUG_SERVER;                      //Enable verbose debug output
            $mail->isSMTP();                                            //Send using SMTP
            $mail->Host       = 'smtp.gmail.com';                     //Set the SMTP server to send through
            $mail->SMTPAuth   = true;                                   //Enable SMTP authentication
            $mail->Username   = 'kumarharshmgr525@gmail.com';                     //SMTP username
            $mail->Password   = '7004525778';                               //SMTP password
            $mail->SMTPSecure = 'tls';            //Enable implicit TLS encryption
            $mail->Port       = 587;                                    //TCP port to connect to; use 587 if you have set `SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS`
            //Recipients
            $mail->setFrom('kumarharshmgr525@gmail.com', $name);
            $mail->addAddress($email, $name);     //Add a recipient
        
            //Content
            $mail->isHTML(true);                                  //Set email format to HTML
            $mail->Subject = 'Email Verification from Tripper';
            $mail->Body    = "Hi '$name',

            Thanks for getting started with our Tripper!
            
            We need a little more information to complete your registration, including a confirmation of your email address. 
            <br><br>
            Click below to confirm your email address:
            <br>
            <a href='http://192.168.1.32/tripper/register/verify-email.php?token=$verify_token'</a>
            <br>
            If you have problems, please paste the above URL into your web browser.";
            $mail->AltBody = "Click below to confirm your email address:
            <br>
            <a href='http://192.168.1.32/tripper/register/verify-email.php?token=$verify_token'</a>
            <br>";
        
            $mail->send();
            echo 'Message has been sent';
        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        } 
    }*/

    $name=$_POST["fullName"];
    $username=$_POST["username"];
    $email=$_POST["email"];
    $password=$_POST["password"];
    $dateOfBirth=$_POST["date"];
    $gender=$_POST["gender"];
    $phone=$_POST["phoneNo"];
    $verify_token=md5(rand(0001,9999));
    

    $phone_query="SELECT * FROM `users` WHERE `phone` = '$phone'";
    $phone_result=mysqli_query($conn,$phone_query);
    $phone_num_rows = mysqli_num_rows($phone_result);

    $email_query="SELECT * FROM `users` WHERE `email` = '$email'";
    $email_result=mysqli_query($conn,$email_query);
    $email_num_rows = mysqli_num_rows($email_result);

    $username_query="SELECT * FROM `users` WHERE `username` = '$username'";
    $username_result=mysqli_query($conn,$username_query);
    $username_num_rows = mysqli_num_rows($username_result);

    if($phone_num_rows>0){
        $response="User with same Mobile Number Exist";
    }elseif ($email_num_rows>0) {
        $response="User with same Email Exist";
    }elseif ($username_num_rows>0) {
        $response="Username already in use.";
    }
    else
    {
        $qry="INSERT INTO `users` (`uid`, `name`, `username`, `email`, `password`, `dateOfBirth`, `gender`, `phone`,`verify_token`) 
        VALUES (NULL, '$name', '$username', '$email', '$password', '$dateOfBirth', '$gender', '$phone','$verify_token')";
       
        if(mysqli_query($conn,$qry))
        {
            sendEmail_verify("$name","$email","$verify_token");
            $response="User Registered Successfully. Verify your email";
        }
        else
            $response="Registration Failed";
    }
    echo $response;
?>