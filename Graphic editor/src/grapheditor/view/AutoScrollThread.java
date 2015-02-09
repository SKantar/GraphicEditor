package grapheditor.view;

import grapheditor.utils.Utils.Direction;


public class AutoScrollThread extends Thread {

	boolean scroll = false;
	DiagramView view;

	Direction direction;
	boolean started = false;

	public AutoScrollThread(DiagramView view) {
		super();
		this.view = view;
	}

	@Override
	public void run() {
		started = true;
		while (true) {
			if (scroll) {
				try {
					sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
				view.autoScroll(direction);
			} else
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (!started)
				break;
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	public boolean isStarted() {
		return started && !isDaemon();
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

}
