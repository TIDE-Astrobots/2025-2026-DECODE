import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


//@Config
@TeleOp(name = "Test OpMode Bin1")
public class TestOpMode2 extends LinearOpMode
{

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor wheelUpLeft = hardwareMap.get(DcMotor.class, "wheelUpLeft");
        DcMotor wheelUpRight = hardwareMap.get(DcMotor.class, "wheelUpRight");
        DcMotor wheelDownLeft = hardwareMap.get(DcMotor.class, "wheelDownLeft");
        DcMotor wheelDownRight = hardwareMap.get(DcMotor.class, "wheelDownRight");

        Servo pushServo = hardwareMap.get(Servo.class, "pushServo");

        int wheelUpLeftDirectionCorrection = 1;
        int wheelUpRightDirectionCorrection = -1;
        int wheelDownLeftDirectionCorrection = 1;
        int wheelDownRightDirectionCorrection = -1;

        double slowSpeed = 0.2;
        double movementSpeed = 0.5;

        double wheelUpLeftPower;
        double wheelUpRightPower;
        double wheelDownLeftPower;
        double wheelDownRightPower;

        double movementStickX;
        double movementStickY;
        double rotationStickX;
        double rotationStickY;

        waitForStart();

        while (opModeIsActive()) {
            pushServo.setPosition(
                    gamepad1.b ||
                    gamepad1.x
                ? 1.0 : 0
            );

            movementStickX = gamepad1.left_stick_x;
            movementStickY = gamepad1.left_stick_y;
            rotationStickX = gamepad1.right_stick_x;
            rotationStickY = gamepad1.right_stick_y;

            wheelUpLeftPower = movementStickY - movementStickX - rotationStickX;
            wheelUpRightPower = movementStickY + movementStickX + rotationStickX;
            wheelDownLeftPower = movementStickY + movementStickX - rotationStickX;
            wheelDownRightPower = movementStickY - movementStickX + rotationStickX;

            double speedM =
                    gamepad1.right_bumper ||
                    gamepad1.left_bumper
                ? slowSpeed : movementSpeed;

            wheelUpLeft.setPower(speedM * wheelUpLeftPower * wheelUpLeftDirectionCorrection);
            wheelUpRight.setPower(speedM * wheelUpRightPower * wheelUpRightDirectionCorrection);
            wheelDownLeft.setPower(speedM * wheelDownLeftPower * wheelDownLeftDirectionCorrection);
            wheelDownRight.setPower(speedM * wheelDownRightPower * wheelDownRightDirectionCorrection);
        }
    }
}