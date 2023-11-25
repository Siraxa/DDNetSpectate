package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
/*
public class MapView extends SurfaceView implements SurfaceHolder.Callback {

    private Bitmap mapBitmap;

    public MapView(Context context) {
        super(context);
        // Initialize bitmap, gesture detectors, etc.
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Handle drawing operations here
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            // Draw your bitmap on the canvas
            canvas.drawBitmap(mapBitmap, ...);
            holder.unlockCanvasAndPost(canvas);
        }
    }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Vykreslení mapy
        canvas.drawBitmap(mapBitmap, ...);

        // Vykreslení každého hráče
        for (Player player : players) {
            // Přepočet pozic hráčů na pixelové pozice
            float x = ...; // Vypočítejte x pozici
            float y = ...; // Vypočítejte y pozici
            canvas.drawBitmap(playerBitmap, x, y, null);
        }
    }

    // Implement other SurfaceHolder.Callback methods: surfaceChanged, surfaceDestroyed

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events for panning and zooming
        return true;
    }
}*/