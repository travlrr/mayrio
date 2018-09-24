/*
 * This file is part of mayrio.
 *
 * mayrio is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mayrio is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mayrio.  If not, see <https://www.gnu.org/licenses/>.
 */

package actors.core;

import core.util.ImageUtils;
import mayflower.Font;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TextActor extends MayrioActor {
    private String text;
    private int size;
    private Color color;

    public TextActor(String text, int size) {
        this.text = text;
        this.size = size;
        this.color = Color.BLACK;

        BufferedImage image = this.createImage(text, size, color);

        this.setImage(ImageUtils.bufferedImageToMayflowerImage(image));
    }

    public TextActor(String text, int size, Color color) {
        this.text = text;
        this.size = size;
        this.color = color;

        BufferedImage image = this.createImage(text, size, color);

        this.setImage(ImageUtils.bufferedImageToMayflowerImage(image));
    }

    @Override
    public void act() {
        super.act();
    }

    public void setText(String text) {
        this.setImage(ImageUtils.bufferedImageToMayflowerImage(createImage(text, size, color)));
    }

    public void setColor(Color color) {
        this.setImage(ImageUtils.bufferedImageToMayflowerImage(createImage(text, size, color)));
    }

    private BufferedImage createImage(String text, int size, Color color) {
        Font font = new Font(false, false, size);
        AffineTransform transform = new AffineTransform();
        FontRenderContext ctx = new FontRenderContext(transform, true, true);

        Rectangle2D bounds = font.getAwtFont().getStringBounds(text, ctx);
        int width = (int) bounds.getWidth() + 8;
        int height = (int) bounds.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = image.getGraphics();
        g.setColor(color);
        g.setFont(font.getAwtFont());
        g.drawString(text, 0, height);
        g.dispose();

        return image;
    }
}
