configuration USBAppC { 
}

implementation {
    components USBC, MainC, LedsC;
    components new TimerMilliC() as Timer;

    USBC.Boot -> MainC.Boot;
    USBC.Leds -> LedsC;
    USBC.Timer -> Timer;

    components SerialActiveMessageC;
    components new SerialAMSenderC(100) as Send;

    USBC.AMControl -> SerialActiveMessageC;
    USBC.AMSendT -> Send;

    components new SensirionSht11C() as TempSensor;
    USBC.TempRead -> TempSensor.Temperature;


}