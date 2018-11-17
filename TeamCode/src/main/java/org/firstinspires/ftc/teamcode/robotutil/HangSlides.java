package org.firstinspires.ftc.teamcode.robotutil;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class HangSlides {
    LinearOpMode opMode;
    private MotorGroup hangMotors;
    private DcMotor topMotor, bottomMotor;
//    private int[] encoderStartPosition;

    private static final int TOTAL_TICKS = (int) (12 * Values.TICKS_PER_INCH_FORWARD);

    public HangSlides(LinearOpMode opMode) {
        this.opMode = opMode;
        this.topMotor = opMode.hardwareMap.dcMotor.get("hangTop");
        this.bottomMotor = opMode.hardwareMap.dcMotor.get("hangBottom");
        this.hangMotors = new MotorGroup(new DcMotor[]{topMotor, bottomMotor});
        hangMotors.useEncoders();
        hangMotors.setBrake();

    }

    public void setPower(double power) {
        hangMotors.setPower(0);
    }

    public void moveSlides(Direction direction,double power,double inches,double timeoutS) {

        hangMotors.resetEncoders();
        hangMotors.useEncoders();
        int topTarget,bottomTarget;
        switch (direction) {
            case UP:
                topTarget = topMotor.getCurrentPosition() + (int) (inches * Values.TICKS_PER_INCH_FORWARD);
                bottomTarget = bottomMotor.getCurrentPosition() + (int) (inches * Values.TICKS_PER_INCH_FORWARD);
                power = Math.abs(power);
                break;
            case DOWN:
                topTarget = topMotor.getCurrentPosition() - (int) (inches * Values.TICKS_PER_INCH_FORWARD);
                bottomTarget = bottomMotor.getCurrentPosition() - (int) (inches * Values.TICKS_PER_INCH_FORWARD);
                power = -Math.abs(power);
                break;

            default:
               topTarget = topMotor.getCurrentPosition();
               bottomTarget = bottomMotor.getCurrentPosition();

        }

        double topError = topTarget - topMotor.getCurrentPosition();
        double bottomError = topTarget - bottomMotor.getCurrentPosition();

        double startTime = System.currentTimeMillis();
        double minError = .1;
        while (Math.abs(topError) > minError && Math.abs(bottomError) > minError &&
                (System.currentTimeMillis() - startTime) / 1000 < timeoutS) {

//            double rfPower = getProportionalPower(power, kp * rfError * power);
//            double lfPower = getProportionalPower(power, kp * lfError * power);
//            double rbPower = getProportionalPower(power, kp * rbError * power);
//            double lbPower = getProportionalPower(power, kp * lbError * power);
            hangMotors.setPower(power);

            topError = topTarget - topMotor.getCurrentPosition();
            bottomError = topTarget - bottomMotor.getCurrentPosition();


        }
        hangMotors.setPower(0);


    }
}
