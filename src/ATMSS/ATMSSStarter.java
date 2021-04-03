package ATMSS;

import ATMSS.BAMSHandler.BAMSHandler;
import ATMSS.DepositCollector.DepositCollectorHandler;
import ATMSS.KeypadHandler.Emulator.KeypadEmulator;
import AppKickstarter.AppKickstarter;
import AppKickstarter.misc.Msg;
import AppKickstarter.timer.Timer;

import ATMSS.ATMSS.ATMSS;
import ATMSS.CardReaderHandler.CardReaderHandler;
import ATMSS.KeypadHandler.KeypadHandler;
import ATMSS.TouchDisplayHandler.TouchDisplayHandler;

import javafx.application.Platform;

import java.io.IOException;


//======================================================================
// ATMSSStarter
public class ATMSSStarter extends AppKickstarter {
    protected Timer timer;
    protected ATMSS atmss;
    protected BAMSHandler bams;
    protected CardReaderHandler cardReaderHandler;
    protected KeypadHandler keypadHandler;
    protected TouchDisplayHandler touchDisplayHandler;
    protected DepositCollectorHandler depositCollectorHandler;


    //------------------------------------------------------------
    // main
    public static void main(String[] args) {
        new ATMSSStarter().startApp();

    } // main


    //------------------------------------------------------------
    // ATMStart
    public ATMSSStarter() {
        super("ATMSSStarter", "etc/ATM.cfg");
    } // ATMStart


    //------------------------------------------------------------
    // startApp
    protected void startApp() {
        // start our application
        log.info("");
        log.info("");
        log.info("============================================================");
        log.info(id + ": Application Starting...");

        startHandlers();
    } // startApp


    //------------------------------------------------------------
    // startHandlers
    protected void startHandlers() {
        // create handlers
        try {
            timer = new Timer("timer", this);
            atmss = new ATMSS("ATMSS", this);
            bams = new BAMSHandler("http://cslinux0.comp.hkbu.edu.hk/comp4107_20-21_grp01/");
            cardReaderHandler = new CardReaderHandler("CardReaderHandler", this);
            keypadHandler = new KeypadHandler("KeypadHandler", this);
            touchDisplayHandler = new TouchDisplayHandler("TouchDisplayHandler", this);
            depositCollectorHandler = new DepositCollectorHandler("DepositCollectorHandler", this);
        } catch (Exception e) {
            System.out.println("AppKickstarter: startApp failed");
            e.printStackTrace();
            Platform.exit();
        }

        // start threads
        new Thread(timer).start();
        new Thread(atmss).start();
        new Thread(cardReaderHandler).start();
        new Thread(keypadHandler).start();
        new Thread(touchDisplayHandler).start();
        new Thread(depositCollectorHandler).start();
    } // startHandlers


    //------------------------------------------------------------
    // stopApp
    public void stopApp() {
        log.info("");
        log.info("");
        log.info("============================================================");
        log.info(id + ": Application Stopping...");
        atmss.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
        cardReaderHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
        keypadHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
        touchDisplayHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
        depositCollectorHandler.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
        timer.getMBox().send(new Msg(id, null, Msg.Type.Terminate, "Terminate now!"));
    } // stopApp
} // ATM.ATMSSStarter
