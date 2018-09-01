import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
	static int mouseX, mouseY, newMouseX, newMouseY;
	static int squareSize=64;
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.yellow);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				squareSize=(int)(Math.min(getHeight(), getWidth()-200))/8;
			}
		});
		for (int i=0;i<64;i+=2) {
			g.setColor(new Color(255,200,100));
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(new Color(150,50,30));
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
		}
		Image chessPiecesImage;
		chessPiecesImage=new ImageIcon("ChessPiecesHD.png").getImage();
		for (int i=0;i<64;i++) {
			int j=-1,k=-1;
			switch (alphaBetaChess.chessBoard[i / 8][i % 8]) {
				case "P":
					j = 5;
					k = 0;
					break;
				case "p":
					j = 5;
					k = 1;
					break;
				case "R":
					j = 4;
					k = 0;
					break;
				case "r":
					j = 4;
					k = 1;
					break;
				case "K":
					j = 3;
					k = 0;
					break;
				case "k":
					j = 3;
					k = 1;
					break;
				case "B":
					j = 2;
					k = 0;
					break;
				case "b":
					j = 2;
					k = 1;
					break;
				case "Q":
					j = 1;
					k = 0;
					break;
				case "q":
					j = 1;
					k = 1;
					break;
				case "A":
					j = 0;
					k = 0;
					break;
				case "a":
					j = 0;
					k = 1;
					break;
			}
			if (j != -1 && k != -1) {
				g.drawImage(chessPiecesImage, (i % 8) * squareSize, (i / 8) * squareSize, (i % 8 + 1) * squareSize, (i / 8 + 1) * squareSize, j * chessPiecesImage.getWidth(this)/6, k * chessPiecesImage.getHeight(this)/2, (j + 1) * chessPiecesImage.getWidth(this)/6, (k + 1) * chessPiecesImage.getHeight(this)/2, this);
			}

		}
        /*g.setColor(Color.BLUE);
        g.fillRect(x-20, y-20, 40, 40);
        g.setColor(new Color(190,81,215));
        g.fillRect(40, 20, 80, 50);
        g.drawString("Jonathan", x, y);
        */
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
			//if inside the board
			mouseX=e.getX();
			mouseY=e.getY();
			repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getX()<8*squareSize &&e.getY()<8*squareSize) {
			//if inside the board
			newMouseX=e.getX();
			newMouseY=e.getY();
			if (e.getButton()==MouseEvent.BUTTON1) {
				String dragMove;
				if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(alphaBetaChess.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
					//pawn promotion
					dragMove=""+mouseX/squareSize+newMouseX/squareSize+alphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
				} else {
					//regular move
					dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+alphaBetaChess.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
				}
				String userPosibilities=alphaBetaChess.posibleMoves();
				if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
					//if valid move
					alphaBetaChess.makeMove(dragMove);
					alphaBetaChess.flipBoard();
					alphaBetaChess.makeMove(alphaBetaChess.alphaBeta(alphaBetaChess.globalDepth, 1000000, -1000000, "", 0));
					alphaBetaChess.flipBoard();
					repaint();
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}