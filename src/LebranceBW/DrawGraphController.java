package LebranceBW;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DrawGraphController implements Initializable
{
	@FXML private Canvas dst_canvas;
	@FXML private Button sure_btn, quit_btn;
	@FXML private Slider slider;
//	@FXML private VBox root;
//	@FXML private BorderPane title_panel;
	
	// ާ��Ҷ�ӵĻ��ƾ��󣬸������ϵ���Ϊ1% 7% 7% 85%
	private final double[][] coefficient = 
	{
		{0, 0, 0, 0, 0.16, 0},
		{0.2, -0.26, 0, 0.23, 0.22, 1.6},
		{-0.15, 0.28, 0, 0.26,0.24,0.44},
		{0.85, 0.04, 0, -0.04, 0.85, 1.6},
	};
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		drawgraph(dst_canvas, (int)slider.getValue());
		quit_btn.setOnMouseClicked(e->{Platform.exit();});
		slider.valueProperty().addListener(e->drawgraph(dst_canvas, (int)slider.getValue()));
//		title_panel.setOnMouseDragged(e->{
//			root.getParent().setLayoutX(e.getSceneX());
//			root.getParent().setLayoutY(e.getSceneY());
//		});
	}
	
	private void drawgraph(Canvas canvas, int counts)
	{
		/* ���뻭�����������������ͼ�� */
		// ���建��ͼ��
		int height = (int) canvas.getHeight();
		int width = (int) canvas.getWidth();
		WritableImage wImage = new WritableImage(width, height);
		PixelWriter pw = wImage.getPixelWriter();
		
		// ����ާ��Ҷ��
		Random rd_generator = new Random();
		double x0 = 1;
		double y0 =1;
		for(int i=0;i<counts;i++)
		{
			float rd = rd_generator.nextFloat();
			int index = 0;
			// ���ݸ���ģ�����ѡȡ
			if(rd < 0.85) //���������
				index = 3;
			else if(rd < 0.92)
				index = 2; // ������
			else if(rd < 0.99)
				index = 1; // ������
			else 
				index = 0; // ������
						
			double x1 = coefficient[index][0]*x0 + coefficient[index][1]*y0 + coefficient[index][2];
			double y1 = coefficient[index][3]*x0 + coefficient[index][4]*y0 + coefficient[index][5];
			
			x0 = x1;
			y0 = y1;
			
			// �趨��ͼ�߽�
			// x -5 ~ 5
			// y 0 - 10
			int draw_x = (int) ((width / 2) + x1/10*(width-1) );
			int draw_y = (int) ((1 - y1/10) * height);
			Color colorRand[] = {Color.RED, Color.TURQUOISE, Color.BLUE, Color.GREEN};
			pw.setColor(draw_x, draw_y, colorRand[index]);
		}
		// ����ϴε�����Ȼ�����»���
		canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
		canvas.getGraphicsContext2D().drawImage(wImage, 0, 0);
	}
}




