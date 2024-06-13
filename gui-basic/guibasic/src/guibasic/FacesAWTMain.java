package guibasic;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FacesAWTMain {
    public static void main(String[] args){
		new FacesAWTMain();
	}

    FacesAWTMain(){
		FaceFrame f = new FaceFrame();
		f.setSize(800,800);
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
			System.exit(0);}});
		f.setVisible(true);
	}

	// インナークラスを定義
	class FaceFrame extends Frame{
    private FaceObj[] fobjs;
	
	FaceFrame(){
		fobjs = new FaceObj[9];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
					int browUpDown = -setEmotion(i);
				int mouthUpDown = setEmotion(i) + 5 * j;
				fobjs[j + 3 * i] = new FaceObj(200, 200, 200 * j + 50, 200 * i + 50, browUpDown, mouthUpDown);
			}
		}
		
    }

	public void paint(Graphics g) {
	// この中には、g.drawLine というのは入ってこない
	// Graphicsクラス(型のようなもの---今は--)のgという変数はメソッドに渡す
		//FaceObj fobj= new FaceObj();
		//fobj.drawRim();
        for (FaceObj fobj : fobjs){
			fobj.drawFace(g);
		}
	}
	public  int setEmotion(int i){
		int faceEmotion = 0;
		int happy = 30;
		int nomal = 0;
		int sad = -30;
		if(i == 0){
			faceEmotion = sad;
		}
		if(i == 1){
			faceEmotion = nomal;
		}
		if(i == 2){
			faceEmotion = happy;
		}
		return faceEmotion;
	}

    
}//FaceFrame end

	//Faceクラスを作ってみよう。
	private class FaceObj{
		private int w, h, xStart, yStart;
		private int browUpDown, mouthUpDown;

		FaceObj(int w, int h, int xStart, int yStart, int browUpDown, int mouthUpDown){
			this.w = w;
			this.h = h;
			this.xStart = xStart;
			this.yStart = yStart;
			this.browUpDown = browUpDown;
			this.mouthUpDown = mouthUpDown;
		}

        public void drawFace(Graphics g) {
        drawRim(g);
		drawBrow(g, browUpDown); 
		drawEye(g, 35);
		drawNose(g, 10);
		drawMouth(g, 100, mouthUpDown);
        }

		public void drawRim(Graphics g) {  // wが横幅、hが縦幅
			g.drawLine(xStart, yStart, xStart+w, yStart);
			g.drawLine(xStart, yStart, xStart, yStart+h);
			g.drawLine(xStart, yStart+h, xStart+w, yStart+h);
			g.drawLine(xStart+w, yStart, xStart+w, yStart+h);
		}

		public void drawBrow(Graphics g,int updown) { // xは目の幅 呼ばれる方(=定義する方)
			int wx1 = xStart + w*2/8;
			int wx2 = xStart + w*5/8;
			int wy = yStart + h/5;
			g.drawLine(wx1, wy+updown, wx1+w*1/8, wy);
			g.drawLine(wx2, wy, wx2+w*1/8, wy+updown);
		}

		/*public void setPosition(int xStart0, int yStart0) {
			this.xStart=xStart0;
			this.yStart=yStart0;
		}*/

		public void drawNose(Graphics g, int nx) { // xは鼻の長さ
			int xNose = xStart + w/2;
			int yNOse = yStart + h/2;
			g.drawLine(xNose, yNOse, xNose, yNOse + nx);
		}

		public void drawEye(Graphics g, int r) { // rは目の半径
			g.drawOval(xStart+45,yStart+65,r,r);
			g.drawOval(xStart+120,yStart+65,r,r);
			
		}

		public void drawMouth(Graphics g, int mx, int mouthUpDown) { // xは口の幅
			int xMiddle = xStart + w/2;
			int yMiddle = yStart + h - 40;
			g.drawLine(xMiddle - mx/2, yMiddle, xMiddle, yMiddle + mouthUpDown);
			g.drawLine(xMiddle, yMiddle + mouthUpDown, xMiddle + mx/2, yMiddle);
		}

	}
}