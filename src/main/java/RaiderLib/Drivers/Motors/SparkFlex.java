package RaiderLib.Drivers.Motors;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import RaiderLib.Config.MotorConfiguration;
import RaiderLib.Config.PIDConstants;

public class SparkFlex implements Motor {
  /*
   * slot 0 is Velocity
   * slot 1 is position
   */
  private final CANSparkFlex m_SparkFlex;
  private final SparkPIDController m_Controller;
  private final RelativeEncoder m_Encoder;
  public SparkFlex(int CANID, MotorConfiguration config){
    m_SparkFlex = new CANSparkFlex(CANID, CANSparkLowLevel.MotorType.kBrushless);
    m_Controller = m_SparkFlex.getPIDController();
    m_Encoder = m_SparkFlex.getEncoder();

    configMotor(config);
  }

  public void setPercentOut(double speed){
    m_SparkFlex.set(speed);
  }
  
  public void setRPM(double RPM){
    m_Controller.setReference(RPM, ControlType.kVelocity);
  }

  public void setPosition(double Rotations){
    m_Controller.setReference(Rotations, ControlType.kPosition);
  }

  public void setVoltage(double voltage){
    m_SparkFlex.setVoltage(voltage);
  }

  public double getRPM(){
    return m_Encoder.getVelocity();
  }

  public double getPosition(){
    return m_Encoder.getPosition();
  }

  public double getVoltage(){
    return m_SparkFlex.getAppliedOutput() * m_SparkFlex.getBusVoltage();
  }

  public double getCurrent(){
    return m_SparkFlex.getOutputCurrent();
  }
  public void setPIDs(PIDConstants constants, int slot){
    m_Controller.setP(constants.p, slot);
    m_Controller.setI(constants.i, slot);
    m_Controller.setD(constants.d, slot);
    m_Controller.setFF(constants.d, slot);

  }
  public void setP(double p, int slot){
    m_Controller.setP(p, slot);
  }
  public void setI(double i, int slot){
    m_Controller.setI(i, slot);
  }
  public void setD(double d, int slot){
    m_Controller.setD(d, slot);
  }
  public void setF(double f, int slot){
    m_Controller.setFF(f,slot);
  }
  public MotorType getType(){
    return MotorType.CANSPARKFLEX;
  }
  private void configMotor(MotorConfiguration config){
    m_SparkFlex.setIdleMode(config.generalConfigs.brakeModeEnabled ? IdleMode.kBrake : IdleMode.kCoast);
    m_SparkFlex.setInverted(config.generalConfigs.motorInvert);

    m_SparkFlex.setOpenLoopRampRate(config.rampRates.openLoopRampRateSeconds);
    m_SparkFlex.setClosedLoopRampRate(config.rampRates.closedLoopRampRateSeconds);
    m_SparkFlex.setSmartCurrentLimit(config.currentConfigs.StatorCurrentLimit);
    m_SparkFlex.setSecondaryCurrentLimit(config.currentConfigs.SupplyCurrentLimit);
    m_SparkFlex.setSoftLimit(SoftLimitDirection.kForward, (float) config.softLimits.forwardSoftLimitRotations);
    m_SparkFlex.setSoftLimit(SoftLimitDirection.kReverse, (float) config.softLimits.reverseSoftLimitRotations);
    
    m_Encoder.setPosition(0);
    m_Encoder.setPositionConversionFactor(config.generalConfigs.sensorToMechanismRatio);
    m_Encoder.setVelocityConversionFactor(config.generalConfigs.sensorToMechanismRatio);
    
    m_Controller.setP(config.PIDConfigs.slot0Configs.kP, 0);
    m_Controller.setI(config.PIDConfigs.slot0Configs.kI, 0);
    m_Controller.setD(config.PIDConfigs.slot0Configs.kD, 0);
    m_Controller.setFF(config.PIDConfigs.slot0Configs.kF, 0);

    m_Controller.setP(config.PIDConfigs.slot1Configs.kP, 1);
    m_Controller.setI(config.PIDConfigs.slot1Configs.kI, 1);
    m_Controller.setD(config.PIDConfigs.slot1Configs.kD, 1);
    m_Controller.setFF(config.PIDConfigs.slot1Configs.kF, 1);

    m_Controller.setP(config.PIDConfigs.slot2Configs.kP, 2);
    m_Controller.setI(config.PIDConfigs.slot2Configs.kI, 2);
    m_Controller.setD(config.PIDConfigs.slot2Configs.kD, 2);
    m_Controller.setFF(config.PIDConfigs.slot2Configs.kF, 2);

  }
}
