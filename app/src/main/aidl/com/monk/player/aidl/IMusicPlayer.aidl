// IMusicPlayer.aidl
package com.monk.player.aidl;
import com.monk.player.aidl.MusicPlayListner;
import com.monk.player.aidl.SongBean;
// Declare any non-default types here with import statements

interface IMusicPlayer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   void action(in int actioncode,in SongBean bean);
   void registListner(in MusicPlayListner listner);
   void unregistListner(in MusicPlayListner listner );
}
