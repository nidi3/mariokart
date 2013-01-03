package org.mimacom.fun.mariokart;

import org.junit.Test;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.Processor;
import javax.media.ProcessorModel;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: stni
 * Date: 20.06.12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class JmfTest {
    @Test
    public void first() throws IOException, CannotRealizeException, NoPlayerException, UnsupportedAudioFileException {
       // AudioInputStream ais = AudioSystem.getAudioInputStream(new File("c:/temp/ai16.wav"));
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("c:/temp/8k16bitpcm.wav"));
        AudioFormat format = ais.getFormat();
        AudioFormat outFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 16000, 16, 1, 2, 16000, false);
        AudioInputStream in=AudioSystem.getAudioInputStream(outFormat,ais);
        AudioSystem.write(in, AudioFileFormat.Type.WAVE,new File("c:/temp/myout.wav"));


        Player player = Manager.createPlayer(new File("c:/temp/8k16bitpcm.wav").toURI().toURL());
        player.prefetch();
        System.out.println(player.getDuration().getSeconds());
//        player.getGainControl().setMute(false);
//        player.getGainControl().setLevel(9999999999f);
        player.start();
        System.out.println("W");
    }
}
