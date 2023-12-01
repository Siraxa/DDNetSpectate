package com.example.myapplication;

public class javascript {
    public static String init = """
                                                 
                                     var clients = [];
                               
                                                 
                                     function updateSnapshot(newSnapshot) {
                                         for (var id = 0; id<128; id++)
                                         {
                                             clients[id] = newSnapshot[id];
                                         }
                                     }
                                     
                                     var canvasWidth = tw.gl.canvas.width;
                                      var canvasHeight = tw.gl.canvas.height;
                                      
                                  
                                                             
            var canvas = document.getElementById('cnvs');
            var gl = canvas.getContext('webgl');
                            

                                     function renderTees(){
                                         var clientsToRender = clients;
                                       if (tw.gl) {
                                       




                        // Vykreslení kruhu

                                                 for (var id = 0; id<128; id++)
                                                 {
                                                     
                                                     if (typeof clientsToRender[id] != "undefined" && 
                                                     typeof clientsToRender[id].Character != "undefined" &&
                                                       typeof clientsToRender[id].Character.character_core != "undefined" )
                                                       {
                                                          var x = clientsToRender[id].Character.character_core.x/32;
                                                          var y = clientsToRender[id].Character.character_core.y/32;
                                                          
                                                              var webGLX = x;//(x / canvasWidth) * 2 - 1;
                                                              var webGLY = y;//1 - (y / canvasHeight) * 2;
                                                                                               

            var offsetX = webGLX;
            var offsetY = webGLY;
            var vertices = [
               -1.0 + offsetX,  1.0 + offsetY,  // horní levý roh
                1.0 + offsetX,  1.0 + offsetY,  // horní pravý roh
               -1.0 + offsetX, -1.0 + offsetY,  // dolní levý roh
                1.0 + offsetX, -1.0 + offsetY   // dolní pravý roh
            ];

            var vertex_buffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, vertex_buffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);



            const positionAttribLocation = tw.gl.getAttribLocation(tw.stdShader, 'aPosition');
            tw.gl.enableVertexAttribArray(positionAttribLocation);
            tw.gl.vertexAttribPointer(positionAttribLocation, 2, tw.gl.FLOAT, false, 0, 0);

            gl.drawArrays(gl.TRIANGLE_STRIP, 0, 4);
                                                     }
                                                     
                                                    
                                                 }
                                                
                                             } 
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
                                         
                                         renderTees();

                                         // Reset zoomed state
                                         tw.zoomed = false;


                                      
                                             
                                        requestAnimFrame(tw.mainLoop);
                                     }
                                     """;
}
