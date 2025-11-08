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
@TeleOp(name = "3rd Rotation Drum")
public class TournamentOpMode extends LinearOpMode
{

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor wheelUpLeft = hardwareMap.get(DcMotor.class, "wheelUpLeft");
        DcMotor wheelUpRight = hardwareMap.get(DcMotor.class, "wheelUpRight");
        DcMotor wheelDownLeft = hardwareMap.get(DcMotor.class, "wheelDownLeft");
        DcMotor wheelDownRight = hardwareMap.get(DcMotor.class, "wheelDownRight");

        DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        DcMotor outtakeMotor = hardwareMap.get(DcMotor.class, "outtakeMotor");

        Servo pushServo = hardwareMap.get(Servo.class, "pushServo");
        DcMotor drumMotor = hardwareMap.get(DcMotor.class, "drumMotor");

        int drumRotation = 0;

        boolean left3rd = false;
        boolean right3rd = false;
        boolean left6th = false;
        boolean right6th = false;

        int wheelUpLeftDirectionCorrection = 1;
        int wheelUpRightDirectionCorrection = -1;
        int wheelDownLeftDirectionCorrection = 1;
        int wheelDownRightDirectionCorrection = -1;

        // REV motors should have 28 tpr
        // Doesn't spin far enough cause the encoder starts
        // slowing down the motor before it reaches the correct
        // spot but that makes it go too slow and it never reaches
        // the correct spot. JUST A GUESS
        double drumMotorTPR = 30.65 * 5.0 * 5.0;

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

        drumMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive()) {
            outtakeMotor.setPower(gamepad2.dpad_up ? 0.8 : (gamepad2.dpad_down ? -0.4 : 0));

            pushServo.setPosition(
                    gamepad2.b ||
                    gamepad2.x
                ? 2.0 : 0.0
            );

            int oldDrumRotation = drumRotation;

            if (gamepad2.left_bumper && !left6th) drumRotation -= 1;
            if (gamepad2.right_bumper && !right6th) drumRotation += 1;
            if (gamepad2.left_trigger > 0 && !left3rd) drumRotation -= 2;
            if (gamepad2.right_trigger > 0 && !right3rd) drumRotation += 2;

            if (gamepad2.a) drumRotation -= drumRotation % 2;
            if (gamepad2.y) drumRotation -= (drumRotation % 2) - 1;

            if (oldDrumRotation != drumRotation) {
                drumMotor.setTargetPosition((int) Math.round(
                    drumMotorTPR * (((double) drumRotation) / 6.0)
                ));
                drumMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                drumMotor.setPower(0.8);
            }

            //drumMotor.setPower(0.5 * gamepad2.right_trigger - 0.5 * gamepad2.left_trigger);

            left3rd = gamepad2.left_trigger > 0;
            right3rd = gamepad2.right_trigger > 0;
            left6th = gamepad2.left_bumper;
            right6th = gamepad2.right_bumper;

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