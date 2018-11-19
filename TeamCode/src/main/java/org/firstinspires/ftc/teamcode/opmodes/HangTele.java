/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robotutil.Logger;
import org.firstinspires.ftc.teamcode.tasks.DriveTrainTaskMecanum;
import org.firstinspires.ftc.teamcode.tasks.DumperTask;
import org.firstinspires.ftc.teamcode.tasks.HangTask;
import org.firstinspires.ftc.teamcode.tasks.HookTask;
import org.firstinspires.ftc.teamcode.tasks.SweeperTask;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Teleop Final")

public class HangTele extends LinearOpMode {
    private HangTask hangTask;
    private DriveTrainTaskMecanum driveTrainTask;
    private DumperTask dumperTask;
    private SweeperTask sweeperTask;
    private HookTask hookTask;
    private Telemetry.Item opmodeStatus = telemetry.addData("STATUS","CONSTRUCTING");
    private Logger l = new Logger("TELEOP");

    public void runOpMode() throws InterruptedException{
        opmodeStatus.setValue("INITIALIZING");
        telemetry.update();
        l.log("initializing");

        initialize();
        opmodeStatus.setValue("READY");
        telemetry.update();
        l.log("waiting for start");

        waitForStart();
        if(opModeIsActive()){
            startAllThreads();
            l.log("started threads");
        }
        while(opModeIsActive() && !isStopRequested()) {
            opmodeStatus.setValue("RUNNING");
            telemetry.update();

        }
        stopAllThreads();
    }
    private void startAllThreads(){
        hangTask.start();
        driveTrainTask.start();
        dumperTask.start();
        sweeperTask.start();
        hookTask.start();
    }
    private void stopAllThreads(){
        hangTask.stopThread();
        driveTrainTask.stopThread();
        sweeperTask.stopThread();
        dumperTask.stopThread();
        hookTask.stopThread();
        opmodeStatus.setValue("STOPPED");
        telemetry.update();
        l.log("stopped");
    }
    private void initialize() {
        hangTask = new HangTask(this);
        driveTrainTask = new DriveTrainTaskMecanum(this);
        sweeperTask = new SweeperTask(this);
        dumperTask = new DumperTask(this);
        hookTask = new HookTask(this);
    }

}