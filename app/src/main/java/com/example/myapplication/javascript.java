package com.example.myapplication;

public class javascript {
    public static String init = """


                    function vytvorBufferProKruh(gl, radius, segments) {
                var body = [];
                for (var i = 0; i <= segments; i++) {
                    var theta = (i / segments) * 2 * Math.PI;
                    body.push(radius * Math.cos(theta), radius * Math.sin(theta));
                }

                var bufferKruhu = gl.createBuffer();
                gl.bindBuffer(gl.ARRAY_BUFFER, bufferKruhu);
                gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(body), gl.STATIC_DRAW);

                return bufferKruhu;
            }

                    
                    
                   
            // Vytvoření bufferu (např. při inicializaci)
            tw.mainLoop = function() {
                // Transform mouse coords to world coords
                tw.mouseDownInc[0] = tw.mouseDownInc[0] / tw.canvas.width * tw.worldView[0] * tw.aspect / tw.cameraZoom;
                tw.mouseDownInc[1] = tw.mouseDownInc[1] / tw.canvas.height * tw.worldView[1] / tw.cameraZoom;

                // Move camera
                tw.cameraPos[0] += tw.mouseDownInc[0];
                tw.cameraPos[1] += tw.mouseDownInc[1];

                tw.mouseDownInc[0] = 0;
                tw.mouseDownInc[1] = 0;

                tw.render();


                    if (tw.gl) {
                        var bufferKruhu = vytvorBufferProKruh(tw.gl, 50.0, 32); // Radius 1.0, 32 segmentů

                        tw.gl.useProgram(tw.stdShader); // Použití existujícího shaderu
                        var colorLocation = tw.gl.getUniformLocation(tw.stdShader, "uColor");
                        var yellowColor = [1.0, 1.0, 0.0, 1.0]; // RGBA pro žlutou
                        tw.gl.uniform4fv(colorLocation, yellowColor); // Nastavit barvu kruhu

                        tw.gl.bindBuffer(tw.gl.ARRAY_BUFFER, bufferKruhu); // Připojení bufferu pro kruh
                        // Nastavení atributů (pokud je potřeba)
                        tw.gl.enableVertexAttribArray(tw.stdShader.vPosAttr);
                        tw.gl.vertexAttribPointer(tw.stdShader.vPosAttr, 2, tw.gl.FLOAT, false, 0, 0);
                        // Vykreslení kruhu
                        tw.gl.drawArrays(tw.gl.TRIANGLE_FAN, 0, 32);
                    } 


                // Reset zoomed state
                tw.zoomed = false;


             
        
               requestAnimFrame(tw.mainLoop);
            }
            """;
}
