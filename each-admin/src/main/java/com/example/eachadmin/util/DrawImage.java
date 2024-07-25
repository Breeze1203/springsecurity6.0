package com.example.eachadmin.util;

import jakarta.servlet.http.HttpServletResponse;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class DrawImage {
    private static final Random random = new Random();
    public static final int DEFAULT_IMAGE_WIDTH = 150;
    public static final int DEFAULT_IMAGE_HEIGHT = 40;
    private static final int MAX_FONT_SIZE = 70;



    public static void drawImage(BufferedImage image, String equation) {
        Graphics2D graphics = image.createGraphics();
        try {
            // 填充背景
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
            // 绘制方程
            drawEquationOnImage(graphics, equation);
            // 绘制干扰线
            int numOfLines = random.nextInt(3) + 4;
            for (int i = 0; i < numOfLines; i++) {
                int x1 = random.nextInt(DEFAULT_IMAGE_WIDTH);
                int y1 = random.nextInt(DEFAULT_IMAGE_HEIGHT);
                int x2 = random.nextInt(DEFAULT_IMAGE_WIDTH);
                int y2 = random.nextInt(DEFAULT_IMAGE_HEIGHT);
                Color lineColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                drawLineOnImage(graphics, x1, y1, x2, y2, lineColor);
            }
        } finally {
            graphics.dispose();
        }
    }

    public static void writeToResponse(HttpServletResponse response, BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", outputStream);
            byte[] imageData = outputStream.toByteArray();
            response.setContentType("image/png");
            response.setContentLength(imageData.length);
            try (OutputStream responseStream = response.getOutputStream()) {
                responseStream.write(imageData);
            }
        } finally {
            outputStream.close();
        }
    }

    private static void drawEquationOnImage(Graphics2D graphics, String equation) {
        int fontSize = Math.min(DEFAULT_IMAGE_HEIGHT / 2, MAX_FONT_SIZE);
        graphics.setFont(new Font("Arial", Font.BOLD, fontSize));
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(equation);
        int textHeight = fontMetrics.getAscent(); // 获取文字的上升高度（基线到最高点的距离）
        int x = (DEFAULT_IMAGE_WIDTH - textWidth) / 2;
        int y = (DEFAULT_IMAGE_HEIGHT - textHeight) / 2 + fontMetrics.getAscent(); // 计算垂直居中位置
        graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        graphics.drawString(equation, x, y);
    }


    private static void drawLineOnImage(Graphics2D graphics, int x1, int y1, int x2, int y2, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x1, y1, x2, y2);
    }
}
