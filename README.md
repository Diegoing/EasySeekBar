# EasySeekBar
An Android Seekbar
# effect
![img](https://github.com/Diegoing/EasySeekBar/blob/master/gifs/seekbar.gif)
![img](https://github.com/Diegoing/EasySeekBar/blob/master/gifs/seekbar1.gif)
![img](https://github.com/Diegoing/EasySeekBar/blob/master/gifs/seekbar2.gif)
![img](https://github.com/Diegoing/EasySeekBar/blob/master/gifs/seekbar3.gif)
# Start
    1. Add root build.gradle
     repositories {
       // ...
      maven { url 'https://www.jitpack.io' }
      }
    2. Add build.gradle
     dependencies {
	        implementation 'com.github.Diegoing:EasySeekBar:0.0.1'
	    }
# Attributes
        name               |       format      |      description     
        ------
        configuration      |      string       |    vertical、horizontal、circle、semicircle
        progress_bg_color  |      color        |    progress background color
        progress_color     |      color        |    progress color
        circle_r           |      dimension    |    Control the radius of the ball
        line               |      dimension    |    Control the length or radius of the radius
        circle_color       |      color        |    Control the color of the ball
        max_progress       |      integer      |    max value
        min_progress       |      integer      |    min value
        progress_with      |      integer      |    progress width
# Example
    1. xml
    <com.diegoing.view.seekbar.EasySeekBar
        android:id="@+id/esb_v1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:configuration="vertical"
        app:progress_bg_color="@color/colorAccent"
        app:progress_color="@color/colorPrimary"
        app:max_progress="100"
        app:min_progress="50"
        app:circle_r="10dp"
        app:line="200dp"
        app:circle_color="@color/colorPrimaryDark"
        app:progress_with="15"
        android:layout_marginLeft="50dp"
        />
    2. code
     EasySeekBar esb_v1 = findViewById(R.id.esb_v1);
      esb_v1.setEasySeekBarLister(new EasySeekBar.EasySeekBarLister() {
            @Override
            public void onProgress(int pro) {
                Toast.makeText(VerticalActivity.this,pro+"",Toast.LENGTH_SHORT).show();
            }
        });
        esb_v1.setValue(60);
 # Thanks
    
    
    
    
   
