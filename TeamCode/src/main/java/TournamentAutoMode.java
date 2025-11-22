import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


//@
@Autonomous(name = "Autonomous")
public class TournamentAutoMode extends LinearOpMode
{
    static final double TICKS_PER_REVOLUTION = 537.7;
    static final double WHEEL_DIAMETER_MM = 96.0;

    static final double ROTATION_FACTOR = 2430.0;
    static final double THIRD_ROTATION = 200.0;

    private DcMotor wheelUpLeft = null;
    private DcMotor wheelUpRight = null;
    private DcMotor wheelDownLeft = null;
    private DcMotor wheelDownRight = null;

    private Servo pushServo = null;
    private DcMotor drumMotor = null;
    private DcMotor outtakeMotor = null;

    private void reset(@NonNull DcMotor dcMotor) {
        dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void spinDistance(@NonNull DcMotor dcMotor, double distance, double power) {
        dcMotor.setTargetPosition(
            (int) Math.round(TICKS_PER_REVOLUTION * (distance / (WHEEL_DIAMETER_MM * Math.PI)))
        );
        dcMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        dcMotor.setPower(power);
    }

    private void spin(double angle, double power) {
        reset(wheelUpLeft);
        reset(wheelUpRight);
        reset(wheelDownLeft);
        reset(wheelDownRight);

        double distance = (angle / 360) * ROTATION_FACTOR;

        while (opModeIsActive()) {
            spinDistance(wheelUpLeft, - distance, power);
            spinDistance(wheelUpRight, - distance, power);
            spinDistance(wheelDownLeft, - distance, power);
            spinDistance(wheelDownRight, - distance, power);

            if (
                !wheelUpLeft.isBusy() &&
                !wheelUpRight.isBusy() &&
                !wheelDownLeft.isBusy() &&
                !wheelDownRight.isBusy()
            ) {
                return;
            }
        }
    }
    private void drive(double distance, double power) {
        reset(wheelUpLeft);
        reset(wheelUpRight);
        reset(wheelDownLeft);
        reset(wheelDownRight);

        while (opModeIsActive()) {
            spinDistance(wheelUpLeft, - distance, power);
            spinDistance(wheelUpRight, + distance, power);
            spinDistance(wheelDownLeft, - distance, power);
            spinDistance(wheelDownRight, + distance, power);

            if (
                !wheelUpLeft.isBusy() &&
                !wheelUpRight.isBusy() &&
                !wheelDownLeft.isBusy() &&
                !wheelDownRight.isBusy()
            ) {
                return;
            }
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad gamepad = gamepad1;

        wheelUpLeft = hardwareMap.get(DcMotor.class, "wheelUpLeft");
        wheelUpRight = hardwareMap.get(DcMotor.class, "wheelUpRight");
        wheelDownLeft = hardwareMap.get(DcMotor.class, "wheelDownLeft");
        wheelDownRight = hardwareMap.get(DcMotor.class, "wheelDownRight");

        pushServo = hardwareMap.get(Servo.class, "pushServo2");
        drumMotor = hardwareMap.get(DcMotor.class, "drumMotor2");
        outtakeMotor = hardwareMap.get(DcMotor.class, "outtakeMotor2");

        waitForStart();

        drumMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pushServo.setPosition(0.0);

        drive(-1000, 0.5);
        //spin(360, 0.5);
        //drive(1000, 0.5);

        outtakeMotor.setPower(0.8);

        double i = 0;

        while (opModeIsActive()) {
            i++;

            sleep(2000);
            pushServo.setPosition(1.0);

            sleep(2000);
            pushServo.setPosition(0.0);

            sleep(2000);
            drumMotor.setTargetPosition((int) Math.round(THIRD_ROTATION * i));
            drumMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            drumMotor.setPower(0.8);
        }
    }
}