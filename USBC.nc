#include "Timer.h"
#include "senseappmsg.h"

module USBC {
	uses {
		interface Boot;
		interface Leds;
		interface Timer<TMilli>;
		interface AMSend as AMSendT;
		interface SplitControl as AMControl;
	}
}

implementation {
	#define SAMPLING_FREQUENCY 1000

	bool LEDon = FALSE;

	bool busy = FALSE;
  	message_t package;
  	uint16_t counter = 0;

	event void Boot.booted() {
		call AMControl.start();
		//call Timer.startPeriodic(SAMPLING_FREQUENCY);
	}

	task void SendTemptoUart(){
		if (!busy){
			TemptoUartMsg* btrpkt = (TemptoUartMsg*)(call AMSendT.getPayload(&package, NULL));

			btrpkt->nodeid = TOS_NODE_ID;

			btrpkt->temp = counter;

			if (call AMSendT.send(AM_BROADCAST_ADDR, &package, sizeof(TemptoUartMsg)) == SUCCESS) {
				busy=TRUE;
			} else {

			}
		}
    }

	event void Timer.fired() {
		if (LEDon) {
			call Leds.led1On();
			counter++;
			post SendTemptoUart();
			LEDon = FALSE;
		} else {
			call Leds.led1Off();
			LEDon = TRUE;
		}
	}

	event void AMSendT.sendDone(message_t* msg, error_t error) {
		if (&package == msg) {
			busy = FALSE;
		}
	}

	event void AMControl.startDone(error_t err) {
		if (err == SUCCESS) {

		} else {

		}
	}

	event void AMControl.stopDone(error_t err) {
	
	}
}