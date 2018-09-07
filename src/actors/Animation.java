package actors;
import mayflower.MayflowerImage;

public class Animation {

    private MayflowerImage[] frames;
    private int currentFrame;
    private int frameRate;

    public Animation(int rate, String[] filenames)
    {
        frames = new MayflowerImage[filenames.length];

        for(int i=0; i < filenames.length; i++)
        {
            frames[i] = new MayflowerImage(filenames[i]);
        }

        currentFrame = 0;
        frameRate = rate;
    }

    public int getFrameRate()
    {
        return frameRate;
    }

    public MayflowerImage getNextFrame()
    {
        currentFrame = currentFrame % frames.length;
        MayflowerImage ret = frames[currentFrame];
        currentFrame = currentFrame + 1;
        return ret;
    }

    public void resize(int w, int h)
    {
        for(MayflowerImage p : frames)
        {
            p.scale(w, h);
        }
    }

    public void setTransparency(int percent)
    {
        for(MayflowerImage p : frames)
        {
            p.setTransparency(percent);
        }
    }

}
