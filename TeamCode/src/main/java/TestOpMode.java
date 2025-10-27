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
@TeleOp(name = "Basic Auto Bin3")
public class TestOpMode extends LinearOpMode
{

    @Override
    public void runOpMode() throws InterruptedException {
        // Ticks per revolution
        double TPR = 103.8;

        Gamepad gamepad = gamepad1;

        DcMotor wheelUpLeft = hardwareMap.get(DcMotor.class, "wheelUpLeft");
        DcMotor wheelUpRight = hardwareMap.get(DcMotor.class, "wheelUpRight");
        DcMotor wheelDownLeft = hardwareMap.get(DcMotor.class, "wheelDownLeft");
        DcMotor wheelDownRight = hardwareMap.get(DcMotor.class, "wheelDownRight");

        //DcMotor intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        waitForStart();

        wheelUpLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelUpRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelDownLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wheelDownRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while (opModeIsActive()) {
            wheelUpLeft.setTargetPosition((int) Math.round(TPR) * 20);
            wheelUpLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if (!wheelUpLeft.isBusy()) {
                wheelUpRight.setTargetPosition((int) Math.round(TPR) * 20);
                wheelUpRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
    }
}