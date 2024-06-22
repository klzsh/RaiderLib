package RaiderLib.Drivers.IMUs;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;

public class NavX implements IMU {
    private AHRS navX;

    public NavX(){
        navX = new AHRS(SPI.Port.kMXP);
    }

    public double getAngle(){
        return navX.getAngle();
    }

    public double getRoll(){
        return navX.getRoll();
    }

    public double getPitch(){
        return navX.getPitch();
    }

    public double getYaw(){
        return navX.getYaw();
    } 

    public void setAngle(Rotation2d angle){
        // TODO: idk if understood the point of this function but hopefully this works
        // setAngle(new Rotation2d(0)) will simply reset the gyro
        navX.reset(); // 0 gyro
        navX.setAngleAdjustment(angle.getDegrees()); // set angle adjustment new angle
    }

}
