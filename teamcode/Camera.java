package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Rotating Camera", group="mm")

public class Camera extends LinearOpMode {

    public AprilTags aprilTags;

    private Servo cameraMount = null;


    private final double THRESHOLD = 5.5;
    private final double INCREMENT = 0.001;


    double target = .5;
    double bearingMin = 9999;

    @Override
    public void runOpMode(){
        telemetry.addLine("Status: Initializing please wait....");
        telemetry.update();

        aprilTags = new AprilTags(this);

        initialize();
        aprilTags.init();

        telemetry.addLine("Status: Initialized, go for it.");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            cameraMount.setPosition(getTargetPos());
        }


    }

    public double getTargetPos(){

        double currentBearing = aprilTags.getBearing(7);
//
//        if (currentBearing < 996) {
//            target -= currentBearing * 0.00625;
//        }
//
//        if(currentBearing < bearingMin){
//            bearingMin = currentBearing;
//        }
//
//        telemetry.addData("bearing", currentBearing);
//        telemetry.addData("initTarget", target);
//
////        //if (currentBearing != 999) {
////            if (target > 1) { //adjust values to ensure they are usable
////                target -= 1;
////            } else if (target < 0) {
////                target += 1;
////            }
//       // }
//
//        telemetry.addData("target", target);
//        telemetry.addData("minBearing", bearingMin);
//        telemetry.update();
        if(Math.abs(currentBearing) > THRESHOLD && currentBearing < 996) {
            target -= INCREMENT * (Math.abs(currentBearing) / currentBearing);
        }

        telemetry.addData("bearing", currentBearing);
        telemetry.addData("target", target);
        telemetry.update();

        return target;
    }

    private void initialize(){
        cameraMount = hardwareMap.get(Servo.class, "mount");

        cameraMount.setPosition(.5);
    }
}
