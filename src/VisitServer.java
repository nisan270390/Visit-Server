import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db.HomeFactory;

import DataModel.Building.Building;
import DataModel.Building.Cell;
import DataModel.Building.IntrestPoint;
import Handler.ConnectionHandler;
import PathBuilder.MultyFloorPathPlanner;


public class VisitServer implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//BatelTest();
		startServer();
	}
	
	public static void BatelTest()
	{
		Building m = HomeFactory.getBuildingHome().getById(1);
		
		MultyFloorPathPlanner mp = new MultyFloorPathPlanner(m);
		Vector<Cell> path2 = mp.FindPath(new Cell(1,325,35), new Cell(2,300,488));
		System.out.println("done2");
		for (Cell cell:path2 )
		{
			cell.Print();
		}
		
	}
	
    public static void startServer() {
        try {
        	VisitServer server = new VisitServer(5000);
            new Thread(server).start();
            Runtime.getRuntime().addShutdownHook(new Thread(server.new ShutdownHandler(server)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private final ServerSocket serverSocket;
    private final ExecutorService threadPool;
    private boolean running;
	
	public VisitServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        threadPool = Executors.newCachedThreadPool();
    }
	
    public void run() {
        running = true;
        try {

            while (running) {
            	System.out.println("Srever run .....");
                threadPool.execute(new ConnectionHandler(serverSocket.accept()));
            }

        } catch (IOException ex) {
            // TODO: write to log
        }
        threadPool.shutdown();

        while (!threadPool.isTerminated()) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
            }
        }

        synchronized (this) {
            notifyAll();
        }
        System.out.println("Stopped server at " + serverSocket.getInetAddress().getHostName() + ":" + serverSocket.getLocalPort());
    }
    
    public void stopServer() {
        running = false;

        try {
            serverSocket.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
    
    public class ShutdownHandler implements Runnable {

    	VisitServer server = null;

        public ShutdownHandler(VisitServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            System.out.println("Shutdown Handler: Shutting down gracefully...");
            server.stopServer();
            try {
                synchronized (server) {
                    System.out.println("Shutdown Handler: Wait for server shutdown...");
                    server.wait();

                    System.out.println("Shutdown Handler: Server shut down, now quitting");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
	
	public static void test() {
		//Building b = HomeFactory.getBuildingHome().getAll().get(0);
		
		System.out.println("Hello world 2:)");
		Building m = new Building(1, 2);
		System.out.println("new building created");
		m.addFloorMap("/home/user/workspace/Visit/Azrieli_Black_Zoom.png", 1);
		m.addFloorMap("/home/user/workspace/Visit/Azrieli_Black_Zoom.png", 2);
		System.out.println("the first floor added!");
		Vector<IntrestPoint> up = new Vector<IntrestPoint>();
		up.add(new IntrestPoint(new Cell(1,59,3),new Cell(2,230,250)));
		//up.add(new IntrestPoint(new Cell(1,2,0),new Cell(2,0,0)));
		//m.setIntrestPoint(up, "up");
		
		//m.printFloorMap(0);
		//m.writeFileFloorMap(0);
		
		/*System.out.println("*****start print area1***********");
		m.printArea(1, 72,9);
		System.out.println("*****done print area1***********");
		System.out.println("*****start print area2***********");
		m.printArea(1, 298, 194);
		System.out.println("*****done print area2***********");
		*/
		
		//same floor nevigation
		/*	PathPlanner p = new PathPlanner(m);
		Vector<Cell> path =  p.FindPath(new Cell(1,59,4), new Cell(2,235,255));
		
		
		System.out.println("done1");
		for (Cell cell:path )
		{
			cell.Print();
		}*/
		//multiple floor nevigation
		MultyFloorPathPlanner mp = new MultyFloorPathPlanner(m);
		Vector<Cell> path2 = mp.FindPath(new Cell(1,59,4), new Cell(2,235,255));
		System.out.println("done2");
		for (Cell cell:path2 )
		{
			cell.Print();
		}
		
		
		/*Wifi f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12;
		
		f1 = new Wifi();
		f1.setBssid("C4:A8:1D:14:3C:19");
		f1.setSsid("BezeqNGN_007699_2.4GHz_1");
		f1.setRssi(-72);


		f2 = new Wifi();
		f2.setBssid("04:A1:51:C4:24:F1");
		f2.setSsid("Pavel5");
		f2.setRssi(-79);


		f3 = new Wifi();
		f3.setBssid("94:10:3E:09:B9:23");
		f3.setSsid("Linksys23357");
		f3.setRssi(-73);


		f4 = new Wifi();
		f4.setBssid("00:22:B0:75:91:C5");
		f4.setSsid("shoshi");
		f4.setRssi(-79);


		f5 = new Wifi();
		f5.setBssid("04:A1:51:C9:34:95");
		f5.setSsid("sarah");
		f5.setRssi(-79);


		f6 = new Wifi();
		f6.setBssid("C0:AC:54:F5:67:10");
		f6.setSsid("fridman");
		f6.setRssi(-80);


		f7 = new Wifi();
		f7.setBssid("E8:FC:AF:91:25:2B");
		f7.setSsid("Sarit_2.4");
		f7.setRssi(-82);


		f8 = new Wifi();
		f8.setBssid("7C:03:4C:BA:9B:89");
		f8.setSsid("david1");
		f8.setRssi(-83);


		f9 = new Wifi();
		f9.setBssid("4A:DD:62:24:DD:B8");
		f9.setSsid("SETUP");
		f9.setRssi(-85);


		f10 = new Wifi();
		f10.setBssid("7C:B7:33:A1:A5:F8");
		f10.setSsid("HOTFiber-9578");
		f10.setRssi(-87);


		f11 = new Wifi();
		f11.setBssid("74:DA:38:2B:AC:B7");
		f11.setSsid("rehuven 02");
		f11.setRssi(-84);


		f12 = new Wifi();
		f12.setBssid("04:A1:51:C4:24:F0");
		f12.setSsid("Pavel2.4");
		f12.setRssi(-60);

		Vector<Wifi> vWifi = new Vector<Wifi>();
		vWifi.add(f1);
		vWifi.add(f2);
		vWifi.add(f3);
		vWifi.add(f4);
		vWifi.add(f5);
		vWifi.add(f6);
		vWifi.add(f7);
		vWifi.add(f8);
		vWifi.add(f9);
		vWifi.add(f10);
		vWifi.add(f11);
		vWifi.add(f12);
		
		Vector<GSM> vGSM = new Vector<GSM>();
		Vector<Bluetooth> vBluetooth = new Vector<Bluetooth>();
		
		Measurement me = new Measurement(vGSM, vWifi, vBluetooth);
		
		Location l = LocatorHome.getLocator().locate(me);
		
		System.out.println("SymbolicID: " + l.getSymbolicID());
		System.out.println("MapXcord: " + l.getMapXcord());
		System.out.println("MapYcord: " + l.getMapYcord());
		System.out.println("Accuracy: " + l.getAccuracy());
		
		//Map m = new Map("NisanHome", "/home/user/Documents/home.jpg");
		
		Map m = HomeFactory.getMapHome().getById(1);
		
		Location l = new Location("Salon", m, 591, 423, 100);
		
		Wifi f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12;
		
		f1 = new Wifi();
		f1.setBssid("04:A1:51:C4:24:F0");
		f1.setSsid("Pavel2.4");
		f1.setRssi(-42);
		
		f2 = new Wifi();
		f2.setBssid("00:0E:2E:FA:5F:D4");
		f2.setSsid("CARMAMA");
		f2.setRssi(-66);
		
		f3 = new Wifi();
		f3.setBssid("80:1F:02:19:15:20");
		f3.setSsid("HomeLess");
		f3.setRssi(-80);
		
		f4 = new Wifi();
		f4.setBssid("C4:A8:1D:14:3C:19");
		f4.setSsid("BezeqNGN_007699_2.4GHz_1");
		f4.setRssi(-88);
		
		f5 = new Wifi();
		f5.setBssid("94:10:3E:09:B9:23");
		f5.setSsid("Linksys23357");
		f5.setRssi(-76);
		
		f6 = new Wifi();
		f6.setBssid("04:A1:51:C4:24:F1");
		f6.setSsid("Pavel5");
		f6.setRssi(-48);
		
		f7 = new Wifi();
		f7.setBssid("00:12:2A:38:9E:64");
		f7.setSsid("Tabak1");
		f7.setRssi(-81);

		f11 = new Wifi();
		f11.setBssid("7C:03:4C:BA:9B:89");
		f11.setSsid("david1");
		f11.setRssi(-51);
		
		f12 = new Wifi();
		f12.setBssid("AC:F1:DF:A0:66:A1");
		f12.setSsid("kessiniki");
		f12.setRssi(-78);
		
		Vector<Wifi> vWifi = new Vector<Wifi>();
		vWifi.add(f1);
		vWifi.add(f2);
		vWifi.add(f3);
		vWifi.add(f4);
		vWifi.add(f5);
		vWifi.add(f6);
		vWifi.add(f7);
		vWifi.add(f11);
		vWifi.add(f12);
		
		Vector<GSM> vGSM = new Vector<GSM>();
		Vector<Bluetooth> vBluetooth = new Vector<Bluetooth>();
		
		Measurement me = new Measurement(vGSM, vWifi, vBluetooth);
		Fingerprint fp = new Fingerprint(l, me);
		
		HomeFactory.getFingerprintHome().add(fp);
		*/	
	}

}
