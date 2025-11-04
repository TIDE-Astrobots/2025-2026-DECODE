import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


//@
@Autonomous(name = "Basic Auto Bin19")
public class TestOpMode extends LinearOpMode
{
    static final double TICKS_PER_REVOLUTION = 537.7;
    static final double WHEEL_DIAMETER_MM = 96.0;

    private DcMotor wheelUpLeft = null;
    private DcMotor wheelUpRight = null;
    private DcMotor wheelDownLeft = null;
    private DcMotor wheelDownRight = null;

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

        while (opModeIsActive()) {
            spinDistance(wheelUpLeft, - angle, power);
            spinDistance(wheelUpRight, - angle, power);
            spinDistance(wheelDownLeft, - angle, power);
            spinDistance(wheelDownRight, - angle, power);

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

        //DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        waitForStart();

        reset(wheelUpLeft);
        reset(wheelUpRight);
        reset(wheelDownLeft);
        reset(wheelDownRight);

        drive(1000, 0.5);
        spin(1000, 0.5);
        drive(1000, 0.5);
    }
}