package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTags {

     private final OpMode opMode;

     public AprilTags (OpMode opMode){
          this.opMode = opMode;

     }


     AprilTagProcessor aprilTagProcessor;
     public VisionPortal visionPortal;

     public double getBearing(int tagId){
          AprilTagDetection idInfo = getIdInfo(tagId);
          if(idInfo != null) {

               return idInfo.ftcPose.bearing;
          } else {
               return 999;
          }
     }

     public AprilTagDetection getIdInfo(int id){
          List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

          for(AprilTagDetection detection : currentDetections){

               if(detection.id == id){
                    return detection;
               }
          }

          return null;
     }


     public void init(){
          aprilTagProcessor = new AprilTagProcessor.Builder()
                  .setDrawAxes(true)
                  .setDrawCubeProjection(true)
                  .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                  .build();

          VisionPortal.Builder builder = new VisionPortal.Builder();

          visionPortal = builder.setCamera(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"))
                  .addProcessor(aprilTagProcessor)

                  .build();
     }
}
