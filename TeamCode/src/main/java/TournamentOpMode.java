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
@TeleOp(name = "BasicMovement OpMode Bin6")
public class TournamentOpMode extends LinearOpMode
{

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad gamepad = gamepad1;

        DcMotor wheelUpLeft = hardwareMap.get(DcMotor.class, "wheelUpLeft");
        DcMotor wheelUpRight = hardwareMap.get(DcMotor.class, "wheelUpRight");
        DcMotor wheelDownLeft = hardwareMap.get(DcMotor.class, "wheelDownLeft");
        DcMotor wheelDownRight = hardwareMap.get(DcMotor.class, "wheelDownRight");

        int wheelUpLeftDirectionCorrection = 1;
        int wheelUpRightDirectionCorrection = -1;
        int wheelDownLeftDirectionCorrection = 1;
        int wheelDownRightDirectionCorrection = -1;

        double movementSpeed = 0.4;

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
            movementStickX = gamepad.left_stick_x;
            movementStickY = gamepad.left_stick_y;
            rotationStickX = gamepad.right_stick_x;
            rotationStickY = gamepad.right_stick_y;

            wheelUpLeftPower = movementStickY - movementStickX - rotationStickX;
            wheelUpRightPower = movementStickY + movementStickX + rotationStickX;
            wheelDownLeftPower = movementStickY + movementStickX - rotationStickX;
            wheelDownRightPower = movementStickY - movementStickX + rotationStickX;

            wheelUpLeft.setPower(movementSpeed * wheelUpLeftPower * wheelUpLeftDirectionCorrection);
            wheelUpRight.setPower(movementSpeed * wheelUpRightPower * wheelUpRightDirectionCorrection);
            wheelDownLeft.setPower(movementSpeed * wheelDownLeftPower * wheelDownLeftDirectionCorrection);
            wheelDownRight.setPower(movementSpeed * wheelDownRightPower * wheelDownRightDirectionCorrection);
        }
    }
}