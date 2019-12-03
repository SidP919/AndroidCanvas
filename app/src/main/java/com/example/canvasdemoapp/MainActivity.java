package com.example.canvasdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    //07
    // A constant OFFSET initialized to 120,
    private static final int OFFSET = 120;
    //08
    //Create a MULTIPLIER constant initialized to 100.
    // You will need this constant later, for generating random colors.
    private static final int MULTIPLIER = 100;
    //01
    //The Canvas object stores information on what to draw onto its associated bitmap.
    // For example, lines, circles, text, and custom paths.
    private Canvas mCanvas;
    //02
    //The Paint objects store how to draw.
    // For example, what color, style, line thickness, or text size.
    // Paint offers a rich set of coloring, drawing, and styling options.
    private Paint mPaint = new Paint();
    //03
    //Create a Paint object for underlined text.
    // Paint offers a full complement of typographical styling methods.
    // We can supply these styling flags when we initialize the object or set them later.
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    //04
    //The Bitmap represents the pixels that are shown on the display.
    private Bitmap mBitmap;
    //05
    //A view, in this example an ImageView, is the container for the bitmap.
    // Layout on the screen and all user interaction is through the view.
    private ImageView mImageView;
    //06
    //two Rect variables, mRect and mBounds and then initialized them to rectangles.
    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();
    // and initialize a member variable mOffset with the constant.
    // This mOffset is the distance of a rectangle we draw from the edge of the canvas.
    private int mOffset = OFFSET;
    //09
    //following private member variables are for colors:-
    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    //00
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //10
        //get color resources and assign them to the color member variables:-
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        //11
        //set the color of mPaint to mColorBackground
        mPaint.setColor(mColorBackground);

        //12
        // set the color for mPaintText to the theme color colorPrimaryDark,
        // and set the text size to 70.
        // Depending on the screen size of your device, you may need to adjust the text size.
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);

        //13
        //Get a reference to the image view.
        mImageView = (ImageView) findViewById(R.id.main_activity_imageView);
    }

    //14
    //The drawSomething() method is where all the interaction with the user and drawing on the
    // canvas are implemented.
    //
    //The drawSomething() click handler responds to user taps by drawing an
    // increasingly smaller rectangle until it runs out of room.
    // Then it draws a circle with the text "Done!" to demonstrate basics of drawing on canvas.
    public void drawSomething(View view) {
        //15
        // You always need to do at least the following:
        //
        //1- Create Bitmap.
        //2- Associate Bitmap with View.
        //3- Create Canvas with Bitmap.
        //4- Draw on Canvas.
        //5- Call invalidate() on the View to force redraw.

        //16
        //Get the width and height of the view and create convenience variables
        // for half the width and height.
        // You must do this step every time the method is called,
        // because the size of the view can change (for example, when the device is rotated).
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        //17
        //Add an if-else statement for (mOffset == OFFSET).
        //When drawSomething() is called, the app is in one of three states:
        //
        //01--- mOffset == OFFSET. The app is only in this state the first time the user taps.
        // Create the Bitmap, associate it with the View, create the Canvas,
        // fill the background, and draw some text. Increase the offset.
        //
        // 02--- mOffset != OFFSET and the offset is smaller than half the screen width and height.
        // Draw a rectangle with a computed color and increase the offset.
        //
        // 03--- mOffset != OFFSET and the offset is equal to or larger than half the
        // screen width and height. Draw a circle with the text "Done!".
        if (mOffset == OFFSET) {

            //18
            //create a Bitmap.
            //Supply the width and height for the bitmap, which are going to be
            // the same as the width and height of the view.
            //Pass in a Bitmap.config configuration object.
            // A bitmap configuration describes how pixels are stored.
            // How pixels are stored affects the quality (color depth) as well as the
            // ability to display transparent/translucent colors.
            // The ARGB_8888 format supports Alpha, Red, Green, and Blue channels for each pixel.
            // Each color is encoded in 8 bits, for a total of 4 bytes per pixel.
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);

            //19
            //Associate the bitmap with the ImageView.
            mImageView.setImageBitmap(mBitmap);

            //20
            //Create a Canvas and associate it with mBitmap,
            // so that drawing on the canvas draws on the bitmap.
            mCanvas = new Canvas(mBitmap);
            //Fill the entire canvas with the background color.
            mCanvas.drawColor(mColorBackground);
            //Draw the "Keep tapping" text onto the canvas.
            // You need to supply a string, x and y positions, and a Paint object for styling.
            mCanvas.drawText(getString(R.string.keep_tapping),
                    100, 100,// x and y represents the position of the view on the ImageView
                    mPaintText);

            //21
            //Increase the offset.
            mOffset += OFFSET;

        } else {
            //23
            if (mOffset < halfHeight && mOffset < halfWidth) {
                //24
                // Change the color by subtracting an integer.
                mPaint.setColor(mColorRectangle - MULTIPLIER * mOffset);//This code generates the
                // next color by subtracting the current offset times a multiplier from the
                // original color. A color is represented by a single number,
                // so you can manipulate it in this way for some fun effects.

                //25
                mRect.set(
                        mOffset, mOffset,
                        vWidth - mOffset,
                        vHeight - mOffset);//Change the size of the mRect rectangle
                // to the width of the view, minus the current offset.
                //26
                mCanvas.drawRect(mRect, mPaint);

                //27
                // Increase the indent.
                mOffset += OFFSET;
            } else {
                //28
                mPaint.setColor(mColorAccent);

                //29
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth / 3, mPaint);

                //30
                String text = getString(R.string.done);
                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();

                //31
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        //22
        // invalidate() the view
        // so that the system redraws the view every time drawSomething() is executed.
        view.invalidate();
    }
}
